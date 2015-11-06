package com.test.testTool.lunch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.test.dao.CurData;
import com.test.dao.HistoryListItem;
import com.test.op.EnquipmentInfoOper;
import com.test.op.FileOper;
import com.test.renderer.HistoryListItemRenderer;

public class HistoryLunch {

	private JFrame frame;
	private ArrayList<HistoryListItem> hisArr;
	private DefaultListModel listModel;
	private String enName;
	private long clickTime;
	private String mod;
	/**
	 * Create the application.
	 */
	public HistoryLunch(String enName) {
		this.enName = enName;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {	
		EnquipmentInfoOper enOper = new EnquipmentInfoOper();
		mod = enOper.getmodel(enName);
		initHisArr();
		frame = new JFrame();
		frame.setBounds(800, 200, 400, 600);
		frame.setResizable(false);
		frame.setTitle("执行记录列表");
		Image icon = Toolkit.getDefaultToolkit().getImage(CurData.ICONLUNCH);
		frame.setIconImage(icon);	
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		final JList list = new JList();
		list.setBackground(SystemColor.controlHighlight);
		scrollPane.setViewportView(list);
		list.setCellRenderer(new HistoryListItemRenderer());
		listModel = new DefaultListModel();
		initListModel();
		list.setModel(listModel);
		list.addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent me)
			{
				if(checkClickTime()){
					HistoryListItem item=(HistoryListItem) list.getSelectedValue();
					//new HistoryDetailLunch(enName,item.getPackgeName()+"-"+item.getTime());
					try {
						System.out.println(CurData.HISTORYPATH+mod+File.separator+item.getPackgeName());
						System.out.println(mod);
						Runtime.getRuntime().exec("cmd.exe /c start "+CurData.HISTORYPATH+mod+File.separator+item.getPackgeName()+"-"+item.getTime());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}				
			}
		});
		
		JLabel lblNewLabel = new JLabel(mod+"-"+enName);
		lblNewLabel.setBackground(Color.GRAY);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblNewLabel);
		frame.setVisible(true);
	}

	private boolean checkClickTime()
	{
		long nowTime = (new Date()).getTime();
		if((nowTime-clickTime)<300)
		{
			clickTime = nowTime;
			return true;
		}
		clickTime = nowTime;
		return false;
	}
	private void initHisArr() {
		hisArr=new ArrayList<>();
		String[] his; 
		String packgeName;
		String time;
		String [] histories=FileOper.getAllFile(mod.replaceAll(":", "-"));
		for(int i=0;i<histories.length;i++){
			if(histories[i].contains("html")){
				his=histories[i].split("-");
				packgeName=his[0];
				time=his[1]+"-"+his[2]+"-"+his[3]+"-"+his[4]+"-"+his[5]+"-"+his[6]+"-"+his[7];
				hisArr.add(new HistoryListItem(packgeName, time));
			}			
		}		
	}
	private void initListModel() {
		listModel.clear();
		for(int i = 0;i<hisArr.size();i++){
			listModel.addElement(hisArr.get(i));
		}

	}
	private  void readHistory(String fileName) {
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
	}
}
