package com.miniboot

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType;
import javax.persistence.Id;

import groovy.transform.ToString;

@Entity
@ToString
class Tag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id
	String name

}
