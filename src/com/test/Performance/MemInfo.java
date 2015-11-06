package com.test.Performance;

import java.io.IOException;

import com.test.op.TestResult;

public class MemInfo {
	private String enName;
	private String packageName;
	
	public MemInfo(String enName,String packageName) {
		this.enName = enName;
		this.packageName = packageName;
	}
	public String getCurMem() {
		String re = null;
		String[] memLine = null;
		String[] result;
		Process proc;
		String cmd = "adb -s "+enName+" shell dumpsys meminfo "+packageName;
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
			if(result[i].contains("TOTAL")){
				memLine = result[i].split("\\s+");
				if(memLine!=null){
					return memLine[2];
				}
				break;
			}else if(result[i].contains("allocated")){
				memLine = result[i].split("\\s+");
				if(memLine!=null){
					return memLine[4];
				}
				break;
			}
		}
		
		return null;
	}
}
