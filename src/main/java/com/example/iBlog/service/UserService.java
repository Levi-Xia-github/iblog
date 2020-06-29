package com.example.iBlog.service;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.iBlog.domain.User;
import com.example.iBlog.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private HttpSession session;
	@Autowired
	private UserRepository userRepository;
	
	public UserService() {}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	public List<User> findAllUser(){
		return userRepository.findAll();
	}
	//判断登录
	public boolean LoginJudge(String userName,String userPassword) {
		return userRepository.existsByUserNameAndUserPassword(userName, userPassword);
	}
	//判断管理员
	public boolean IdentityJudge(String userName,String userIdentity) {
		return userRepository.existsByUserNameAndUserIdentity(userName, userIdentity);
	}
	//注册判断此用户是否存在
	public boolean existsUser(String userName) {
		return userRepository.existsByUserName(userName);
	}
	//根据用户名查询整个用户
	public  User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public User getOne(int id) {
		return userRepository.getOne(id);
	}
	//模糊查询
	public List<User> findByUserNameContaining(String userName){
		return userRepository.findByUserNameContaining(userName);
	}
	public User findById(int userNumber) {
		return userRepository.findByUserNumber(userNumber);
	}
	public int updateState(boolean newState,int userNumber) {

		return userRepository.UpdateState(newState, userNumber);
	}
	public int updateIdentity(String newIdentity,int userNumber) {
		return userRepository.UpdateIdentity(newIdentity, userNumber);
	}
	public  List<User> findByUserNameAndUserLoginnameContaining(String userInfo){
		return userRepository.findByUserNameAndUserLoginnameContaining(userInfo);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		User user = userRepository.findByUserName(userName);

		if (user != null) {

			if (user.isUserState()) {
				session.setAttribute("user", user);
				return user;
			}
			else {

				throw new BadCredentialsException("您的账号被锁定");
			}
		}
		else {

			throw new BadCredentialsException("抱歉，用户名或密码错误");
			}

		}

	public User addUser(User user){
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		user.setUserPassword(encoder.encode((user.getPassword())));
		return userRepository.save(user);
	}

}
