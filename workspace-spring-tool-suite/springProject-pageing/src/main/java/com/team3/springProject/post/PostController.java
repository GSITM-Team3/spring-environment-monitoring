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
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		Page<Post> paging = this.postService.getList(page);
		model.addAttribute("paging", paging);
		return "post_list";
	}

	@GetMapping("/post/{postId}")
	public String commentList(@PathVariable("postId") int postId, Model model) {
		// Post post = this.postService.getComment(postId);
		List<Comment> commentList = commentService.getCommentsByPostId(postId);
		// model.addAttribute("post", post);
		model.addAttribute("commentList", commentList);
		return "community";
	}

	@GetMapping("/post/detail/{id}")
	public String detail(Model model, @PathVariable("id") int id) {
		Post post = this.postService.getPost(id);
		model.addAttribute("post", post);
		return "post_detail";
	}

	@GetMapping("/post/create")
	public String postCreate(Model model) {
		model.addAttribute("postForm", new PostForm());
		return "post_form";
	}

	@PostMapping("/post/create")
	public String postCreate(@Valid @ModelAttribute("postForm") PostForm postForm, BindingResult bindingResult,
			Model model) throws IOException {

		if (bindingResult.hasErrors()) {
			// model.addAttribute("postForm", postForm);
			return "post_form";
		}

		String imagePath = null;
		MultipartFile imageFile = postForm.getImageFile();
		if (imageFile != null && !imageFile.isEmpty()) {
			imagePath = saveImage(imageFile);
		}

		postService.createPost(postForm.getLocation(), postForm.getContent(), imagePath);

		return "redirect:/community";
	}

	private String saveImage(MultipartFile imageFile) throws IOException {
		byte[] bytes = imageFile.getBytes();
		Path path = Paths.get(uploadDir + imageFile.getOriginalFilename());
		Files.write(path, bytes);
		return "/uploads/" + imageFile.getOriginalFilename();
	}

	// 수정
	@GetMapping("/post/update/{id}")
	public String getPostUpdate(@PathVariable("id") int id, Model model) {
		Post post = postService.getPost(id);

		if (post == null) {
			return "error"; // 해당 ID의 게시물이 없는 경우 처리
		}

		// postForm 초기화 및 설정
		PostForm postForm = new PostForm();
		postForm.setLocation(post.getLocation());
		postForm.setContent(post.getContent());

		// postForm과 post를 모델에 추가
		model.addAttribute("postForm", postForm);
		model.addAttribute("post", post);
		model.addAttribute("postId", id);

		return "post_modify";
	}

	@PostMapping("/post/update/{id}")
	public String postUpdate(@ModelAttribute("postForm") @Valid PostForm postForm, BindingResult bindingResult,
			@PathVariable("id") int id, Model model) throws IOException {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}

		Post post = postService.getPost(id);

		if (post == null) {
			return "error"; // 해당 ID의 게시물이 없는 경우 처리
		}

		String imagePath = post.getImagePath(); // 기존 이미지 경로를 기본으로 설정

		// 새 이미지가 업로드된 경우 처리
		MultipartFile imageFile = postForm.getImageFile();
		if (imageFile != null && !imageFile.isEmpty()) {
			imagePath = saveImage(imageFile);
		}

		// 게시물 업데이트
		this.postService.updatePost(post, postForm.getLocation(), postForm.getContent(), imagePath);

		return String.format("redirect:/post/detail/%s", id);
		// 수정된 게시물 상세 페이지로 리다이렉트
	}

	// 삭제
	@GetMapping("/post/delete/{id}")
	public String postDelete(@PathVariable("id") int id) {
		Post post = this.postService.getPost(id);
		this.postService.delete(post);
		return "redirect:/community";
	}

}
