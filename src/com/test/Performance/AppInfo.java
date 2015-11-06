package com.test.Performance;

import java.io.IOException;

import com.test.op.TestResult;

public class AppInfo {
	private String packageName = null;
	private String enName = null;
	
	public AppInfo(String packageName,String enName) {
		this.packageName = packageName;
		this.enName = enName;		
	}
	public String getPid() {
		String re = null;
		String[] pidLine = null;
		String[] result;
		Process proc;
		String cmd = "adb -s "+enName+" shell ps";
		try {
			proc = Runtime.getRuntime().exec(cmd);
			re=new TestResult(proc,0).call();		
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}				
		result = re.split(";");
		for(int i=0;i<result.length;i++){
			if(result[i].contains(packageName)){
				String s= result[i];
				pidLine = result[i].split("\\s+");
				break;
			}
		}
		if(pidLine!=null){
			return pidLine[1];
		}
		return null;
	}
	public String getUid() {
		String re = null;
		String[] uidLine = null;
		String[] result;
		Process proc;
		if(getPid()==null){
			return null;
		}
		String cmd = "adb -s "+enName+" shell cat /proc/"+getPid()+"/status";
		System.out.println(cmd);
		try {
			proc = Runtime.getRuntime().exec(cmd);
			re=new TestResult(proc,0).call();		
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}				
		result = re.split(";");
		for(int i=0;i<result.length;i++){
			if(result[i].contains("Uid:")){
				uidLine = result[i].split("\\s+");
				System.out.println("uidLine:"+result[i]);
				break;
			}
		}
		return uidLine[1];
	}
}
