package com.exam.BAM.container;

import com.exam.BAM.dao.ArticleDao;
import com.exam.BAM.dao.MemberDao;
import com.exam.BAM.service.ArticleService;
import com.exam.BAM.service.MemberService;

public class Container {
	public static ArticleService articleService;
	public static ArticleDao articleDao;
	public static MemberService memberService;
	public static MemberDao memberDao;
	
	static {
		memberDao = new MemberDao();
		articleDao = new ArticleDao();
		memberService = new MemberService();
		articleService = new ArticleService();
	}
}
