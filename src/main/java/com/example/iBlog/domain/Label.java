package com.example.iBlog.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Label {

	 @Id
	 @GeneratedValue
	 private int labelNumber;
	 @Column(length=30)
	 private String labelName;
	 
	 private boolean labelState = true;
	 
	 @ManyToOne
	 private User user;
	 
	 @OneToMany(mappedBy="label")
	private Set<Article> articles = new HashSet<>();
	 
	 public  Label() {
	 }
	public int getLabelNumber() {
		return labelNumber;
	}
	public void setLabelNumber(int labelNumber) {
		this.labelNumber = labelNumber;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public boolean getLabelState() {
		return labelState;
	}
	public void setLabelState(boolean labelState) {
		this.labelState = labelState;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<Article> getArticles() {
		return articles;
	}
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
	
}
