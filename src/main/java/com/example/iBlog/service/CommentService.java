package com.example.iBlog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.iBlog.domain.Comment;
import com.example.iBlog.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}
	public List<Comment> findAllComment(){
		return commentRepository.findAll();
	}
	public CommentService() {
	}
	public void deleteByArticleNumber(int articleNumber) {
		 commentRepository.deleteByArticleNumber(articleNumber);
	}
    public  Comment deleteById(int comNumber){
		return commentRepository.deleteById(comNumber);
	}
}
