package com.team3.springProject.post;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.team3.springProject.comment.Comment;
import com.team3.springProject.comment.CommentService;
import com.team3.springProject.like.LikeTableService;
import com.team3.springProject.userTable.UserService;
import com.team3.springProject.userTable.UserTable;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

//@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {

	private final PostService postService;
	private final String uploadDir = "src/main/resources/static/uploads/";
	private final CommentService commentService;
	private final UserService userService;
	private final LikeTableService likeTableService;

	@GetMapping("/community")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page, Principal principal) {
		Page<Post> paging = this.postService.getList(page);
		model.addAttribute("paging", paging);

		UserTable userTable = null;
		if (principal != null) {
			String username = principal.getName();
			userTable = userService.getUser(username);
		}

		// 각 게시물에 대한 좋아요 여부 체크
		for (Post post : paging) {
			boolean loggedInUserLiked = likeTableService.filledLike(post, userTable);
			post.setLoggedInUserLiked(loggedInUserLiked);

			int likeCount = likeTableService.countLikesByPost(post);
			post.setLikeCount(likeCount);
		}
		model.addAttribute("paging", paging);
		model.addAttribute("userTable", userTable);

		return "post_list";
	}

	@GetMapping("/post/{postId}")
	public String commentList(@PathVariable("postId") Long postId, Model model) {
		List<Comment> commentList = commentService.getCommentsByPostId(postId);
		model.addAttribute("commentList", commentList);
		return "community";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/post/create")
	public String postCreate(Model model) {
		model.addAttribute("postForm", new PostForm());
		return "post_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/post/create")
	public String postCreate(@Valid PostForm postForm, BindingResult bindingResult, Principal principal)
			throws IOException {

		if (bindingResult.hasErrors()) {
			return "post_form";
		}

		String imagePath = null;
		MultipartFile imageFile = postForm.getImageFile();
		if (imageFile != null && !imageFile.isEmpty()) {
			imagePath = saveImage(imageFile);
		}

		UserTable userTable = this.userService.getUser(principal.getName());
		postService.createPost(postForm.getLocation(), postForm.getContent(), imagePath, userTable);

		return "redirect:/community";
	}

	private String saveImage(MultipartFile imageFile) throws IOException {
		byte[] bytes = imageFile.getBytes();
		Path path = Paths.get(uploadDir + imageFile.getOriginalFilename());
		Files.write(path, bytes);
		return "/uploads/" + imageFile.getOriginalFilename();
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/post/detail/{id}")
	public String detail(Model model, @PathVariable("id") Long id) {
		Post post = this.postService.getPost(id);
		model.addAttribute("post", post);
		return "post_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/post/update/{id}")
	public String getPostUpdate(@PathVariable("id") Long id, Model model) {
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

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/post/update/{id}")
	public String postUpdate(@ModelAttribute("postForm") @Valid PostForm postForm, BindingResult bindingResult,
			@PathVariable("id") Long id, Model model) throws IOException {
		if (bindingResult.hasErrors()) {
			return "post_modify";
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
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/post/delete/{id}")
	public String postDelete(Principal principal, @PathVariable("id") Long id) {
		Post post = this.postService.getPost(id);
		if (!post.getUserTable().getLoginId().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.postService.delete(post);
		return "redirect:/community";
	}

}