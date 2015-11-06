package com.test.Performance;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.test.op.TestResult;

public class CpuInfo {
	private String enName = null;
	public CpuInfo(String enName) {
		this.enName = enName;
	}
	public String getCurRate() {
		String re = null;
		String[] rateLine = null;
		String[] result;
		int rate = 0;
		Process proc;
		String cmd = "adb -s "+enName+" shell top -n 1";
		try {
			proc = Runtime.getRuntime().exec(cmd);
			re=new TestResult(proc,0).call();		
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}				
		result = re.split(";");
		rateLine = result[1].split(",");
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher;
		for(int i=0;i<rateLine.length;i++){
			matcher = pattern.matcher(rateLine[i]);
			rate = rate+Integer.parseInt(matcher.replaceAll(""));
		}
		return rate+"";
	}
}
