package com.exam.BAM.dao;

import java.util.List;

import com.exam.BAM.container.Container;
import com.exam.BAM.dto.Member;
import com.exam.BAM.util.Util;

public class MemberDao {
	private List<Member> members;
	private int lastId;
	
	public MemberDao() {
		this.members = Container.members;
		this.lastId = 0;
	}

	public Member getMemberByLoginId(String loginId) {
		for (Member member : members) {
			if (member.getLoginId().equals(loginId)) {
				return member;
			}
		}
		return null;
	}

	public void joinMember(String loginId, String loginPw, String name) {
		lastId++;
		members.add(new Member(lastId, Util.getDateStr(), loginId, loginPw, name));
	}

	public String getWriterName(int memberId) {
		for (Member member : members) {
			if (memberId == member.getId()) {
				return member.getLoginId();
			}
		}
		return null;
	}
}
