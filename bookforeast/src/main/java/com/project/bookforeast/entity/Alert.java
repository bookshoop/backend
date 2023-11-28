package com.project.bookforeast.entity;

import java.util.Date;

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
public class Alert {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int alertId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private String category;
	private String message;
	private Date registDt;
	
	
	
}
