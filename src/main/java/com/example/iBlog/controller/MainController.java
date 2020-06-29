package com.example.iBlog.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.iBlog.domain.Article;
import com.example.iBlog.domain.Comment;
import com.example.iBlog.domain.User;
import com.example.iBlog.service.ArticleService;
import com.example.iBlog.service.CommentService;
import com.example.iBlog.service.LabelService;
import com.example.iBlog.service.UserService;

@Controller
public class MainController {
	@Autowired
	private HttpSession session;
	@Autowired
	private UserService userService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/")
	public String addLabel() {
		return "main/index";
	}
	@GetMapping("nologin")
	public String noLogin(Model model){
		//获取文章
		List<Article> list = articleService.findAll(0,4);
		model.addAttribute("listAllArticle",list);
		//获取评论
		List<Comment> list1 = commentService.findAllComment();
		model.addAttribute("listAllComment", list1);
		return "main/noLoginMain";
	}

	@GetMapping("/login")
	public String Login() {
		return "user/login";
	}
	//登录验证
//	@PostMapping("/login")
//	public String Login(String userName,String userPassword,Model model) {
//		//判断是否正确
//		if(userService.LoginJudge(userName,userPassword)) {
//			//根据用户名获取用户
//			User user = userService.findByUserName(userName);
//
//			if(user.isUserState()) {
//				session.setAttribute("user", user);
//
//				return "redirect:main";
//			}
//			else {
//				model.addAttribute("ErrorMessage", "您的账号被锁定");
//				return "user/login";
//			}
//		}
//		else
//			model.addAttribute("ErrorMessage", "抱歉，用户名或密码错误");
//			return "user/login";
//	}
//
	@GetMapping("/main")
	public String list(Model model) {

		if(session.getAttribute("user")!=null) {
			//获取文章
			List<Article> list = articleService.findAll(0,4);
			model.addAttribute("listAllArticle",list);
			//获取评论
			List<Comment> list1 = commentService.findAllComment();
			model.addAttribute("listAllComment", list1);
			
			return "main/main";
		}
		else {
			return "user/login";
		}	
	}
	
	//搜索
	@PostMapping("/searchResult")
	public String listSearchResult(String searchContent,Model model) {
		//显示所查询内容
		model.addAttribute("searchContent", searchContent);
		//显示查询用户
		List<User> listUser = userService.findByUserNameContaining(searchContent);		
		if(listUser.size()==0) {
			model.addAttribute("noHaveUser","暂未查询到此用户");
		}
		else {
			model.addAttribute("listUser", listUser);
		}
		//显示查询标签文章
		List<Article> listLabelArticle = articleService.findByLabelName(searchContent);
		if(listLabelArticle.size()==0) {
			model.addAttribute("noHaveLabelArticle","暂未查询含有此标签的文章");
		}
		else {
			model.addAttribute("listLabelArticle", listLabelArticle);
		}
		List<Article> listArticle = articleService.findByArticleContain(searchContent);
		if(listArticle.size()==0) {
			model.addAttribute("noHaveArticle","暂未查询含有此内容的文章");
		}
		else {
			model.addAttribute("listArticle", listArticle);
		}
		return "main/searchResult";
	}
	
}
