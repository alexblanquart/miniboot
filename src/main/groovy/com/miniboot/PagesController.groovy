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
		postsRepository.findAllByCategory("blog", new PageRequest(0, 10, Direction.DESC, "date"))
	}
	
	def recentPosts(){
		postsRepository.findAll(new PageRequest(0, 4, Direction.DESC, "date"))
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
	public String index(Map<String, Object> model) {
		model.put("recentBlog", recentBlogPosts())
		"index"
	}
	
	@RequestMapping("/posts/{id}")
	public String post(Map<String, Object> model, @PathVariable("id") Post post){
		model.put("post", post)
		model.put("content", content(post))
		model.put("recentPosts", recentPosts())
		model.put("allTags", allTags())
		"post"
	}
}
