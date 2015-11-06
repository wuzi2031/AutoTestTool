package com.test.testTool.lunch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.test.dao.CurData;
import com.test.exui.TextAreaMenu;

public class HistoryDetailLunch {

	private JFrame frame;
	private String enName;
	private String hisName;
	private TextAreaMenu textArea;
	private String text;

	/**
	 * Create the application.
	 */
	public HistoryDetailLunch(String enName,String hisName) {
		this.enName=enName;
		this.hisName=hisName;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 200, 900, 600);
		frame.setResizable(false);
		frame.setTitle("详细结果");
		Image icon = Toolkit.getDefaultToolkit().getImage(CurData.ICONLUNCH);
		frame.setIconImage(icon);	
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);	
		textArea = new TextAreaMenu();
		textArea.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(textArea);
		text=readHis();
		textArea.setText(text);
		JLabel lblNewLabel = new JLabel(enName+"    "+hisName);
		lblNewLabel.setBackground(Color.CYAN);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblNewLabel);
		frame.setVisible(true);
	}
	private  String readHis() {
		//createDir(fileName);
		String content=null;
		String line;
		try {						
			BufferedReader bufferedReader = new BufferedReader(new FileReader(CurData.HISTORYPATH+enName.replaceAll(":", "-")+File.separator+hisName));
			while((line=bufferedReader.readLine())!=null ){

				if(content==null){
					content=line+"\r\n";
				}else{
					content=content+line+"\r\n";
				}
			}
			bufferedReader.close();
			return content;
		}catch(Exception e){
			e.printStackTrace();
		}		
		return null;
	}
}
