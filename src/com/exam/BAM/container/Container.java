package com.exam.BAM.container;

import java.util.ArrayList;
import java.util.List;

import com.exam.BAM.dto.Article;
import com.exam.BAM.dto.Member;

public class Container {
	public static List<Article> articles;
	public static List<Member> members;
	
	static {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}
}
