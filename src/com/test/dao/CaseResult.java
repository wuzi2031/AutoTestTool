package com.test.dao;

public class CaseResult {
	private String name;
	private String pack;
	private String time;
	private String result;
	private String detail;
	public CaseResult() {
	}
	public CaseResult(String detail,String pack,String name) {
		this.name = name;
		this.pack = pack;
		this.detail = detail;
		initAttr();
	}
	private void initAttr() {
		if(detail!=null&&!detail.isEmpty()){
			String[] line;
			String[] s = detail.split("\r\n");
			for(int i=0;i<s.length;i++){
				if(s[i].contains("Time:")){
					line=s[i].split("\\s+");
					this.time = line[1];
				}else if(s[i].contains("FAILURES!!!")){
					this.result = "failure";
				}else if(s[i].contains("OK")){
					this.result = "success";
				}else if(s[i].contains("Process crashed")){
					this.result = "crashed";
				}
			}
		}
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	


}
