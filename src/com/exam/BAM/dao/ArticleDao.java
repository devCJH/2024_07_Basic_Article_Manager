package com.exam.BAM.dao;

import java.util.ArrayList;
import java.util.List;

import com.exam.BAM.container.Container;
import com.exam.BAM.dto.Article;
import com.exam.BAM.util.Util;

public class ArticleDao {
	private List<Article> articles;
	private int lastId;
	
	public ArticleDao() {
		this.articles = Container.articles;
		this.lastId = 0;
	}

	public void writeArticle(int memberId, String title, String body, int viewCnt) {
		lastId++;
		articles.add(new Article(lastId, Util.getDateStr(), memberId, title, body, viewCnt));
	}

	public int getLastId() {
		return lastId;
	}

	public List<Article> getArticles(String searchKeyword) {
		if (searchKeyword.length() > 0) {
			System.out.println("검색어 : " + searchKeyword);

			List<Article> printArticles = new ArrayList<>();
			
			for (Article article : articles) {
				if (article.getTitle().contains(searchKeyword)) {
					printArticles.add(article);
				}
			}
			
			return printArticles;
		}
		return articles;
	}

	public Article getArticleById(int id) {
		for (Article article : articles){
			if (id == article.getId()) {
				return article;
			}
		}
		return null;
	}

	public void increaseViewCnt(Article foundArticle) {
		foundArticle.increaseViewCnt();
	}

	public void modifyArticle(Article foundArticle, String title, String body) {
		foundArticle.setTitle(title);
		foundArticle.setBody(body);
	}

	public void deleteArticle(Article foundArticle) {
		articles.remove(foundArticle);
	}
}
