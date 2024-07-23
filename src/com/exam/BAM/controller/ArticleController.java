package com.exam.BAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.exam.BAM.dto.Article;
import com.exam.BAM.util.Util;

public class ArticleController extends Controller {

	private List<Article> articles;
	
	public ArticleController(Scanner sc) {
		this.sc = sc;
		this.lastId = 0;
		this.articles = new ArrayList<>();
	}

	@Override
	public void doAction(String cmd, String methodName) {
		this.cmd = cmd;
		
		switch (methodName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다");
		}
	}
	
	private void doWrite() {
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
		
		lastId++;
        
		Article article = new Article(lastId, Util.getDateStr(), title, body, 0);
		
		articles.add(article);
		
		System.out.println(lastId + "번 글이 생성되었습니다");
	}
	
	private void showList() {
		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}
		
		List<Article> printArticles = articles;
		
		String searchKeyword = cmd.substring("article list".length()).trim();
		
		if (searchKeyword.length() > 0) {
			System.out.println("검색어 : " + searchKeyword);

			printArticles = new ArrayList<>();
			
			for (Article article : articles) {
				if (article.getTitle().contains(searchKeyword)) {
					printArticles.add(article);
				}
			}
			
			if (printArticles.size() == 0) {
				System.out.println("검색결과가 없습니다");
				return;
			}
		}
		
		System.out.println("번호	|	제목	|		작성일		|	조회수");
		
		for (int i = printArticles.size() - 1; i >= 0; i--) {
			Article article = printArticles.get(i);
			System.out.printf("%d	|	%s	|	%s	|	%d\n", article.getId(), article.getTitle(), article.getRegDate(), article.getViewCnt());
		}
	}

	private void showDetail() {
		int id = getIdByCmd(cmd);
		
		if (id == 0) {
			System.out.println("명령어가 올바르지 않습니다");
			return;
		}
		
		Article foundArticle = getArticleById(id);
		
		if (foundArticle == null) {
			System.out.println(id + "번 게시물은 존재하지 않습니다");
			return;
		}
		
		foundArticle.increaseViewCnt();
		
		System.out.printf("번호 : %d\n", foundArticle.getId());
		System.out.printf("작성일 : %s\n", foundArticle.getRegDate());
		System.out.printf("제목 : %s\n", foundArticle.getTitle());
		System.out.printf("내용 : %s\n", foundArticle.getBody());
		System.out.printf("조회수 : %d\n", foundArticle.getViewCnt());
		
	}

	private void doModify() {
		int id = getIdByCmd(cmd);
		
		if (id == 0) {
			System.out.println("명령어가 올바르지 않습니다");
			return;
		}

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.println(id + "번 게시물은 존재하지 않습니다");
			return;
		}

		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine();

		foundArticle.setTitle(title);
		foundArticle.setBody(body);

		System.out.println(id + "번 게시물을 수정했습니다");
	}

	private void doDelete() {
		int id = getIdByCmd(cmd);
		
		if (id == 0) {
			System.out.println("명령어가 올바르지 않습니다");
			return;
		}
		
		Article foundArticle = getArticleById(id);
		
		if (foundArticle == null) {
			System.out.println(id + "번 게시물은 존재하지 않습니다");
			return;
		}
		
		articles.remove(foundArticle);
		
		System.out.println(id + "번 게시물을 삭제했습니다");
	}
	
	private int getIdByCmd(String cmd) {
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

	private Article getArticleById(int id) {
		for (Article article : articles){
			if (id == article.getId()) {
				return article;
			}
		}
		return null;
	}
	
	@Override
	public void makeTestData() {
		System.out.println("테스트용 게시물 데이터 3개를 생성했습니다");
		for (int i = 1; i <= 3; i++) {
			articles.add(new Article(++lastId, Util.getDateStr(), "제목" + i, "내용" + i, i * 10));
		}		
	}
}
