package com.pzb.webflux.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "user-manager")
public class User {
	private String id;
	private String username;
	private String email;
	private String firstName;
	private String lastName;

	private String password;

	private Map<String, String> attributes;

	@JsonIgnore
	private Map<String, String> roles;
	@JsonIgnore
	private IMPORT_STATUS status;

	public enum IMPORT_STATUS{
		ADD,UPDATE,DELETE
	}
	
}
