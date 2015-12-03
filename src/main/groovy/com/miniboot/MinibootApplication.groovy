package com.miniboot

import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter;
import java.util.List

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper

@SpringBootApplication
class MinibootApplication {

    static void main(String[] args) {
        SpringApplication.run MinibootApplication, args
    }
    
    @Bean
    CommandLineRunner init(PostsRepository repository, ResourceLoader resourceLoader, ObjectMapper mapper){
    	{ args ->
        	def metaFolder = resourceLoader.getResource("file:src/main/init/posts/meta").file
        	metaFolder.eachFile { file ->
        		def post = mapper.readValue(file, Post.class)
        		post.filename = file.name.split("\\.")[0]
        		post.localDate = LocalDate.parse(post.date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        		if (post.image){ // TODO should make all post have an image        			
        			repository.save(post)
        		}
        	}
        	
        	repository.findAll().each { post ->
        		println "loaded $post" 
        	}
    	}
    }
    
}
