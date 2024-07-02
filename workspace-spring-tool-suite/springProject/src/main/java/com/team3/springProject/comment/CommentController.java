package com.team3.springProject.comment;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.team3.springProject.post.Post;
import com.team3.springProject.post.PostService;
import com.team3.springProject.userTable.UserService;
import com.team3.springProject.userTable.UserTable;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentController {

	private final CommentService commentService;
	private final PostService postService;
	private final UserService userService;

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/community")
	public String createComment(Model model, @Valid CommentForm commentForm, BindingResult bindingResult,
	                            Principal principal, @RequestParam(value = "page", defaultValue = "0" + "") int page,
	                            HttpServletRequest request) {		
	    if (bindingResult.hasErrors()) {	    	
	        return "redirect:/community?page=" + page;
	    }

	    Post post = postService.getPost(commentForm.getPostId());
	    UserTable userTable = this.userService.getUser(principal.getName());

	    this.commentService.createComment(post, commentForm.getContent(), userTable);

	    return "redirect:/community?page=" + page;
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/comment/delete/{id}")
	public String deleteComment(Principal principal, @PathVariable("id") Long id,
			@RequestParam(value = "page", defaultValue = "0" + "") int page) {
		Comment comment = this.commentService.getComment(id);
		if (!comment.getUserTable().getLoginId().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.commentService.delete(comment);
	    return "redirect:/community?page=" + page;
	}

}
