package com.example.iBlog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.iBlog.domain.Label;

public interface LabelRepository extends JpaRepository<Label, Integer> {

	//根据标签查询某用户的标签
	@Query(value="select l from Label l where l.user.userName=:userName and l.labelState=true")
	List<Label> findByUserNameAndLabelState(@Param("userName") String userName);
	//根据标签名返回标签
	Label findByLabelName(String labelName);
	//根据用户id查看用户拥有的标签
	@Query(value="select l from Label l where l.user.userNumber=:userNumber")
	List<Label> findByUserNumber(@Param("userNumber")int userNumber);
	//禁用和启用标签
	@Transactional
	@Modifying
	@Query(value="update Label l set l.labelState=:newState where l.labelNumber=:labelNumber")
	public int UpdateState(@Param("newState")boolean newState,@Param("labelNumber")int labelNumber);
	//查询此用户标签是否已经存在
	@Query(value="select l from Label l where l.user.userName=:userName and l.labelName=:labelName")
	Label findByLabelNameAndUserName(@Param("userName")String userName,@Param("labelName")String labelName);
	


}
