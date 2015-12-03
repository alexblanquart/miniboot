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
	
	@Autowired
	PostsRepository postsRepository
	
	@Autowired
	TagsRepository tagsRepository
	
	@Autowired
	ResourceLoader resourceLoader
	
	def recentBlogPosts(){
		postsRepository.findAllByCategory("blog", new PageRequest(0, 10, Direction.DESC, "localDate"))
	}
	
	def recentPosts(){
		postsRepository.findAll(new PageRequest(0, 4, Direction.DESC, "localDate"))
	}
	
	def allBlogPosts(){
		postsRepository.findAllByCategory("blog", new Sort(Direction.DESC, "localDate"))
	}
	
	def allTags(){
		tagsRepository.findAll()
	}
	
	def content(Post post){
		def contentFolder = resourceLoader.getResource("file:src/main/init/posts/content")
		def pdp = new PegDownProcessor()
        pdp.markdownToHtml(new File(contentFolder.file, post.filename+".md").text)
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
		"post"
	}
	
	@RequestMapping("/blog")
	String blog(Map<String, Object> model){
		def posts = allBlogPosts()
		posts.each { post ->
			def content = content(post)
			// remove any html tags for summary
	        post.summary = content.replaceAll("<(.|\n)*?>", "") 
	        post.summary = post.summary[0..Math.min(100, post.summary.length()-1)]
		}
		model.put("category", "blog")
		model.put("posts", posts)
		"blog"
	}
}
