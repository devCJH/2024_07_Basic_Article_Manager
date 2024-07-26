package com.exam.BAM.controller;

import java.util.List;
import java.util.Scanner;

import com.exam.BAM.dto.Article;
import com.exam.BAM.service.ArticleService;
import com.exam.BAM.service.MemberService;

public class ArticleController extends Controller {

	private ArticleService articleService;
	private MemberService memberService;
	
	public ArticleController(Scanner sc) {
		this.sc = sc;
		this.articleService = new ArticleService();
		this.memberService = new MemberService();
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
		
		articleService.writeArticle(loginedMember.getId(), title, body, 0);
		
		int id = articleService.getLastId();
		
		System.out.println(id + "번 글이 생성되었습니다");
	}
	
	private void showList() {
		String searchKeyword = cmd.substring("article list".length()).trim();
		
		List<Article> printArticles = articleService.getArticles(searchKeyword);
		
		if (printArticles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}
		
		System.out.println("번호	|	제목	|		작성일		|	작성자	|	조회수");
		
		for (int i = printArticles.size() - 1; i >= 0; i--) {
			Article article = printArticles.get(i);
			
			String writerName = memberService.getWriterName(article.getMemberId());
			
			System.out.printf("%d	|	%s	|	%s	|	%s	|	%d\n", article.getId(), article.getTitle(), article.getRegDate(), writerName, article.getViewCnt());
		}
	}

	private void showDetail() {
		int id = articleService.getIdByCmd(cmd);
		
		if (id == 0) {
			System.out.println("명령어가 올바르지 않습니다");
			return;
		}
		
		Article foundArticle = articleService.getArticleById(id);
		
		if (foundArticle == null) {
			System.out.println(id + "번 게시물은 존재하지 않습니다");
			return;
		}
		
		articleService.increaseViewCnt(foundArticle);
		
		String writerName = memberService.getWriterName(foundArticle.getMemberId());
		
		System.out.printf("번호 : %d\n", foundArticle.getId());
		System.out.printf("작성일 : %s\n", foundArticle.getRegDate());
		System.out.printf("작성자 : %s\n", writerName);
		System.out.printf("제목 : %s\n", foundArticle.getTitle());
		System.out.printf("내용 : %s\n", foundArticle.getBody());
		System.out.printf("조회수 : %d\n", foundArticle.getViewCnt());
		
	}

	private void doModify() {
		int id = articleService.getIdByCmd(cmd);
		
		if (id == 0) {
			System.out.println("명령어가 올바르지 않습니다");
			return;
		}

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.println(id + "번 게시물은 존재하지 않습니다");
			return;
		}

		if (loginedMember.getId() != foundArticle.getMemberId()) {
			System.out.println("해당 게시물에 대한 권한이 없습니다");
			return;
		}
		
		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine();

		articleService.modifyArticle(foundArticle, title, body);

		System.out.println(id + "번 게시물을 수정했습니다");
	}

	private void doDelete() {
		int id = articleService.getIdByCmd(cmd);
		
		if (id == 0) {
			System.out.println("명령어가 올바르지 않습니다");
			return;
		}
		
		Article foundArticle = articleService.getArticleById(id);
		
		if (foundArticle == null) {
			System.out.println(id + "번 게시물은 존재하지 않습니다");
			return;
		}
		
		if (loginedMember.getId() != foundArticle.getMemberId()) {
			System.out.println("해당 게시물에 대한 권한이 없습니다");
			return;
		}
		
		articleService.deleteArticle(foundArticle);
		
		System.out.println(id + "번 게시물을 삭제했습니다");
	}
	
	@Override
	public void makeTestData() {
		System.out.println("테스트용 게시물 데이터 3개를 생성했습니다");
		for (int i = 1; i <= 3; i++) {
			articleService.writeArticle((int) (Math.random() * 3) + 1, "제목" + i, "내용" + i, i * 10);
		}		
	}
}
