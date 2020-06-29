package com.example.iBlog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.iBlog.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

	//根据用户显示所有文章
	@Query(value="select a from Article a where a.user.userName=:userName")
	List<Article> findByUserName(@Param("userName")String userName);
	//根据文章名或内容模糊查询
	@Query(value="select a from Article a where a.articleName like %:article% or articleContent like  %:article%")
	List<Article> findByArticleContain(@Param("article") String article);
	//根据标签名查文章
	@Query(value="select a from Article a where a.label.labelName=:labelName")
	List<Article> findByLabelName(@Param("labelName") String labelName);
	//删除文章
	Article deleteById(int articleNumber);
	//根据id查文章
	Article findByArticleNumber(int ArticleNumber);
	//分页查询所有内容
	Page<Article> findAll(Pageable pageable);
	
}
