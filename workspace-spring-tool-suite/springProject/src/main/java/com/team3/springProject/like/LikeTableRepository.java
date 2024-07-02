package com.team3.springProject.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team3.springProject.post.Post;
import com.team3.springProject.userTable.UserTable;


@Repository
public interface LikeTableRepository extends JpaRepository<LikeTable, Integer> {

	// 게시물별로 좋아요가 몇개인지?
    int countByPost(Post post);
    
    // 사용자가 특정 글에 좋아요를 눌렀는지 여부를 확인하는 메서드
    boolean existsByUserTableAndPost(UserTable userTable, Post post);
    
    // Like 조회
    LikeTable findByUserTableAndPost(UserTable userTable, Post post);

    void deleteByUserTable(UserTable userTable);
    
}
