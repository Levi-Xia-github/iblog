package com.example.iBlog.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {

	 @Id
	 @GeneratedValue
	 private int comNumber; 
	 @Column(length=100)
	 private String comContent;
	 
	 private LocalDateTime comTime;
		
	 @ManyToOne
	 private User replyUser;
	
	 @ManyToOne
	 private User user;
	 
	 @ManyToOne
	 private Article article;
	 
	 public Comment() {
		// TODO Auto-generated constructor stub
	}
	public int getComNumber() {
		return comNumber;
	}
	public void setComNumber(int comNumber) {
		this.comNumber = comNumber;
	}
	public String getComContent() {
		return comContent;
	}
	public void setComContent(String comContent) {
		this.comContent = comContent;
	}
	public LocalDateTime getComTime() {
		return comTime;
	}
	public void setComTime(LocalDateTime comTime) {
		this.comTime = comTime;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public User getReplyUser() {
		return replyUser;
	}
	public void setReplyUser(User replyUser) {
		this.replyUser = replyUser;
	}

	

}
