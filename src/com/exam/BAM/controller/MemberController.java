package com.exam.BAM.controller;

import java.util.List;
import java.util.Scanner;

import com.exam.BAM.container.Container;
import com.exam.BAM.dto.Member;
import com.exam.BAM.util.Util;

public class MemberController extends Controller {

	private List<Member> members;
	
	public MemberController(Scanner sc) {
		this.sc = sc;
		this.lastId = 0;
		this.members = Container.members;
	}
	
	@Override
	public void doAction(String cmd, String methodName) {
		switch (methodName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다");
		}
	}

	private void doJoin() {
		String loginId = null;
		String loginPw = null;
		String name = null;
	
		while(true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();
			
			if (loginId.length() == 0) {
				System.out.println("아이디는 필수 입력 정보입니다");
				continue;
			}
			
			Member member = getMemberByLoginId(loginId);
			
			if (member != null) {
				System.out.printf("[ %s ]은(는) 이미 사용중인 아이디입니다\n", loginId);
				continue;
			}
			
			System.out.printf("[ %s ]은(는) 사용가능한 아이디입니다\n", loginId);
			break;
		}
		
		while (true) {
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine();
			
			if (loginPw.length() == 0) {
				System.out.println("비밀번호는 필수 입력 정보입니다");
				continue;
			}
			
			System.out.printf("비밀번호 확인 : ");
			String loginPwChk = sc.nextLine();
			
			if (loginPw.equals(loginPwChk) == false) {
				System.out.println("비밀번호가 일치하지 않습니다");
				continue;
			}
			break;
		}
		
		while (true) {
			System.out.printf("이름 : ");
			name = sc.nextLine().trim();
			
			if (name.length() == 0) {
				System.out.println("이름은 필수 입력 정보입니다");
				continue;
			}
			break;
		}
		
		lastId++;
		
		Member member = new Member(lastId, Util.getDateStr(), loginId, loginPw, name);
		
		members.add(member);
		
		System.out.println(name + "님이 가입되었습니다");
	}
	
	private void doLogin() {
		if (loginedMember != null) {
			System.out.println("이미 로그인이 되어있습니다");
			return;
		}
		
		System.out.printf("아이디 : ");
		String loginId = sc.nextLine().trim();
		System.out.printf("비밀번호 : ");
		String loginPw = sc.nextLine().trim();
		
		if (loginId.length() == 0) {
			System.out.println("아이디를 입력해주세요");
			return;
		}
		
		if (loginPw.length() == 0) {
			System.out.println("비밀번호를 입력해주세요");
			return;
		}
		
		Member member = getMemberByLoginId(loginId);
		
		if (member == null) {
			System.out.printf("[ %s ]은(는) 존재하지 않는 아이디입니다\n", loginId);
			return;
		}
		
		if (member.getLoginPw().equals(loginPw) == false) {
			System.out.println("비밀번호를 확인해주세요");
			return;
		}
		
		loginedMember = member;
		
		System.out.printf("[ %s ] 회원님 환영합니다~\n", member.getName());
	}
	
	private void doLogout() {
		if (loginedMember == null) {
			System.out.println("로그인이 되어 있지 않습니다");
			return;
		}
		
		loginedMember = null;
		System.out.println("정상적으로 로그아웃 되었습니다");
	}

	private Member getMemberByLoginId(String loginId) {
		for (Member member : members) {
			if (member.getLoginId().equals(loginId)) {
				return member;
			}
		}
		return null;
	}
	
	@Override
	public void makeTestData() {
		System.out.println("테스트용 회원 데이터 3개를 생성했습니다");
		for (int i = 1; i <= 3; i++) {
			members.add(new Member(++lastId, Util.getDateStr(), "test" + i, "test" + i, "유저" + i));
		}
	}
	
}