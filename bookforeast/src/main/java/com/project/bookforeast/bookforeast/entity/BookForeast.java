package com.project.bookforeast.bookforeast.entity;

import java.util.Date;

import com.project.bookforeast.file.entity.FileGroup;
import com.project.bookforeast.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookForeast {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookForeastId;
	
	private String title;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "content_file_id")
	private FileGroup contentFileGroup;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "main_image_id")
	private FileGroup mainImageFileGroup;
	
	private int state;
	
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private User registUser;
	
	@ManyToOne
	@JoinColumn(name = "updater_id")
	private User updateUser;
	
	private Date registDt;
	private Date updateDt;
	
}
