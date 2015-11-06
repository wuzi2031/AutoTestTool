package com.test.op;
import java.io.File;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.test.dao.TestCase;

public class OperateTestExcel {
	String filePath=null;
	ArrayList<TestCase> cases=null;
	public OperateTestExcel(String filePath) {
		this.filePath=filePath;
	}
	public ArrayList<TestCase> resolve() {
		cases=new ArrayList<TestCase>();
		int startRow=0;
		int caseNum=0;
		int caseName=0;
		int casetype=0;
		int casestep=0;
		int caseResult=0;
		int caseExecute=0;
		TestCase testCase = null;
		boolean isTrue = false;
		try {
			Workbook book = Workbook.getWorkbook(new File(filePath));			
			//Sheet的下标是从0开始   

			//获取第一张Sheet表   

			Sheet readsheet = book.getSheet(0);   

			//获取Sheet表中所包含的总列数   

			int rsColumns = readsheet.getColumns();   

			//获取Sheet表中所包含的总行数   

			int rsRows = readsheet.getRows();   

			//获取指定单元格的对象引用   

			for (int i = 0; i < rsRows; i++)   

			{   
				if(i>=(startRow+1)){
					testCase=new TestCase();
				}

				for (int j = 0; j < rsColumns; j++)   

				{   

					Cell cell = readsheet.getCell(j, i);
					String cellCon=cell.getContents();
					if(cellCon.contentEquals("用例编号")){
						isTrue=true;
						startRow=i;
						caseNum=j;						
					}

					if((i==startRow)&&isTrue){
						switch (cellCon) {					
						case "用例名称":
							caseName=j;
							break;
						case "测试分类":
							casetype=j;
							break;
						case "测试步骤":
							casestep=j;
							break;
						case " 测试结果":
							caseResult=j;
							break;
						case "是否自动化":
							caseExecute=j;
							break;
						default:
							break;
						}
					}
					if(isTrue){
						if(j==caseNum){
							testCase.setNum(cellCon);
						}else if(j==caseName){
							testCase.setName(cellCon);
						}else if(j==casetype){
							testCase.setType(cellCon);
						}else if(j==casestep){
							//testCase.setStep(cellCon);
						}else if(j==caseResult){
							//testCase.setResult(cellCon);
						}else if(j==caseExecute){
							testCase.setIsExecute(cellCon);
						}
					}

					System.out.print(cell.getContents() + " ");   

				}  
				if((i>=(startRow+1))&&(testCase.getNum()!=null)){
					cases.add(testCase);
				}
			}  
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return cases;
	}
}
