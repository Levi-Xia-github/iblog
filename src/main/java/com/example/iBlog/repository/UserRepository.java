package com.example.iBlog.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.iBlog.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUserNumber(int userNumber);
	//判断用户是否存在
	boolean existsByUserName(String userName);
	//判断用户账号密码是否正确
	boolean existsByUserNameAndUserPassword(String userName,String userPassword);
	//判断用户状态
	//boolean existsByUserNameAndUserState(String userName,boolean userState);
	//根据用户名查询整个用户
	User findByUserName(String userName);
	//判断用户权限
	boolean existsByUserNameAndUserIdentity(String userName,String userIdentity);
	//根据用户姓名模糊查询返回list
	@Query(value="select u from User u where u.userLoginname like %:userLoginname%")
	List<User> findByUserNameContaining(@Param("userLoginname") String userLoginname);
	//根据用户姓名或用户名模糊查询返回list
	@Query(value = "select u from User u where u.userLoginname like %:userInfo% or u.userName like %:userInfo%")
	List<User> findByUserNameAndUserLoginnameContaining(@Param("userInfo") String userInfo);
	//修改密码
	@Transactional
	@Modifying
	@Query(value="update User u set u.userPassword=:newPassword where u.userName=:userName")
	public int UpdatePasswprd(@Param("newPassword")String userPassword,@Param("userName")String userName);	
	
	//禁用或启用用户
	@Transactional
	@Modifying
	@Query(value="update User u set u.userState=:newState where u.userNumber=:userNumber")
	public int UpdateState(@Param("newState")boolean newState,@Param("userNumber")int userNumber);
	
	//设置或取消管理员身份
	@Transactional
	@Modifying
	@Query(value="update User u set u.userIdentity=:newIdentity where u.userNumber=:userNumber")
	public int UpdateIdentity(@Param("newIdentity")String newIdentity,@Param("userNumber")int userNumber);




}
