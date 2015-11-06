package com.test.op;

import java.io.File;
import java.io.IOException;

import com.test.dao.CurData;

public class PhoneResultOPer {
	private int type;
	private String enName;
	private String filePath;
	public PhoneResultOPer(String enName,String filePath,int type) {
		this.type = type;
		this.enName = enName;
		this.filePath = filePath;
	}
	//	public static void main(String[] args) {
	//		new PhoneResultOPer("304D1981577C8C9E", "G:/test/history/GT-I9100\\com.m1905.mobilefree.test-2014-12-25-14-21-34-229", 0).pullFile("log");
	////		new PhoneResultOPer("?", "G:/test/history/HUAWEI C8812E\\com.film.news.mobile.test-2014-12-18-14-38-13-146", 0).pullFile("log");
	//	}
	public void pullFile(String tp) {
		String re = null;
		Process proc;
		String cmd = null;

		if(tp.contentEquals("log")){
			if(type==0){
				cmd = "adb -s "+enName+" pull "+CurData.RESULTTXT0+" "+filePath;
			}else if(type==1){
				cmd = "adb -s "+enName+" pull "+CurData.RESULTTXT1+" "+filePath;
			}
			try {
				System.out.println(cmd);
				proc = Runtime.getRuntime().exec(cmd);
				//re=new TestResult(proc,0).call();		
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}else{			
			String[] ls=lsAllName(tp);	
			if(ls==null){
				return;
			}
			for(int i=0;i<ls.length;i++){
				if(type==0){
					cmd = "adb -s "+enName+" pull "+CurData.RESULTPICPATH0+ls[i]+" "+filePath+File.separator+ls[i];
				}else if(type==1){
					cmd = "adb -s "+enName+" pull "+CurData.RESULTPICPATH1+ls[i]+" "+filePath+File.separator+ls[i];
				}	
				try {
					System.out.println(cmd);
					proc = Runtime.getRuntime().exec(cmd);
					//re=new TestResult(proc,0).call();		
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		}				

	}

	public void delOldfile(String tp) {
		String re = null;
		Process proc;
		String cmd = null;
		String[] ls=lsAllName(tp);	
		if(ls==null){
			return;
		}
		for(int i=0;i<ls.length;i++){
			if(tp.contentEquals("log")){
				if(type==0){
					cmd = "adb -s "+enName+" shell rm -r "+CurData.RESULTTXT0+ls[i];
				}else if(type==1){
					cmd = "adb -s "+enName+" shell rm -r "+CurData.RESULTTXT1+ls[i];
				}	
			}else{
				if(type==0){
					cmd = "adb -s "+enName+" shell rm -r "+CurData.RESULTPICPATH0+ls[i];
				}else if(type==1){
					cmd = "adb -s "+enName+" shell rm -r "+CurData.RESULTPICPATH1+ls[i];
				}	
			}

			try {
				proc = Runtime.getRuntime().exec(cmd);
				//re=new TestResult(proc,0).call();		
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}

	}
	private String[] lsAllName(String tp) {
		String re = null;
		String[] result=null;
		Process proc;
		String cmd = null;
		if(tp.contentEquals("log")){
			if(type==0){
				cmd = "adb -s "+enName+" shell ls "+CurData.RESULTTXT0;
			}else if(type==1){
				cmd = "adb -s "+enName+" shell ls "+CurData.RESULTTXT1;
			}	
		}else{
			if(type==0){
				cmd = "adb -s "+enName+" shell ls "+CurData.RESULTPICPATH0;
			}else if(type==1){
				cmd = "adb -s "+enName+" shell ls "+CurData.RESULTPICPATH1;
			}		
		}

		try {
			proc = Runtime.getRuntime().exec(cmd);
			re=new TestResult(proc,0).call();		
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(re!=null){
			result = re.split(";");
		}


		return result;
	}
}
