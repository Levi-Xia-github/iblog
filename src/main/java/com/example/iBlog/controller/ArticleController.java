package com.example.iBlog.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.iBlog.domain.Article;
import com.example.iBlog.domain.Comment;
import com.example.iBlog.domain.Label;
import com.example.iBlog.domain.User;
import com.example.iBlog.service.ArticleService;
import com.example.iBlog.service.CommentService;
import com.example.iBlog.service.LabelService;

@Controller
@RequestMapping("/article")
public class ArticleController {
	@Autowired
	private HttpSession session;
	@Autowired
	private ArticleService articleService;
	@Autowired 
	private LabelService labelService;
	@Autowired
	private CommentService commentService;
	
	public ArticleController() {
		// TODO Auto-generated constructor stub
	}
	@GetMapping("/addArticle")
	public String list(Model model) {
		//显示标签
		User user = (User) session.getAttribute("user");		
		List<Label> list = labelService.findByUserNameAndLabelState(user.getUserName());
		model.addAttribute("listEnableLabel",list);
		
		//显示当前用户的所有文章
		
		List<Article> list1 = articleService.findByUserName(user.getUserName());
		model.addAttribute("listUserArticle",list1);
		
		//显示评论
		List<Comment> list2 = commentService.findAllComment();
		model.addAttribute("listAllComment", list2);
		
		
		return "article/addArticle";
	}
	@PostMapping("addArticle")
	public String addArticle(Article article,String labelName) {
		User user = (User) session.getAttribute("user");
		article.setUser(user);
		article.setArticleTime(LocalDateTime.now());
		Label label = labelService.findByLabelNameAndUserName(user.getUserName(), labelName);
		article.setLabel(label);
		articleService.save(article);
		return "redirect:addArticle";
	}
	
	@GetMapping("deleteArticle")
	public String deleteArticle(int articleNumber) {
		
		//先删除评论
		commentService.deleteByArticleNumber(articleNumber);
		//在删除文章
		articleService.deleteById(articleNumber);
		return "redirect:addArticle";
	}
	@GetMapping("findAllByPage")
	public String findAllByPage(int pagenumber,Model model){
		List<Article> list = articleService.findAll(pagenumber,4);
		model.addAttribute("listAllArticle",list);
		List<Comment> list1 = commentService.findAllComment();
		model.addAttribute("listAllComment", list1);
		return "main/main";
	}
	@PostMapping("findAllByPage")
	public String findAllByPage1(int pagenumber,Model model){
		List<Article> list = articleService.findAll(pagenumber,4);
		model.addAttribute("listAllArticle",list);
		List<Comment> list1 = commentService.findAllComment();
		model.addAttribute("listAllComment", list1);
		return "main/main";
	}
	@GetMapping("noLoginFindPage")
	public String findAllByPage2(int pagenumber,Model model){
		List<Article> list = articleService.findAll(pagenumber,4);
		model.addAttribute("listAllArticle",list);
		List<Comment> list1 = commentService.findAllComment();
		model.addAttribute("listAllComment", list1);
		return "main/noLoginMain";
	}
}
