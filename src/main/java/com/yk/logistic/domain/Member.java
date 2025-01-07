package com.yk.logistic.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Long id;
//	
//	@Column(name = "password",nullable = false)
//	private String password;
	
	@Column(name = "name", nullable = false)
	private String name;
	
//	@Column(name = "email", nullable = false)
//	private String email;
//	
//	@Column(name = "address" ,nullable = true)
//	private String address;
//	
//	@Column(name = "createdDate",nullable = true)
//	private Date createdDate;
//	
//	@Column(name = "modifiedDate",nullable = true)
//	private Date modifiedDate;

}