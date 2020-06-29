package com.example.iBlog.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.iBlog.domain.Article;
import com.example.iBlog.domain.Comment;
import com.example.iBlog.domain.User;
import com.example.iBlog.service.ArticleService;
import com.example.iBlog.service.CommentService;
import com.example.iBlog.service.UserService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private HttpSession session;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/addComment")
	public String addComment(Comment comment,int articleNumber) {
		User user = (User) session.getAttribute("user");
		Article article = articleService.findByArticleNumber(articleNumber);
		
		//找到文章作者
		User user2 = userService.findById(article.getUser().getUserNumber());
		comment.setUser(user);
		comment.setArticle(article);
		comment.setComTime(LocalDateTime.now());
		comment.setReplyUser(user2);
		commentService.save(comment);
		return "redirect:/main";
	}
	@PostMapping("/replyComment")
	public String addComment(Comment comment,int articleNumber,int replyUserNumber) {
		//评论者
		User user = (User) session.getAttribute("user");
		//被评论者
		User user2 = userService.findById(replyUserNumber);		
		
		Article article = articleService.findByArticleNumber(articleNumber);
		comment.setUser(user);
		comment.setArticle(article);
		comment.setComTime(LocalDateTime.now());
		comment.setReplyUser(user2);
		commentService.save(comment);
		return "redirect:/main";
	}
	@GetMapping("/deleteComment")
	public String deleteComment(int comNumber){
		commentService.deleteById(comNumber);
		return "redirect:/main";
	}
	public CommentController() {
		// TODO Auto-generated constructor stub
	}

}
