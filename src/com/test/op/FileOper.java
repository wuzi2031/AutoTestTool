package com.test.op;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.test.dao.CurData;

public class FileOper {

	public FileOper() {
	}
	public  static void createDir(String path) {
		File directory = new File(path);
		if(!directory.exists()){
			directory.mkdirs();
		}
	}
	public  static void recordLog(String str,String path) {
		try {	
			FileWriter fw=new FileWriter(path,true);
			BufferedWriter bufferedWriter = new BufferedWriter(fw);	
			if(str!=null){
				bufferedWriter.write(str);
				bufferedWriter.flush();
				bufferedWriter.close();
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static String getCurrTime() {
		Date date = new Date(); 
		SimpleDateFormat currTime=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SS");//其中yyyy-MM-dd是你要表示的格式 
		//可以任意组合，不限个数和次序；具体表示为：MM-month,dd-day,yyyy-year;kk-hour,mm-minute,ss-second; 
		String currTimeStr=currTime.format(date);
		return currTimeStr; 
	}
	public static String[] getAllFile(String fileName) {
		File file=new File(CurData.HISTORYPATH+fileName);
		String[] test=file.list();
		return test;		  
	}
	public static boolean fileExist(String fileName) {
		File file=new File(fileName);		
		return file.exists();		  
	}
	/**
	 * @describe Read file with specified encode
	 * @param filename
	 * @param encoding
	 * @return the content of the file in the form of string
	 */
	public static String read(String fileName, String encoding) {

		String string ="";
		File f = new File(fileName);
		if(f.exists()){
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						new FileInputStream(fileName), encoding));

				String str = "";
				while ((str = in.readLine()) != null) {
					string += str + "\n";
				}
				string=string.replaceAll("<", " ");
				string=string.replaceAll(">", " ");
				in.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return string;
	}
}
