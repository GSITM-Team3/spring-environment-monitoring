package com.team3.springProject.like;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.team3.springProject.post.Post;
import com.team3.springProject.userTable.UserTable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeTableService {
	private final LikeTableRepository likeTableRepository;
	
	// 좋아요 추가 메서드(누가,어느글에/void)
    public void addLike(UserTable userTable, Post post) {
        LikeTable like = new LikeTable();
        like.setUserTable(userTable);
        like.setPost(post);
        like.setCreatedAt(LocalDateTime.now());
        likeTableRepository.save(like);
    }

    // 게시물별 좋아요 수 조회 메서드(어느글에/몇개?)
    public int countLikesByPost(Post post) {
    	return likeTableRepository.countByPost(post);
    }
    
    // 어느 글에 누가 좋아요를 눌렀는지 여부를 확인하는 메서드
    public boolean filledLike(Post post, UserTable userTable) {
        return likeTableRepository.existsByUserTableAndPost(userTable, post);
    }


 // 좋아요 삭제 메서드
    public void deleteLike(UserTable userTable, Post post) {
        LikeTable like = likeTableRepository.findByUserTableAndPost(userTable, post);
        if (like != null) {
            likeTableRepository.delete(like);
        }
    }

}
