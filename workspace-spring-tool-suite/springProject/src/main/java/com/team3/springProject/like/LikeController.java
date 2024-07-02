package com.team3.springProject.like;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team3.springProject.beach.BeachController;
import com.team3.springProject.post.Post;
import com.team3.springProject.post.PostService;
import com.team3.springProject.userTable.UserService;
import com.team3.springProject.userTable.UserTable;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {

	private static final Logger logger = Logger.getLogger(LikeController.class.getName());
    private final LikeTableService likeTableService;
    private final UserService userService;
    private final PostService postService;
    
    @PostMapping("/{postId}")
    public ResponseEntity<String> likePost(@PathVariable("postId") Long postId, Authentication authentication) {
    	
    	logger.info("들어오니?");
        // 인증된 사용자 정보 가져오기
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Authentication is required.");
        }

        UserTable userTable = userService.getUser(authentication.getName());
        if (userTable == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        // 게시물 ID로 게시물 조회
        Optional<Post> optionalPost = postService.getOptionalPost(postId);
        if (!optionalPost.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();

        // 좋아요 처리
        if (likeTableService.filledLike(post, userTable)) {
            // 이미 좋아요를 누른 경우 -> 좋아요 취소
            likeTableService.deleteLike(userTable, post);
            return ResponseEntity.ok("unliked");
        } else {
            // 좋아요 추가
            likeTableService.addLike(userTable, post);
            return ResponseEntity.ok("liked");
        }
    }
    


}