package com.project.bookforeast.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int followId;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "following_id")
	private User followingUser;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "follower_id")
	private User followerUser;
}
