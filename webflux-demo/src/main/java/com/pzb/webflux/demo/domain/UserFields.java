package com.pzb.webflux.demo.domain;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Map;
import java.util.Set;

public final class UserFields {
    public static final String FIELD_ID = "id";
    public static final String FIELD_ACTIVE = "ACTIVE";
    public static final String FIELD_HRIS_EMPLOYEE_ID = "HRIS_EMPLOYEE_ID";
    public static final String FIELD_USER_NAME = "USER_NAME";
    public static final String FIELD_EMAIL_ADDRESS = "EMAIL_ADDRESS";
    public static final String FIELD_FIRST_NAME = "FIRST_NAME";
    public static final String FIELD_MIDDLE_NAME = "MIDDLE_NAME";
    public static final String FIELD_LAST_NAME = "LAST_NAME";
    public static final String FIELD_LOCALE = "LOCALE";
    public static final String FIELD_COUNTRY = "COUNTRY";
    public static final String FIELD_JOB_TITLE = "JOB_TITLE";
    public static final String FIELD_SSO_ENABLED = "SSO_ENABLED";
    public static final String FIELD_PHONE_NUMBER = "PHONE_NUMBER";
    public static final String FIELD_PHONE_EXTENSION = "PHONE_EXTENSION";
    public static final String FIELD_FAX_NUMBER = "FAX_NUMBER";
    public static final String FIELD_TIMEZONE = "TIMEZONE";
    public static final String FIELD_VALIDITY_DATE = "VALIDITY_DATE";
    public static final String FIELD_EXPIRATION_DATE = "EXPIRATION_DATE";
    public static final String FIELD_LAST_LOGIN = "LAST_LOGIN";
    public static final String FIELD_SEND_PASSWORD_EMAIL = "SEND_PASSWORD_EMAIL";
    public static final String FIELD_USER_CATEGORY = "USER_CATEGORY";
    public static final String FIELD_IBMMarketEnablements = "IBMMarketEnablements";
    public static final String FIELD_IWR_USER_TYPE = "IWR_USER_TYPE";
    public static final String FIELD_IWR_ACCESS = "IWR_ACCESS";
    public static final String FIELD_MYCA_USER_TYPE = "MYCA_USER_TYPE";
    public static final String FIELD_MYCA_ACCESS = "MYCA_ACCESS";
    public static final String FIELD_TS_ADMIN_USER_TYPE = "TS_ADMIN_USER_TYPE";
    public static final String FIELD_TS_ADMIN_ACCESS = "TS_ADMIN_ACCESS";
    public static final String FIELD_WCA_USER_TYPE = "WCA_USER_TYPE";
    public static final String FIELD_WCA_ACCESS = "WCA_ACCESS";

    public static final String ATTRIBUTE_CLIENT_ID = "client";

    public static final String APP_WCA = "WCA";
    public static final String APP_TS_ADMIN = "TS_ADMIN";
    public static final String APP_MYCA = "MYCA";
    public static final String APP_IWR = "IWR";

    public static final Set<String> USER_CUSTOM_FIELDS = ImmutableSet.<String>builder()
            .add(FIELD_ACTIVE)
            .add(FIELD_HRIS_EMPLOYEE_ID)
            .add(FIELD_MIDDLE_NAME)
            .add(FIELD_LOCALE)
            .add(FIELD_COUNTRY)
            .add(FIELD_JOB_TITLE)
            .add(FIELD_SSO_ENABLED)
            .add(FIELD_PHONE_NUMBER)
            .add(FIELD_PHONE_EXTENSION)
            .add(FIELD_FAX_NUMBER)
            .add(FIELD_TIMEZONE)
            .add(FIELD_VALIDITY_DATE)
            .add(FIELD_EXPIRATION_DATE)
            .add(FIELD_LAST_LOGIN)
            .add(FIELD_SEND_PASSWORD_EMAIL)
            .add(FIELD_USER_CATEGORY)
            .add(FIELD_IBMMarketEnablements)
            .build();

    public static final Set<String> USER_REQUIRED_FIELDS = ImmutableSet.<String>builder()
            .add(FIELD_ID)
            .add(FIELD_USER_NAME)
            .add(FIELD_FIRST_NAME)
            .add(FIELD_LAST_NAME)
            .add(FIELD_EMAIL_ADDRESS)
            .build();

    public static final Set<String> USER_ROLE_FIELDS = ImmutableSet.<String>builder()
            .add(FIELD_IWR_USER_TYPE)
            .add(FIELD_IWR_ACCESS)
            .add(FIELD_MYCA_USER_TYPE)
            .add(FIELD_MYCA_ACCESS)
            .add(FIELD_TS_ADMIN_USER_TYPE)
            .add(FIELD_TS_ADMIN_ACCESS)
            .add(FIELD_WCA_USER_TYPE)
            .add(FIELD_WCA_ACCESS)
            .build();

    public static final Map<String, String> COLUMN_NAME_MAPPING = ImmutableMap.<String, String>builder()
            .put(FIELD_ACTIVE, "active")
            .put(FIELD_HRIS_EMPLOYEE_ID, "hrisid")
            .put(FIELD_USER_NAME, "username")
            .put(FIELD_EMAIL_ADDRESS, "email")
            .put(FIELD_FIRST_NAME, "firstName")
            .put(FIELD_MIDDLE_NAME, "middleName")
            .put(FIELD_LAST_NAME, "lastName")
            .put(FIELD_LOCALE, "locale")
            .put(FIELD_COUNTRY, "country")
            .put(FIELD_JOB_TITLE, "jobTitle")
            .put(FIELD_SSO_ENABLED, "ssoEnabled")
            .put(FIELD_PHONE_NUMBER, "phoneNumber")
            .put(FIELD_PHONE_EXTENSION, "phoneExtension")
            .put(FIELD_FAX_NUMBER, "faxNumber")
            .put(FIELD_TIMEZONE, "timeZone")
            .put(FIELD_VALIDITY_DATE, "validityDate")
            .put(FIELD_EXPIRATION_DATE, "expirationDate")
            .put(FIELD_LAST_LOGIN, "lastLogin")
            .put(FIELD_SEND_PASSWORD_EMAIL, "sendPasswordEmail")
            .put(FIELD_USER_CATEGORY, "userCategory")
            .put(FIELD_IBMMarketEnablements, "ibmMarketEnablements")
            .put(FIELD_IWR_USER_TYPE, "iwrUserType")
            .put(FIELD_IWR_ACCESS, "iwrAccess")
            .put(FIELD_MYCA_USER_TYPE, "mycaUserType")
            .put(FIELD_MYCA_ACCESS, "mycaAccess")
            .put(FIELD_TS_ADMIN_USER_TYPE, "tsAdminUserType")
            .put(FIELD_TS_ADMIN_ACCESS, "tsAdminAccess")
            .put(FIELD_WCA_USER_TYPE, "wcaUserType")
            .put(FIELD_WCA_ACCESS, "wcaAccess")
            .build();

    public static final Map<String, String> APP_USER_TYPE_MAPPING = ImmutableMap.<String, String>builder()
            .put(APP_TS_ADMIN, FIELD_TS_ADMIN_USER_TYPE)
            .put(APP_IWR, FIELD_IWR_USER_TYPE)
            .put(APP_WCA, FIELD_WCA_ACCESS)
            .put(APP_MYCA, FIELD_MYCA_USER_TYPE)
            .build();
}
