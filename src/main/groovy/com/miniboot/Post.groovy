package com.miniboot

import java.util.Date
import java.util.List

import javax.persistence.CascadeType;
import javax.persistence.Entity
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType;
import javax.persistence.Id
import javax.persistence.OneToMany

import com.fasterxml.jackson.databind.annotation.JsonDeserialize

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
	Date date
	String category
	String filename
}
