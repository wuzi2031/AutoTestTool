package com.test.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.test.op.CaseExecuter;

public class CurData {
	public final static String HISTORYPATH="G:/test/history/";
	public final static String PATH="G:/test/";
	public final static Hashtable<String, IconListItem> LISTDATA =new Hashtable<>();
	public final static ArrayList<CaseExecuter> LISTEXECUTERS =new ArrayList<CaseExecuter>();
	public final static Icon ICONFREE= new ImageIcon(("./picture/normal.png"));
	public final static Icon ICONUSED= new ImageIcon("./picture/used.png");
	public final static String ICONLUNCH= "./picture/icon.jpg";
	public final static String FREE="空闲中...";
	public final static String RESULTPICPATH0="sdcard/Robotium-Screenshots/";
	public final static String RESULTTXT0="sdcard/robotium_log/";
	public final static String RESULTPICPATH1="sdcard-ext/Robotium-Screenshots/";
	public final static String RESULTTXT1="sdcard-ext/robotium_log/";
	private CurData() {
	}
	public static String getCurrTime() {
		Date date = new Date(); 
		SimpleDateFormat currTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");//其中yyyy-MM-dd是你要表示的格式 
		//可以任意组合，不限个数和次序；具体表示为：MM-month,dd-day,yyyy-year;kk-hour,mm-minute,ss-second; 
		String currTimeStr=currTime.format(date);
		return currTimeStr; 
	}
	public static String delLastStr(String[] str){
		String re = null;
		for(int i=0;i<str.length-1;i++){
			if(re==null){
				re=str[i]+".";
			}else{
				if(i!=str.length-2){
					re=re+str[i]+".";
				}else{
					re=re+str[i];
				}
			}		
		}
		return re;		
	}
}
