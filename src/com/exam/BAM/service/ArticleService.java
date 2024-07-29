package com.exam.BAM.service;

import java.util.List;

import com.exam.BAM.container.Container;
import com.exam.BAM.dao.ArticleDao;
import com.exam.BAM.dto.Article;

public class ArticleService {

	private ArticleDao articleDao;
	
	public ArticleService() {
		this.articleDao = Container.articleDao;
	}

	public void writeArticle(int memberId, String title, String body, int viewCnt) {
		articleDao.writeArticle(memberId, title, body, viewCnt);
	}

	public int getLastId() {
		return articleDao.getLastId();
	}

	public List<Article> getArticles(String searchKeyword) {
		return articleDao.getArticles(searchKeyword);
	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public void increaseViewCnt(Article foundArticle) {
		articleDao.increaseViewCnt(foundArticle);
	}

	public void modifyArticle(Article foundArticle, String title, String body) {
		articleDao.modifyArticle(foundArticle, title, body);
	}

	public void deleteArticle(Article foundArticle) {
		articleDao.deleteArticle(foundArticle);
	}
	
	public int getIdByCmd(String cmd) {
		String[] cmdBits = cmd.split(" ");
		
		try {
			int id = Integer.parseInt(cmdBits[2]);
			return id;
		} catch (NumberFormatException e) {
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}
	
}
