package com.test.dao;


public class HistoryListItem {
	private String packgeName;

	private String time;
	
	public HistoryListItem(String packgeName, String time)
	{
		this.packgeName = packgeName;
		this.time = time;
	}
	public String getPackgeName() {
		return packgeName;
	}

	public void setPackgeName(String packgeName) {
		this.packgeName = packgeName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	
}
