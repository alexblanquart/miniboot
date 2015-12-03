package com.miniboot

import java.util.List
import java.time.LocalDate;
import java.util.Date

import javax.persistence.CascadeType;
import javax.persistence.Entity
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType;
import javax.persistence.Id
import javax.persistence.OneToMany

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import groovy.transform.ToString

@ToString
@Entity
class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id
	String title
	@JsonDeserialize(using=TagsDeserializer.class)
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	List<Tag> tags
	String image
	String date
	LocalDate localDate
	String category
	String filename
	
	// should be outside for sure
	String summary
}
