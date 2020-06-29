package com.example.iBlog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.iBlog.domain.Article;
import com.example.iBlog.repository.ArticleRepository;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
    public Article save(Article article) {
    	return articleRepository.save(article);
    }
    public List<Article> findAllArticle(){
    	return articleRepository.findAll();
    }
    public List<Article> findByUserName(String userName){
    	return articleRepository.findByUserName(userName);
    }
	//根据标签名称查询文章
    public List<Article> findByLabelName(String labelName){
    	return articleRepository.findByLabelName(labelName);
    }
    public Article findByArticleNumber(int articleNumber) {
    	return articleRepository.findByArticleNumber(articleNumber);
    }
    //根据名或内容
    public List<Article> findByArticleContain(String article){
    	return articleRepository.findByArticleContain(article);
    }
    //根据id删除文章
    public Article deleteById(int articleNumber) {
    	return articleRepository.deleteById(articleNumber);
    }
	public ArticleService() {
		// TODO Auto-generated constructor stub
	}
	//分页查询数据
    public List<Article> findAll(int page,int size){
        Sort sort = Sort.by(Sort.Direction.DESC,"articleTime");
        Pageable pageable = PageRequest.of(page,size,sort);
        return articleRepository.findAll(pageable).getContent();
    }

}
