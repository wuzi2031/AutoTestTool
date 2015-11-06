package com.test.op;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

import com.test.dao.CaseResult;
import com.test.dao.CurData;
import com.test.dao.IconListItem;
import com.test.testTool.lunch.HomeLunch;

public class CaseExecuter extends Observable implements Runnable{
	private String testPackage;
	private IconListItem item;	
	private String enName;
	private String historyPath;
	private String filePath;
	private String fName;
	private boolean goOn;
	private ArrayList<CaseResult> resultArr;
	private String enModel;
	private PhoneResultOPer rp;
	
	public CaseExecuter(String enName,String testPackage) {
		this.enName=enName;
		this.testPackage=testPackage;
		this.item=CurData.LISTDATA.get(enName);	
		this.enModel = new EnquipmentInfoOper().getmodel(enName);
		this.historyPath=CurData.HISTORYPATH+enModel.replaceAll(":", "-");
		
	}
	public IconListItem getItem() {
		return item;
	}
	public void setItem(IconListItem item) {
		this.item = item;
	}
	public String getTestPackage() {
		return testPackage;
	}
	public void setTestPackage(String testPackage) {
		this.testPackage = testPackage;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public boolean isGoOn() {
		return goOn;
	}

	public void setGoOn(boolean goOn) {
		this.goOn = goOn;
	}
	@Override
	public void run() {
		FileOper.createDir(historyPath);
		fName = testPackage+"-"+FileOper.getCurrTime();
		filePath = historyPath+File.separator+fName;
		if(testPackage!=null&&!testPackage.isEmpty()){
			resultArr= new ArrayList<CaseResult>();
			startExecute();
		}
		if(rp!=null){
			System.out.println("---开始pull---");
			rp.pullFile("log");
			rp.pullFile("pic");									
			System.out.println("---pull结束---");
		}
//		XmlOper xOper = new XmlOper();
//		xOper.createXml(filePath+".xml", resultArr);
		HtmlCreater html = new HtmlCreater(enName);
		html.create(filePath, resultArr);
		CurData.LISTEXECUTERS.remove(this);
	}
	
	private void startExecute() {
		
		String pName=testPackage+".txt";
		//String testLog = null;
		String re=null;
		String cmd = null;
		if(isGoOn()){
			item.setIcon(CurData.ICONUSED);
			item.setText("准备中...");
			item.setEnName(enModel);
			item.setPackgeName(testPackage);
			setChanged();
			notifyObservers();
		}
		String[] caseNums=read(pName);
		CensusPerformancer cenPer = new CensusPerformancer(CurData.delLastStr(testPackage.split("\\.")),enName,fName);
		cenPer.setEnd(false);
		new Thread(cenPer).start();
		FileOper.createDir(filePath);
		rp = new PhoneResultOPer(enName, filePath, 0);
		rp.delOldfile("pic");
		rp.delOldfile("log");
		for(int testCount=0;testCount<caseNums.length;testCount++){
			if(isGoOn()){
				item.setIcon(CurData.ICONUSED);
				item.setText("正在执行："+caseNums[testCount]);
				item.setEnName(enModel);
				item.setPackgeName(testPackage);
				setChanged();
				notifyObservers();
			}else {
				break;
			}
			cmd="adb -s "+enName+" shell am instrument -e class "+testPackage+"."+caseNums[testCount]+" -w "+
					testPackage+"/android.test.InstrumentationTestRunner";
			try {
				Process proc = Runtime.getRuntime().exec(cmd);				
				try {
					re=new TestResult(proc,1).call();
					int reDo=1;
					while(!re.contains("OK")){
						System.out.println(enModel+(reDo+1)+"次执行："+caseNums[testCount]);
						proc = Runtime.getRuntime().exec(cmd);
						re=new TestResult(proc,1).call();
						reDo++;
						if(reDo>2){
							break;
						}
					}
					//System.out.println(re);
					//FileOper.recordLog(re, filePath+".txt");
					resultArr.add(new CaseResult(re,testPackage,caseNums[testCount]));					
					if(!isGoOn()){					
						item.setIcon(CurData.ICONFREE);
						item.setText(CurData.FREE);
						item.setEnName(enModel);
						item.setPackgeName("");	
						item.setState(null);
						CurData.LISTEXECUTERS.remove(this);							
						setChanged();
						notifyObservers();
						cenPer.setEnd(true);
						break;
					}else if(testCount==caseNums.length-1){
						item.setIcon(CurData.ICONFREE);
						item.setText(CurData.FREE);
						item.setEnName(enModel);
						item.setPackgeName("");	
						item.setState(null);	
						CurData.LISTEXECUTERS.remove(this);												
						cenPer.setEnd(true);
						setChanged();
						notifyObservers();
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		cenPer.setEnd(true);
	}
	private  String[] read(String fileName) {
		//createDir(fileName);
		String content=null;
		String line;
		try {						
			BufferedReader bufferedReader = new BufferedReader(new FileReader(CurData.PATH+fileName));
			while((line=bufferedReader.readLine())!=null ){

				if(content==null){
					content=line;
				}else{
					content=content+line;
				}
			}
			bufferedReader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(content!=null){
			return content.split(";");
		}
		return null;
	}
	
}
