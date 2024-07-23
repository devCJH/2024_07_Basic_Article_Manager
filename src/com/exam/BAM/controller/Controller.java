package com.exam.BAM.controller;

import java.util.Scanner;

public abstract class Controller {
	
	public Scanner sc;
	public int lastId;
	public String cmd;
	
	public abstract void makeTestData();

	public abstract void doAction(String cmd, String methodName);
}
