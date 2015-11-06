package com.test.op;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.test.dao.CaseResult;

public class XmlOper {

	public XmlOper() {
	}
	public void createXml(String filePath,ArrayList<CaseResult> caseResults) {
		XMLWriter writer = null;// 声明写XML的对象
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");// 设置XML文件的编码格式
		File file = new File(filePath);
		Document _document = DocumentHelper.createDocument();
		Element _root = _document.addElement("tests");
		for(int i=0;i<caseResults.size();i++){
			Element _test = _root.addElement("test");
			Element _id = _test.addElement("testCase");
			_id.setText(caseResults.get(i).getName());
			Element _time = _test.addElement("time");
			_time.setText(caseResults.get(i).getTime());
			Element _result = _test.addElement("result");
			_result.setText(caseResults.get(i).getResult());
			Element _detail = _test.addElement("detail");
			_detail.setText(caseResults.get(i).getDetail());
		}		
		try {
			writer = new XMLWriter(new FileWriter(file), format);
			writer.write(_document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public ArrayList<CaseResult> read(String filePath) {
		ArrayList<CaseResult> caseResults =new ArrayList<CaseResult>();
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new File(filePath));
			Element root = document.getRootElement();	
			CaseResult caseRe;
			for ( Iterator iter = root.elementIterator(); iter.hasNext(); ) {
				Element element = (Element) iter.next();
				caseRe = new CaseResult();
				//				// 获取person节点的age属性的值
				//				Attribute ageAttr=element.attribute("age");
				//				if(ageAttr!=null){
				//					String age = ageAttr.getValue();
				//					if (age != null&&!age.equals("")) {
				//						hm.put(element.getName() + "-"+ageAttr.getName()+ num, age);
				//					} else {
				//						hm.put(element.getName() + "-" +ageAttr.getName()+ num, "20");
				//					}
				//				}else{
				//					hm.put(element.getName() + "-age"+ num, "20");
				//				}
				for ( Iterator iterInner = element.elementIterator(); iterInner.hasNext(); ) {
					Element elementInner = (Element) iterInner.next();
					if(elementInner.getName().equals("testCase")){
						caseRe.setName(elementInner.getText());

						//						//获取college节点的leader属性的值
						//						Attribute leaderAttr=elementInner.attribute("leader");
						//						if(leaderAttr!=null){
						//							String leader = leaderAttr.getValue();
						//							if (leader != null&&!leader.equals("")) {
						//								hm.put(elementInner.getName() + "-"+leaderAttr.getName()+ num, leader);
						//							} else {
						//								hm.put(elementInner.getName() + "-" +leaderAttr.getName()+ num, "leader");
						//							}
						//						}else {
						//							hm.put(elementInner.getName() + "-leader"+ num, "leader");
						//						}
					}else if(elementInner.getName().equals("time")){
						caseRe.setTime(elementInner.getText());
					}else if(elementInner.getName().equals("result")){
						caseRe.setResult(elementInner.getText());
					}else if(elementInner.getName().equals("detail")){
						caseRe.setDetail(elementInner.getText());
					}
				}
				caseResults.add(caseRe);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return caseResults;
	}
}

