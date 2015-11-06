package com.test.op;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.security.auth.callback.Callback;

public class TestResult implements Callback{
	Process proc;
	String testLog = null;
	int type;
	String sp;
	public TestResult(Process proc,int type) {
		this.proc=proc;
		this.type=type;
	}	
	public String call() throws Exception {
		if(type==0){
			sp=";";
		}else if(type==1){
			sp="\r\n";
		}
		try {
			BufferedReader bufferedReader = new BufferedReader(  
					new InputStreamReader(proc.getInputStream(),"utf-8"));
			//FileWriter fw=new FileWriter(logSavePath+name,true);
			//BufferedWriter bufferedWriter = new BufferedWriter(fw);

			//StringBuilder log=new StringBuilder();  
			String line;  


			while ((line = bufferedReader.readLine()) != null) {
				
				if(testLog==null){
					testLog=line+sp;
				}else{
					if(line.length()>0){
						testLog=testLog+line.trim()+sp;
					}						
				}

				//log.append(line);  				
				//bufferedWriter.write(line+CommonData.ENTER);			
			}
			//			bufferedWriter.flush();
			//				bufferedWriter.close();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return URLEncoder.encode(testLog, "utf-8"); 
		return testLog; 
	}
}
