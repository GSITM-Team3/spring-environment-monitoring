package com.team3.springProject.comment;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3.springProject.DataNotFoundException;
import com.team3.springProject.post.Post;
import com.team3.springProject.post.PostService;

import lombok.RequiredArgsConstructor;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/community")
    public String createComment(@RequestParam("postId") int postId,
                                @RequestParam("content") String content) {
        Post post = postService.getPost(postId);
        if (post != null) {
            commentService.createComment(post, content);
        } else {
            throw new DataNotFoundException("Post not found with id: " + postId);
        }
        return "redirect:/community"; 
    }
    
    @PostMapping("/comment/create")
    public String createComment(@RequestParam("postId") int postId, @RequestParam("content") String content, Model model) {
        // 댓글 생성 후 해당 게시물의 댓글 리스트를 다시 가져와서 모델에 추가
        List<Comment> commentList = commentService.getCommentsByPostId(postId);
        Post post = postService.getPost(postId);

        // 모델에 댓글 리스트와 게시물을 추가하여 뷰에 전달
        model.addAttribute("commentList", commentList);

        this.commentService.createComment(post, content);
        return "community"; // 댓글 생성 후 해당 게시물로 리다이렉트
    }
	
	@GetMapping("/comment/delete/{id}")
	public String commentDelete(@PathVariable("id") int id) {
		Comment comment = this.commentService.getComment(id);
		if(comment !=null) {
			this.commentService.delete(comment);
		}
		return "redirect:/community";
	}
}
