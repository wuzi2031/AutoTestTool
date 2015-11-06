package com.test.op;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.test.dao.CaseResult;
import com.test.dao.CurData;

public class HtmlCreater {
	private static String color1 = "LightBlue";
	private static String color2 = "LightCyan";
	private static int picWidth = 260;
	private String enName;
	public HtmlCreater(String enName) {
		this.enName = enName;
	}
	
	public void create(String filePath,ArrayList<CaseResult> caseResults) {
		StringBuilder sb = new StringBuilder();
		PrintStream printStream = null;
		EnquipmentInfoOper enOper = new EnquipmentInfoOper();
		float[] pix=enOper.getPixels(enName);
		float picHeight = (picWidth/pix[0])*pix[1];
		try {
			printStream = new PrintStream(new FileOutputStream(filePath+".html"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
		sb.append("<html>");  
		sb.append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/> <style type=\"text/css\">img {margin:5px;}</style></head>");  
		sb.append("<body>");  
		sb.append("<h2 align=\"center\">"+enOper.getmodel(enName)+"测试结果</h2>");
		sb.append("<h3 align=\"center\">分辨率："+pix[0]+"×"+pix[1]+"</h3>");
		sb.append("<table frame=\"box\" align=\"center\">");
		sb.append("<tr style=\"background:"+color1+";\">");
		sb.append("<th colspan=\"3\">All Test Case Results</th>");
		sb.append("</tr>");
		sb.append("<tr style=\"background:"+color2+";\">");
		sb.append("<th>Test Case</th>");
		sb.append("<th>Result</th>");
		sb.append("<th>Total Time</th>");
		sb.append("</tr>");
		int sucNum=0;
		float totalTime=0;
		String res;
		for(int i=0;i<=caseResults.size();i++){

			if(i%2==0){
				sb.append("<tr style=\"background:"+color1+";\">");
			}else{
				sb.append("<tr style=\"background:"+color2+";\">");
			}     
			if(i!=caseResults.size()){
				sb.append("<td width=\"500\"><a href=\"#C"+i+"\">"+caseResults.get(i).getName()+"</a></td>");
				res = caseResults.get(i).getResult();
				if(res!=null&&res.contentEquals("success")){
					sucNum++;
					sb.append("<td width=\"400\" align=\"center\">"+caseResults.get(i).getResult()+"</td>");
				}else{
					sb.append("<td width=\"400\" align=\"center\" style=\"background:red;\">"+caseResults.get(i).getResult()+"</td>");

				}           
				sb.append("<td width=\"200\" align=\"center\">"+caseResults.get(i).getTime()+"</td>");
				if(caseResults.get(i).getTime()!=null){
					totalTime=totalTime+Float.parseFloat(caseResults.get(i).getTime());
				}				
			}else{
				sb.append("<td>Total</td>");
				sb.append("<td align=\"center\">Success:"+sucNum+" Failure:"+(caseResults.size()-sucNum)+"  Success Rate:"+(sucNum*100)/caseResults.size()+"%</td>");
				sb.append("<td align=\"center\">"+totalTime+"</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table><br><hr>");
		sb.append("<div style=\"padding-left:30px\">");
		File[] f;
		String casePic;
		for(int j=0;j<caseResults.size();j++){
			sb.append("<h4 ><a name=\"C"+j+"\">"+caseResults.get(j).getName()+"</a></h4>");
			subTxt(filePath+File.separator+caseResults.get(j).getName());
			sb.append("<pre>"+caseResults.get(j).getDetail()+"<a href=\""+filePath+File.separator+caseResults.get(j).getName()+".html"+"\"  target=\"_blank\">查看详细</a></pre>");
			casePic = filePath+File.separator+caseResults.get(j).getName();
			System.out.println(caseResults.get(j).getName());
			System.out.println(casePic);
			f=listPic(casePic);
			if(f!=null){
				for(int k=0;k<f.length;k++){
					sb.append("<img src=\""+casePic+File.separator+f[k].getName()+"\" alt=\"Big Boat\" onload=\"javascript:if(this.height<this.width){this.height="+picWidth+",this.width="+picHeight+"}else{this.width="+picWidth+", this.height="+picHeight+"}\">");
				}
			}		
			if(j!=caseResults.size()-1){
				sb.append("<br><hr style=\"border:1px dashed #000; height:1px\">");
			}		
		}
		sb.append("</div></body></html>");
		if(printStream!=null){
			printStream.print(sb.toString());
			printStream.flush();
			printStream.close();
		}					
	}
	private File[] listPic(String filePath) {
		File f = new File(filePath);
		if(!f.isDirectory()){
			System.out.println("你输入的不是一个文件夹，请检查路径是否有误！！");
		}
		else{
			return f.listFiles();
		}
		return null;

	}
	private void subTxt(String path) {
		StringBuilder sb = new StringBuilder();
		PrintStream printStream = null;
		try {
			printStream = new PrintStream(new FileOutputStream(path+".html"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
		sb.append("<html>");  
		sb.append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/> </head>");  
		sb.append("<body>");
		sb.append("<pre>"+FileOper.read(path+".txt", "utf-8")+"</pre>");
		sb.append("</body></html>");
		if(printStream!=null){
			printStream.print(sb.toString());
			printStream.flush();
			printStream.close();
		}	
	}
}
