import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static int lastArticleId;
	static List<Article> articles;
	
	static {
		lastArticleId = 0;
		articles = new ArrayList<>();
	}
	
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		
		makeTestData();
		
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.printf("명령어) ");
			
			String cmd = sc.nextLine().trim();
			
			if (cmd.equals("exit")) {
				break;
			} 
			
			if (cmd.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}
			
			if (cmd.equals("article write")) {
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();
				
				lastArticleId++;
		        
				Article article = new Article(lastArticleId, Util.getDateStr(), title, body, 0);
				
				articles.add(article);
				
				System.out.println(lastArticleId + "번 글이 생성되었습니다");
				
			} else if (cmd.equals("article list")) {
				
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다");
					continue;
				}
				
				System.out.println("번호	|	제목	|		작성일		|	조회수");
				
				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);
					System.out.printf("%d	|	%s	|	%s	|	%d\n", article.id, article.title, article.regDate, article.viewCnt);
				}
				
			} else if (cmd.startsWith("article detail ")) {
				String[] cmdBits = cmd.split(" ");

				int id = 0;
				
				try {
					id = Integer.parseInt(cmdBits[2]);
				} catch (NumberFormatException e) {
					System.out.println("명령어가 올바르지 않습니다");
					continue;
				} catch (Exception e) {
					System.out.println("error : " + e);
				}
				
				Article foundArticle = null;
				
				for (Article article : articles){
					if (id == article.id) {
						foundArticle = article;
						break;
					}
				}
				
				if (foundArticle == null) {
					System.out.println(id + "번 게시물은 존재하지 않습니다");
					continue;
				}
				
				foundArticle.viewCnt++;
				
				System.out.printf("번호 : %d\n", foundArticle.id);
				System.out.printf("작성일 : %s\n", foundArticle.regDate);
				System.out.printf("제목 : %s\n", foundArticle.title);
				System.out.printf("내용 : %s\n", foundArticle.body);
				System.out.printf("조회수 : %d\n", foundArticle.viewCnt);
				
			} else if (cmd.startsWith("article delete ")) {
				String[] cmdBits = cmd.split(" ");

				int id = 0;
				
				try {
					id = Integer.parseInt(cmdBits[2]);
				} catch (NumberFormatException e) {
					System.out.println("명령어가 올바르지 않습니다");
					continue;
				} catch (Exception e) {
					System.out.println("error : " + e);
				}
				
				Article foundArticle = null;
				
				for (Article article : articles){
					if (id == article.id) {
						foundArticle = article;
						break;
					}
				}
				
				if (foundArticle == null) {
					System.out.println(id + "번 게시물은 존재하지 않습니다");
					continue;
				}
				
				articles.remove(foundArticle);
				
				System.out.println(id + "번 게시물을 삭제했습니다");
				
			} else {
				System.out.println("존재하지 않는 명령어 입니다");
			}
		}
		
		sc.close();
		
		System.out.println("== 프로그램 끝 ==");
	}

	private static void makeTestData() {
		System.out.println("테스트용 게시물 데이터 3개를 생성했습니다");
		for (int i = 1; i <= 3; i++) {
			articles.add(new Article(++lastArticleId, Util.getDateStr(), "제목" + i, "내용" + i, i * 10));
		}
	}
}

class Article {
	int id;
	String regDate;
	String title;
	String body;
	int viewCnt;
	
	public Article(int id, String regDate, String title, String body, int viewCnt) {
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.body = body;
		this.viewCnt = viewCnt;
	}
}