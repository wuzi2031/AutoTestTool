package com.test.dao;

import javax.swing.Icon;

public class IconListItem {
	private String packgeName;
	private Icon icon;
	private String enName;
	private String text;
	private String state;
	
	public IconListItem(Icon icon,String enName,String packgeName, String text)
	{
		this.icon = icon;
		this.text = text;
		this.packgeName = packgeName;
		this.enName = enName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPackgeName() {
		return packgeName;
	}
	public void setPackgeName(String packgeName) {
		this.packgeName = packgeName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	
	public Icon getIcon() {
		return icon;
	}
	public String getText() { 
		return text;
	}
	public void setIcon(Icon icon){
		this.icon = icon;
	}
	public void setText(String text){ 
		this.text = text; 
	}
}
