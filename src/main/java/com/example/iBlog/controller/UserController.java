package com.example.iBlog.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.iBlog.domain.User;
import com.example.iBlog.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	 private HttpSession session;
	@Autowired
	private UserService userService;
	
	@GetMapping("/addUser")
	public String addUser(User user) {
		return "user/addUser";
	}
	@PostMapping("/addUser")
	public String addUser(@Valid User user, BindingResult result, Model model) {
		if(result.hasErrors()){
			return "user/addUser";
		} else{
			if(userService.existsUser(user.getUserName())) {
				model.addAttribute("addUserMessage","已存在此用户名");
				return "user/addUser";
			} else{
				userService.addUser(user);
				return "user/login";
			}
		}
	}
	@GetMapping("/myCenter")
	public String myCenter() {
		return "user/myCenter";
	}
	@GetMapping("/myFouces")
	public String myFouces() {
		return "user/myFouces";
	}
	@GetMapping("/resetMyInfo")
	public String resetMyInfo() {
		return "user/resetMyInfo";
	}
	@PostMapping("/resetMyInfo")
	public String resetMyInfo(String userLoginname,String userTel,String userBirth,String userGender,String userMail,String userDescribe) {
		User user = userService.getOne(((User)session.getAttribute("user")).getUserNumber());
		LocalDate birth = LocalDate.parse(userBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));		
		user.setUserLoginname(userLoginname);
		user.setUserBirth(birth);
		user.setUserGender(userGender);
		user.setUserMail(userMail);
		user.setUserTel(userTel);
		user.setUserDescribe(userDescribe);		
		userService.save(user);
		session.setAttribute("user", user);
		return "user/resetMyInfo";
	}
	
	@GetMapping("/resetPassword")
	public String resetPassword() {			
		return "user/resetPassword";
	}
	@PostMapping("/resetPassword")
	public String resetPassword(String userPassword,String olduserPassword,String userPassword2,Model model) {
		
		User user = userService.getOne(((User)session.getAttribute("user")).getUserNumber());
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		if(   encoder.matches(olduserPassword,user.getUserPassword())    ) {
			
			if(userPassword.equals(userPassword2)) {
				user.setUserPassword(userPassword);
				userService.addUser(user);
				session.setAttribute("user", user);
				return "redirect:/login";
			}
			else {
				model.addAttribute("PasswordMessage", "两次密码不一致，请重新输入");
				return "user/resetPassword";
		     }
		}
		else {
			model.addAttribute("PasswordMessage2", "原密码错误,请重新输入或联系管理员");
			return "user/resetPassword";
		}				
	}

	@GetMapping("/admin")
	public String list(Model model) {
		User user = (User) session.getAttribute("user");
			List<User> list = userService.findAllUser();
			model.addAttribute("listAllUser", list);
			return "user/admin";
	}

	@GetMapping("/adminUpdateUserInfo")
	public String adminUpdateUserInfo(int userNumber){
		//获取用户，存入session
		User user = userService.getOne(userNumber);
		session.setAttribute("ad_user",user);
		return "user/adminUpdateUserInfo";
	}
	@PostMapping("/adminUpdateUserInfo")
	public String adminUpdateUserInfo(String userBirth,String userLoginname,String userGender,String userMail,String userTel,String userDescribe){
		User user = userService.getOne(((User)session.getAttribute("ad_user")).getUserNumber());
		LocalDate birth = LocalDate.parse(userBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		user.setUserLoginname(userLoginname);
		user.setUserBirth(birth);
		user.setUserGender(userGender);
		user.setUserMail(userMail);
		user.setUserTel(userTel);
		user.setUserDescribe(userDescribe);
		userService.save(user);
		session.setAttribute("ad_user",user);
		return "user/adminUpdateUserInfo";
	}
	@GetMapping("/adminRefreshPassword")
	public String adminRefreshPassword(int userNumber){
		User user = userService.getOne(userService.findById(userNumber).getUserNumber());
		user.setUserPassword("000000");
		userService.addUser(user);
		return "user/adminUpdateUserInfo";
	}

	@PostMapping("searchUser")
	public String searchUser(String searchUser,Model model){
		List<User> list = userService.findByUserNameAndUserLoginnameContaining(searchUser);
		model.addAttribute("searchUser",list);
		return "user/searchUser";
	}
	@GetMapping("/adminUpdateInfo")
	public String adminUpdateInfo(int userNumber) {
		return "redirect:/user/admin";
	}
	@GetMapping("/adminUpdateState")
	public String adminUpdateState(int userNumber) {
		User user = userService.findById(userNumber);
		if(user.isUserState()) {
			boolean newState=false;
			userService.updateState(newState, userNumber);
			return "redirect:/user/admin";
		}
		else {
			boolean newState=true;
			userService.updateState(newState, userNumber);
			return "redirect:/user/admin";
		}
		
	}
	@GetMapping("/adminUpdateIdentity")
	public String adminUpdateIdentity(int userNumber) {
		User user = userService.findById(userNumber);
		if(user.getUserIdentity().equals("管理员")) {			
			userService.updateIdentity("普通用户", userNumber);
			return "redirect:/user/admin";
		}
		else {
			userService.updateIdentity("管理员", userNumber);
			return "redirect:/user/admin";
		}
	}

}
