package com.pzb.webflux.demo.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pzb.webflux.demo.domain.User;
import com.pzb.webflux.demo.domain.UserFields;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CsvHandler {
    public List<User> getUsers(InputStream input) throws Exception {
        final List<User> retVal = Lists.newArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(input), StandardCharsets.UTF_8));
        CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

        final Map<String, Integer> headerMap = parser.getHeaderMap();

        Iterator<CSVRecord> records = parser.iterator();
        while(records.hasNext()) {
            CSVRecord record = records.next();

            User u = new User();
            //u.setId(record.get(UserFields.FIELD_ID));
            u.setUsername(record.get(UserFields.FIELD_USER_NAME));
            u.setFirstName(record.get(UserFields.FIELD_FIRST_NAME));
            u.setLastName(record.get(UserFields.FIELD_LAST_NAME));
            u.setEmail(record.get(UserFields.FIELD_EMAIL_ADDRESS));

            // update attributes
            final Map<String, String> attributes = Maps.newHashMap();
            UserFields.USER_CUSTOM_FIELDS.stream().forEach(v -> {
                if(headerMap.containsKey(v)) {
                    String value = record.get(v);
                    if(!StringUtils.isEmpty(value)) {
                        attributes.put(UserFields.COLUMN_NAME_MAPPING.get(v), value);
                    }
                }
            });
            u.setAttributes(attributes);

            final Map<String, String> roles = Maps.newHashMap();
            UserFields.APP_USER_TYPE_MAPPING.keySet().stream().forEach(k -> {
                String name = UserFields.APP_USER_TYPE_MAPPING.get(k);
                if(headerMap.containsKey(name)) {
                    String value = record.get(name);
                    if(!StringUtils.isEmpty(value)) {
                        roles.put(k, value);
                    }
                }
            });
            u.setRoles(roles);

            retVal.add(u);
        }

        return retVal;
    }
}
