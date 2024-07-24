package com.exam.BAM.controller;

import java.util.Scanner;

import com.exam.BAM.dto.Member;

public abstract class Controller {
	
	public Scanner sc;
	public int lastId;
	public String cmd;
	public static Member loginedMember;
	
	public abstract void makeTestData();

	public abstract void doAction(String cmd, String methodName);
}
