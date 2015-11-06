package com.test.op;

import java.io.File;
import java.util.ArrayList;

import com.test.dao.CurData;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Number;

public class OperatePerformanceExcel {
	private String fileName = null;
	private String enName = null;
	private static String[] columName = {"时间","应用内存","总使用cpu","应用上行流量","应用下行流量"};
	private String filePath;
	public OperatePerformanceExcel(String enName,String fileName) {
		this.enName = enName;
		this.fileName = fileName;
		this.filePath = CurData.HISTORYPATH+(new EnquipmentInfoOper().getmodel(enName))+File.separator+fileName;
	}
	public void createExcel() {
		try   {
			//  打开文件 
			WritableWorkbook book  =  Workbook.createWorkbook(new File(filePath));
			//  生成名为“第一页”的工作表，参数0表示这是第一页 
			WritableSheet sheet  =  book.createSheet( enName,  0 );
			Label label;
			for(int i=0;i<columName.length;i++){
				label  =   new  Label( i, 5,  columName[i] );
				sheet.addCell(label);
			}			
			book.write();
			book.close();
			System.out.println("createExcel:"+filePath);
		}   catch  (Exception e)  {
			System.out.println(e);
		} 
	}
	public void writeInfo(ArrayList<String> con) {
		try   {
			//  Excel获得文件 
			Workbook wb  =  Workbook.getWorkbook(new File(filePath));
			Sheet sh= wb.getSheet(0);
			int row=sh.getRows();
			//  打开一个文件的副本，并且指定数据写回到原文件 
			WritableWorkbook book  =  Workbook.createWorkbook(new File(filePath),wb);
			//  添加一个工作表 
			WritableSheet sheet  =  book.getSheet(0);
			for(int i=0;i<columName.length;i++){
				sheet.addCell(new Label(i, row, con.get(i)));
			}			
			book.write();
			book.close();
		}   catch  (Exception e)  {
			System.out.println(e);
		} 
	}
	public void readInfo() {

	}
}
