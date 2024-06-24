package com.team3.springProject.comment;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentController {
	
	private final CommentService commentService;
	
	@GetMapping("/community")
	public String comment(Model model) {
		List<Comment> commentList = this.commentService.getCommentList();
		model.addAttribute("commentList", commentList);
		return "comment_list";
	}
	
	@PostMapping("/community")
	public String commentCreate(@RequestParam(value="content") String content) throws IOException {		
		commentService.createComment(content);
		return "redirect:/community";
	}

	@GetMapping("/comment/delete/{id}")
	public String commentDelete(@PathVariable("id") Long id) {
		Comment comment = this.commentService.getComment(id);
        if (comment != null) {
            this.commentService.delete(comment);
        }
		return "redirect:/community";
	}
}
