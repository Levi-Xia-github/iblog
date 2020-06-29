package com.example.iBlog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.iBlog.domain.Comment;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

	//删除评论
	Comment deleteById(int comNumber);
	//显示此文章所有评论
	@Query(value="select c from Comment c where c.article.articleNumber=:articleNumber")
	List<Comment> findAllByArticleNumber(@Param("articleNumber")int articleNumber);
	//删除某篇文章所有评论
	@Transactional
	@Modifying
	@Query(value="delete from Comment c where c.article.articleNumber=:articleNumber")
	void deleteByArticleNumber(@Param("articleNumber")int articleNumber);
}
