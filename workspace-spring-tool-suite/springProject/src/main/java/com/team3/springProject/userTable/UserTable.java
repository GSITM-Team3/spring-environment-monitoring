package com.team3.springProject.userTable;

import java.time.LocalDateTime;
import java.util.List;

import com.team3.springProject.post.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserTable {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String loginId;
	
	@Column(nullable=false)
	private String password;
	
	private String name;
	
	private String phoneNumber;
	
	private String gender;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
    @OneToMany(mappedBy = "userTable", cascade = CascadeType.REMOVE)
    private List<Post> posts;
	
	
	
}
