package com.test.Performance;

import java.io.IOException;

import com.test.op.TestResult;

public class FlowInfo {
	private String enName;
	private String uid;
	
	public FlowInfo(String enName,String uid) {
		this.enName = enName;
		this.uid = uid;
	}
	public String upFlowInfo() {
		String re = null;
		String[] result;
		Process proc;
		String cmd = "adb -s "+enName+" shell cat /proc/uid_stat/"+uid+"/tcp_snd";//4.0以上的版本
		try {
			proc = Runtime.getRuntime().exec(cmd);
			re=new TestResult(proc,0).call();		
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}				
		result = re.split(";");
		if(!result[0].contains("No such file or directory")){
			return result[0];
		}		
		return null;		
	}
	public String downFlowInfo() {
		String re = null;
		String[] result;
		Process proc;
		String cmd = "adb -s "+enName+" shell cat /proc/uid_stat/"+uid+"/tcp_rcv";
		try {
			proc = Runtime.getRuntime().exec(cmd);
			re=new TestResult(proc,0).call();		
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}				
		result = re.split(";");
		if(!result[0].contains("No such file or directory")){
			return result[0];
		}		
		return null;				
	}
}
