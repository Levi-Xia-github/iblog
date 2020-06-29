package com.example.iBlog.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.iBlog.domain.Article;
import com.example.iBlog.domain.Comment;
import com.example.iBlog.service.ArticleService;
import com.example.iBlog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.iBlog.domain.Label;
import com.example.iBlog.domain.User;
import com.example.iBlog.service.LabelService;

@Controller
@RequestMapping("/label")
public class LabelController {
	@Autowired
	 private HttpSession session;
    @Autowired
    private LabelService labelService;
    @Autowired
	private ArticleService articleService;
    @Autowired
	private CommentService commentService;
	
	@GetMapping("/addLabel")
	public String list(Model model) {
		User user = (User) session.getAttribute("user");		
		List<Label> list = labelService.findByUserNumber(user.getUserNumber());
		model.addAttribute("listAlllabel",list);
		return "label/addLabel";
	}
	@PostMapping("/addLabel")
	public String addLabel(Label label,Model model) {
		User user = (User) session.getAttribute("user");
		label.setUser(user);
		if(labelService.findByLabelNameAndUserName(user.getUserName(), label.getLabelName())!=null) {		
			model.addAttribute("labelMessage", "已有此标签");
			List<Label> list = labelService.findByUserNumber(user.getUserNumber());
			model.addAttribute("listAlllabel",list);
			return "label/addLabel";
		}
		else {
			labelService.addLabel(label);	   
			return "redirect:addLabel";
		}
		
	}
	@GetMapping("/updateState")
	public String updateState(int labelNumber) {
		Label label = labelService.getOne(labelNumber);
		if(label.getLabelState()==false) {
			boolean State=true;
			labelService.UpdateState(State, labelNumber);		
		}
		else {
			boolean State = false;
			labelService.UpdateState(State, labelNumber);	
		}
		return "redirect:addLabel";
		
		
	}
	@GetMapping("/deleteLabel")
	public String deleteLabel(int labelNumber,Model model) {
		User user = (User) session.getAttribute("user");
		Label label = labelService.getOne(labelNumber);

		//判断是否被使用
		if(label.getArticles().size()==0){
			labelService.deleteByLabelNumber(labelNumber);
		}else{
			model.addAttribute("labelMessage", "标签已被使用");
		}
		List<Label> list = labelService.findByUserNumber(user.getUserNumber());
		model.addAttribute("listAlllabel",list);
		return "label/addLabel";


	}
	@GetMapping("/mainLabelFindArticle")
	public  String mainLabelFindArticle(String labelName,Model model){
		model.addAttribute("labelName",labelName);
		Label label = labelService.findByLabelName(labelName);
		//判断标签是否存在
		if(label==null){
			return "main/mainLabelFindNoArticle";
		}
		else{
			List<Article> articlelist = articleService.findByLabelName(label.getLabelName());
			model.addAttribute("articlelist",articlelist);
			//判断文章是否存在
			if(articlelist.size()==0){
				return "main/mainLabelFindNoArticle";
			}
			else{
				//获取评论
				List<Comment> list1 = commentService.findAllComment();
				model.addAttribute("listAllComment", list1);
				return "main/mainLabelFindArticle";
			}

		}
	}
}
