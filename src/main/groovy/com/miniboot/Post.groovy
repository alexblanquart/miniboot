package com.miniboot

import java.util.List
import java.time.LocalDate;
import java.util.Date

import javax.persistence.CascadeType
import javax.persistence.ElementCollection;
import javax.persistence.Entity
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType;
import javax.persistence.Id
import javax.persistence.OneToMany

import groovy.transform.ToString

@ToString
@Entity
class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id
	String title
	@ElementCollection(fetch=FetchType.EAGER)
	List<String> tags
	String image
	String date
	LocalDate localDate
	String category
	String filename
	
	// should be outside for sure
	String summary
}
