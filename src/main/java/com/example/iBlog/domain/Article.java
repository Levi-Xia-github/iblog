package com.example.iBlog.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Article {
    @Id
    @GeneratedValue
	private int articleNumber;
	@Column(length=100)
	private String articleName;
	@Column(length=1000)
	private String articleContent;
	
	private LocalDateTime articleTime;
	
	private LocalDateTime articleUpdateTime;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy="article")
	private Set<Comment> comments = new HashSet<>();
	
	@ManyToOne
	private Label label;

	public Article() {
		// TODO Auto-generated constructor stub
	}

	public int getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public LocalDateTime getArticleTime() {
		return articleTime;
	}

	public void setArticleTime(LocalDateTime articleTime) {
		this.articleTime = articleTime;
	}

	public LocalDateTime getArticleUpdateTime() {
		return articleUpdateTime;
	}

	public void setArticleUpdateTime(LocalDateTime articleUpdateTime) {
		this.articleUpdateTime = articleUpdateTime;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}
	

}
