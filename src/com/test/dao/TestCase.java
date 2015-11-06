package com.test.dao;

import java.util.ArrayList;

public class TestCase {
	String num=null;
	String name=null;
	String type=null;
	ArrayList<String> step=null;
	ArrayList<String> result=null;
	String isExecute=null;
	public String getIsExecute() {
		return isExecute;
	}
	public void setIsExecute(String isExecute) {
		this.isExecute = isExecute;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<String> getStep() {
		return step;
	}
	public void setStep(ArrayList<String> step) {
		this.step = step;
	}
	public ArrayList<String> getResult() {
		return result;
	}
	public void setResult(ArrayList<String> result) {
		this.result = result;
	}
	
	public TestCase() {		
	}

}
