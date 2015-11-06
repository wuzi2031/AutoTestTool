package com.test.op;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnquipmentInfoOper {
	public EnquipmentInfoOper() {
	}

	public float[] getPixels(String enName) {
		String re = null;
		String maStr = null;		
		String[] result;
		float[] pix = new float[2];
		Process proc;
		String cmd;
		String enModel = getmodel(enName);
		if(enModel.contentEquals("HTC S720t")||enModel.contentEquals("HUAWEI C8812E")){			
			cmd = "adb -s "+enName+" shell dumpsys window";
		}else{
			cmd = "adb -s "+enName+" shell dumpsys window displays";
		}		
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
			if(result[i].contains("init")&&result[i].contains("cur")){				
				maStr = result[i];
				break;
			}else if(result[i].contains("DisplayWidth")&&result[i].contains("DisplayHeight")){
				maStr=result[i];
				break;
			}
		}		
		Pattern pat = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pat.matcher(maStr); 
		int i=0;
		while (matcher.find()) {
			String temp = maStr.substring(matcher.start(),matcher.end());
			pix[i]=Float.parseFloat(temp);
			i++;
			if(i>1){
				break;
			}
		}     
		return pix;
	}
	public String getmodel(String enName) {
		String re = null;
		String[] modLine = null;
		String[] result;
		Process proc;
		String cmd = "adb -s "+enName+" shell cat /system/build.prop";
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
			if(result[i].contains("ro.product.model")){
				modLine=result[i].split("=");
				break;
			}
		}	
		if(modLine!=null){
			return modLine[1].replaceAll(" ", "-").replaceAll(":", "-");
		}
		return enName.replaceAll(" ", "-").replaceAll(":", "-");
	}
}
