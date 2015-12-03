package com.miniboot

import org.pegdown.PegDownProcessor
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class PagesController {
	
	PostsRepository postsRepository
	ResourceLoader resourceLoader
	PegDownProcessor pdp
	File contentFolder
	File materialFolder
	
	@Autowired
	PagesController(PostsRepository postsRepository, ResourceLoader resourceLoader){
		this.postsRepository = postsRepository
		this.resourceLoader = resourceLoader
		this.pdp = new PegDownProcessor()
		this.contentFolder = resourceLoader.getResource("file:src/main/init/posts/content").file
		this.materialFolder = resourceLoader.getResource("file:src/main/init/posts/material").file
	}
	
	def recentBlogPosts(){
		postsRepository.findAllByCategory("blog", new PageRequest(0, 10, Direction.DESC, "localDate"))
	}
	
	def recentPosts(){
		postsRepository.findAll(new PageRequest(0, 4, Direction.DESC, "localDate"))
	}
	
	def allCategoryPosts(String category){
		postsRepository.findAllByCategory(category, new Sort(Direction.DESC, "localDate"))
	}
	
	def allTagPosts(String tag){
		postsRepository.findAllByTagsContaining(tag, new Sort(Direction.DESC, "localDate"))
	}
	
	def allTags(){
		def allTagsList = []
		postsRepository.findAll().collect{ it.tags.each{allTagsList << it} }
		def allTagsSet = allTagsList as Set 
	}
	
	def content(Post post){
        pdp.markdownToHtml(new File(contentFolder, post.filename+".md").text)
	}
	
	def material(Post post){
		pdp.markdownToHtml(new File(materialFolder, post.filename+".md").text)
	}
	
	@RequestMapping("/")
	String index(Map<String, Object> model) {
		model.put("recentBlog", recentBlogPosts())
		"index"
	}
	
	@RequestMapping("/posts/{id}")
	String post(Map<String, Object> model, @PathVariable("id") Post post){
		model.put("post", post)
		model.put("content", content(post))
		model.put("recentPosts", recentPosts())
		model.put("allTags", allTags())
		if (post.category == "idea"){
			model.put("material", material(post))
		}
		post.category + "Post"
	}
	
	@RequestMapping("/posts/category/{category}")
	String category(Map<String, Object> model, @PathVariable String category){
		def posts = allCategoryPosts(category)
		posts.each { post ->
			def content = content(post)
			// remove any html tags for summary
	        post.summary = content.replaceAll("<(.|\n)*?>", "") 
	        post.summary = post.summary[0..Math.min(100, post.summary.length()-1)]
		}
		model.put("category", category)
		model.put("posts", posts)
		"posts"
	}
	
	@RequestMapping("/posts/tag/{tag}")
	String tag(Map<String, Object> model, @PathVariable String tag){
		def posts = allTagPosts(tag)
		posts.each { post ->
			def content = content(post)
			// remove any html tags for summary
			post.summary = content.replaceAll("<(.|\n)*?>", "") 
			post.summary = post.summary[0..Math.min(100, post.summary.length()-1)]
		}
		model.put("category", "blog") // TODO not necessarily
		model.put("posts", posts)
		"posts"
	}
	
	@RequestMapping("/about")
	String about(){
		"about"
	}
	
}
