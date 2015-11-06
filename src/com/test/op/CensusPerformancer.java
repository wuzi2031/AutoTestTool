package com.test.op;

import java.util.ArrayList;

import com.test.Performance.CpuInfo;
import com.test.Performance.AppInfo;
import com.test.Performance.FlowInfo;
import com.test.Performance.MemInfo;
import com.test.dao.CurData;

public class CensusPerformancer implements Runnable{
	private boolean isEnd = false;
	private String packageName = null;	
	private String fileName = null;	
	private String enName = null;
	public CensusPerformancer(String packageName,String enName,String fileName){
		this.packageName = packageName;
		this.enName = enName; 
		this.fileName = fileName;
	}
	public boolean isEnd() {
		return isEnd;
	}
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	public String getPackageName() {
		return packageName;
	}
	@Override
	public void run() {
		AppInfo enInfo = new AppInfo(packageName, enName);
		OperatePerformanceExcel oper = new OperatePerformanceExcel(enName, fileName+".xls");
		CpuInfo cInfo = new CpuInfo(enName);
		String uid=enInfo.getUid();		
		MemInfo mInfo = null;
		FlowInfo fInfo = null;
		ArrayList<String> con=new ArrayList<String>();
		int oldUpFlow=0;
		int oldDownFlow=0;
		int curUpFlow=0;
		int curDownFlow=0;
		int curUpFlowInfo=0;
		int curDownFlowInfo=0;
		oper.createExcel();
		while(!isEnd){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(uid==null){
				uid=enInfo.getUid();
				if(uid==null){
					continue;
				}
			}
			if(uid!=null&&mInfo==null&&fInfo==null){
				System.out.println("uid:"+uid);
				mInfo = new MemInfo(enName, packageName);
				fInfo = new FlowInfo(enName, enInfo.getUid());
			}
			con.add(CurData.getCurrTime());
			con.add(mInfo.getCurMem());
			con.add(cInfo.getCurRate());
			if(fInfo.upFlowInfo()!=null){
				curUpFlow = Integer.parseInt(fInfo.upFlowInfo());
			}
			if(fInfo.downFlowInfo()!=null){
				curDownFlow = Integer.parseInt(fInfo.downFlowInfo());
			}
					
			if(oldUpFlow!=0&&oldDownFlow!=0){
				curUpFlowInfo = curUpFlow-oldUpFlow;
				curDownFlowInfo = curDownFlow-oldDownFlow;
			}			
			oldUpFlow = curUpFlow;
			oldDownFlow = curDownFlow;			
			con.add(curUpFlowInfo+"");
			con.add(curDownFlowInfo+"");		
			oper.writeInfo(con);
			con.clear();
		}	

	}
}


