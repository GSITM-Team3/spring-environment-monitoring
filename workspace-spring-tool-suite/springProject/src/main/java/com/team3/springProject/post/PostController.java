package com.team3.springProject.post;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.team3.springProject.comment.Comment;
import com.team3.springProject.comment.CommentService;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;


//@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {
	
	private final PostService postService;
	private final String uploadDir = "src/main/resources/static/uploads/";
	private final CommentService commentService;
	
	@GetMapping("/community")
	public String list(Model model) {
		List<Post> postList = this.postService.getPostList();
		model.addAttribute("postList", postList);
		return "post_list";		
	}	

	@GetMapping("/post/{postId}")
	public String commentList(@PathVariable("postId") Long postId, Model model) {
        //Post post = this.postService.getComment(postId);
        List<Comment> commentList = commentService.getCommentsByPostId(postId);
        //model.addAttribute("post", post);
        model.addAttribute("commentList", commentList);
		return "community";		
	}	
	
	@GetMapping("/post/detail/{id}")
	public String detail(Model model, @PathVariable("id") Long id) {
		Post post = this.postService.getPost(id);
		model.addAttribute("post", post);
		return "post_detail";
	}
	
	@GetMapping("/post/create")
	public String postCreate() {
		return "post_form";
	}

	@PostMapping("/post/create")
	public String postCreate(
				//@Valid PostForm postForm, BindingResult bindingResult,
				@RequestParam(value="location") String location,
				@RequestParam(value="content") String content,
				@RequestParam(value="imageFile") 
				MultipartFile imageFile, Model model) throws IOException {
		
//		if (bindingResult.hasErrors()) {
//			model.addAttribute("postForm", postForm);
//			return "post_form";
//		}
		
		String imagePath = null;
		if (!imageFile.isEmpty()) {
			imagePath = saveImage(imageFile);
			//postForm.setImagePath(imagePath);
		}
		
		//postService.createPost(postForm.getLocation(), postForm.getContent(), postForm.getImagePath());
		postService.createPost(location, content, imagePath);
		return "redirect:/community";
	}
	
	private String saveImage(MultipartFile imageFile) throws IOException {
		byte[] bytes = imageFile.getBytes();
		Path path = Paths.get(uploadDir + imageFile.getOriginalFilename());
		Files.write(path, bytes);
		return "/uploads/" + imageFile.getOriginalFilename();
	}	
	
	// 수정
//	@GetMapping("/post/update/{id}")
//	public String postUpdate(@PathVariable("id") Long id) {
//		return "post_form";
//	}
//	
//	@PostMapping("/post/update/{id}")
//	public String postUpdate(Post post, @RequestParam(value="content") String content,
//				@RequestParam(value="imagePath") String imagePath) {
//		postService.updatePost(post, content, imagePath);
//		return "rediredct:/post_detail";
//	}
	
	// 수정 (진행중)
	@GetMapping("/post/update/{id}")
	public String postUpdate(@PathVariable("id") Long id) {
		return "post_detail";
	}
	
	@PostMapping("/post/update/{id}")
	public String postUpdate(PostForm postForm, @PathVariable("id") Long id) {
		Post post = this.postService.getPost(id);
		this.postService.updatePost(post, postForm.getLocation(),
				  postForm.getContent(), postForm.getImagePath());

		return String.format("rediredct:/post/detail/%s", id);
	}
	
	// 삭제
	@GetMapping("/post/delete/{id}")
	public String postDelete(@PathVariable("id") Long id) {
		Post post = this.postService.getPost(id);
		this.postService.delete(post);
		return "redirect:/community";
	}

}
