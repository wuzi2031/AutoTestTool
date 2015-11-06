package com.test.testTool.lunch;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.test.dao.CurData;
import com.test.dao.IconListItem;
import com.test.dao.TestCase;
import com.test.op.CaseExecuter;
import com.test.op.EnquipmentInfoOper;
import com.test.op.EnquipmentObser;
import com.test.op.FileOper;
import com.test.op.OperateTestExcel;
import com.test.op.TestResult;
import com.test.renderer.IconListItemRenderer;

import java.awt.Color;
import java.awt.SystemColor;

public class HomeLunch extends Observable implements Observer{

	private JFrame frame;
	private JTextField textField,textSField1,textSField2;
	private JComboBox<String> comboBox;
	private JButton button;
	private JButton button_1,button_s1,button_s2;
	private JPanel panel_1;
	private JLabel label_1,label_2;
	private JButton button_2;
	private JScrollPane scrollPane;
	EnquipmentObser enObser;
	actEvent ae= new actEvent();
	String defaulComboBox = "------All------";
	Vector<String> comboBoxv;	
	boolean goOn=true;
	boolean isUsed=false;
	String filePath=null;
	String packageName=null;
	String selectPath=null;
	String comboBoxvItem=null;
	ArrayList<TestCase> cases=null;
	CaseExecuter caseExecuter;
	ComboBoxModel<String> cbm;
	private JList list;
	DefaultListModel listModel;
	HistoryLunch historyPage;
	ArrayList<String> curEnquipmentes=new ArrayList<String>();
	private JButton button_3;
	private JButton button_4;
	private String testPath;
	private String[] testPathArr;
	private EnquipmentInfoOper enOper;
	private JButton button_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try{
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e){}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeLunch window = new HomeLunch();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HomeLunch() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		enOper = new EnquipmentInfoOper();
		comboBoxv = new Vector();
		comboBoxv.add(defaulComboBox);
		cbm = new DefaultComboBoxModel(comboBoxv);
		frame = new JFrame();
		frame.setBounds(200, 200, 800, 600);
		frame.setResizable(false);
		Image icon = Toolkit.getDefaultToolkit().getImage(CurData.ICONLUNCH);
		frame.setIconImage(icon);		
		frame.setTitle("自动化工具");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		frame.getContentPane().add(panel, BorderLayout.NORTH);

		textField = new JTextField("安装包或测试文件");
		panel.add(textField);
		textField.setColumns(20);

		comboBox = new JComboBox(cbm);
		comboBox.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				//查询在线设备放入comboBoxv
				reViewComboBox();
				//comboBox.setModel(cbm);															
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {


			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent arg0) {

			}
		});
		comboBox.addActionListener(ae);

		button_4 = new JButton("选择");
		button_4.addActionListener(ae);
		button_4.setActionCommand("selectPack");
		panel.add(button_4);

		comboBox.setEditable(false);
		comboBox.setActionCommand("fixequipment");		
		panel.add(comboBox);

		button_3 = new JButton("安装");
		button_3.addActionListener(ae);
		button_3.setActionCommand("install");
		panel.add(button_3);

		button_5 = new JButton("卸载");
		button_5.addActionListener(ae);
		button_5.setActionCommand("uninstall");
		panel.add(button_5);

		button = new JButton("执行");
		button.addActionListener(ae);			
		button.setActionCommand("start");
		panel.add(button);

		button_1 = new JButton("停止");
		button_1.addActionListener(ae);
		button_1.setActionCommand("stop");
		panel.add(button_1);

		button_2 = new JButton("查看结果");
		button_2.addActionListener(ae);
		button_2.setActionCommand("history");
		panel.add(button_2);

		panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(2, 3, 20, 2));

		label_1 = new JLabel("用例文档");		
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label_1);

		textSField1 = new JTextField();
		textSField1.setColumns(20);
		panel_1.add(textSField1);

		button_s1 = new JButton("选择");
		button_s1.addActionListener(ae);
		button_s1.setActionCommand("select");
		panel_1.add(button_s1);

		label_2 = new JLabel("测试包名");		
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label_2);

		textSField2 = new JTextField();
		textSField2.setColumns(20);
		panel_1.add(textSField2);

		button_s2 = new JButton("生成用例");
		button_s2.addActionListener(ae);
		button_s2.setActionCommand("build");
		panel_1.add(button_s2);

		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);		

		list = new JList();
		list.setBackground(Color.LIGHT_GRAY);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		scrollPane.setViewportView(list);
		list.setCellRenderer(new IconListItemRenderer());
		listModel = new DefaultListModel();
		list.setModel(listModel);
		list.setFixedCellWidth(frame.getWidth()-10);
		opCurData();
		this.addObserver(this);
		enObser=new EnquipmentObser();
		enObser.addObserver(this);
		enObser.checkEnquipment();			
	}
	private void opCurData() {
		String cmd = null;
		String re = null;
		cmd="adb devices";
		String[] enquipmentes=null;
		String[] enquipment=null;	
		curEnquipmentes.clear();
		String key;
		try {
			Process proc = Runtime.getRuntime().exec(cmd);				
			try {
				re=new TestResult(proc,0).call();		
				enquipmentes = re.split(";");
				IconListItem item;
				for(int i=1;i<enquipmentes.length;i++){
					enquipment = enquipmentes[i].split("\t");
					key=enquipment[0];
					curEnquipmentes.add(key);
					if(!CurData.LISTDATA.containsKey(key)){//新增

						item=new IconListItem(CurData.ICONFREE, enOper.getmodel(key), "", CurData.FREE);
						CurData.LISTDATA.put(key, item);
					}					
				}
				Set set = CurData.LISTDATA.entrySet();
				Iterator it = set.iterator();
				while(it.hasNext()){
					Map.Entry me = (Map.Entry) it.next();
					String itemKey=(String) me.getKey();
					if(!curEnquipmentes.contains(itemKey)){
						CurData.LISTDATA.remove(itemKey);//下线
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		viewList();
	}
	private void viewList() {
		listModel.clear();
		Set set = CurData.LISTDATA.entrySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			
			Map.Entry me = (Map.Entry) it.next();
			IconListItem item=(IconListItem) me.getValue();
			listModel.addElement(item);
		}
	}
	class actEvent  implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			switch (e.getActionCommand()) {
			case "select":
				select(2);
				break;
			case "selectPack":
				select(1);
				break;
			case "start":
				if(comboBox.getSelectedIndex()!=0){
					testPath = textField.getText().trim();
					if(testPath!=null&&!testPath.isEmpty()){
						if(FileOper.fileExist(testPath)){						
							for(int i=0;i<CurData.LISTEXECUTERS.size();i++){
								if(CurData.LISTEXECUTERS.get(i).getEnName().contentEquals(comboBox.getSelectedItem().toString())){
									JOptionPane.showMessageDialog(null, "设备正在使用中", null, JOptionPane.ERROR_MESSAGE);
									isUsed = true;
									break;
								}					
							}
							if(!isUsed){
								testPathArr = testPath.split("\\\\");									
								caseExecuter=new CaseExecuter(comboBox.getSelectedItem().toString(),CurData.delLastStr(testPathArr[testPathArr.length-1].split("\\.")));
								caseExecuter.setGoOn(true);
								caseExecuter.addObserver(HomeLunch.this);
								new Thread(caseExecuter).start();
								CurData.LISTEXECUTERS.add(caseExecuter);
							}
							isUsed=false;
						}else{
							JOptionPane.showMessageDialog(null, "文件不存在，请生成用例执行文件", null, JOptionPane.ERROR_MESSAGE);
						}

					}else{
						JOptionPane.showMessageDialog(null, "请填入包名", null, JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
			case "build":
				if(!textSField1.getText().isEmpty()&&!textSField2.getText().isEmpty()){
					build();
				}
				break;
			case "stop":
				if(comboBox.getSelectedIndex()!=0){
					for(int i=0;i<CurData.LISTEXECUTERS.size();i++){
						CaseExecuter exc = CurData.LISTEXECUTERS.get(i);
						//String t=textField.getText().trim();
						String t1=comboBox.getSelectedItem().toString();
						boolean b=exc.getEnName().contentEquals(t1);
						//boolean b1=exc.getTestPackage().contentEquals(t);
						if(b){						
							exc.getItem().setState("即将停止");						
							exc.setGoOn(false);							
							//CurData.LISTEXECUTERS.remove(i);	
							setChanged();
							notifyObservers();
						}
					}
				}
				break;
				//			case "fixequipment":
				//				if(comboBox)
				//				comboBoxvItem = comboBox.getSelectedItem().toString().trim();
				//				break;
			case "history":
				if(comboBox.getSelectedIndex()!=0){
					historyPage = new HistoryLunch(comboBox.getSelectedItem().toString());
					qureyResult();
				}
				break;
			case "uninstall":
				if(comboBox.getSelectedIndex()!=0){	
					if(textField.getText().trim()!=null&&!textField.getText().trim().isEmpty()){
						boolean re=uninstallPackage(textField.getText().trim(),comboBox.getSelectedItem().toString());
						if(re){
							JOptionPane.showMessageDialog(null, "成功", null, JOptionPane.INFORMATION_MESSAGE);
						}else{
							JOptionPane.showMessageDialog(null, "失败", null, JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null, "请填入卸载包名", null, JOptionPane.ERROR_MESSAGE);
					}

				}else{
					if(textField.getText().trim()!=null&&!textField.getText().trim().isEmpty()){
						reViewComboBox();
						StringBuffer sb=new StringBuffer();
						for(int i=1;i<comboBoxv.size();i++){
							boolean suc =uninstallPackage(textField.getText().trim(), comboBoxv.get(i));
							if(!suc){
								sb.append(comboBoxv.get(i)+" ");								
							}
						}
						if(!sb.toString().isEmpty()){
							JOptionPane.showMessageDialog(null, "失败:"+sb.toString(), null, JOptionPane.ERROR_MESSAGE);
						}else{
							JOptionPane.showMessageDialog(null, "成功", null, JOptionPane.INFORMATION_MESSAGE);
						}	
					}else{
						JOptionPane.showMessageDialog(null, "请填入卸载包名", null, JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
			case "install":		
				if(comboBox.getSelectedIndex()!=0){
					if(FileOper.fileExist(textField.getText().trim())){	
						if(textField.getText().trim()!=null&&!textField.getText().trim().isEmpty()){
							boolean re=installPackage(textField.getText().trim(),comboBox.getSelectedItem().toString());
							if(re){
								JOptionPane.showMessageDialog(null, "成功", null, JOptionPane.INFORMATION_MESSAGE);
							}else{
								JOptionPane.showMessageDialog(null, "安装失败", null, JOptionPane.ERROR_MESSAGE);
							}
						}else{
							JOptionPane.showMessageDialog(null, "请填入安装包路径", null, JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null, "安装包不存在", null, JOptionPane.ERROR_MESSAGE);
					}
				}else{		
					
					if(FileOper.fileExist(textField.getText().trim())){	
						reViewComboBox();
						StringBuffer sb=new StringBuffer();
						for(int i=1;i<comboBoxv.size();i++){
							boolean suc =installPackage(textField.getText().trim(), comboBoxv.get(i));
							if(!suc){
								sb.append(comboBoxv.get(i)+" ");								
							}
						}
						if(!sb.toString().isEmpty()){
							JOptionPane.showMessageDialog(null, "失败:"+sb.toString(), null, JOptionPane.ERROR_MESSAGE);
						}else{
							JOptionPane.showMessageDialog(null, "成功", null, JOptionPane.INFORMATION_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null, "安装包不存在", null, JOptionPane.ERROR_MESSAGE);
					}

				}
				break;
			default:
				break;
			}
		}

	}

	private boolean installPackage(String path,String en) {
		String re = null;
		Process proc;
		String cmd = "adb -s "+en+" install "+path;
		try {
			proc = Runtime.getRuntime().exec(cmd);
			re=new TestResult(proc,0).call();
			if(re.contains("Success")){			
				return true;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}				

		return false;
	}
	private boolean uninstallPackage(String path,String en) {
		String re = null;
		Process proc;
		String cmd = "adb -s "+en+" uninstall "+path;
		try {
			proc = Runtime.getRuntime().exec(cmd);
			re=new TestResult(proc,0).call();
			if(re.contains("Success")){			
				return true;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}				

		return false;
	}
	private void reViewComboBox() {
		String cmd = null;
		String re = null;
		cmd="adb devices";
		String[] enquipmentes=null;
		String[] enquipment=null;
		for(int j=comboBoxv.size()-1;j>0;j--){
			comboBoxv.removeElementAt(j);
		}
		try {
			Process proc = Runtime.getRuntime().exec(cmd);				
			try {
				re=new TestResult(proc,0).call();		
				enquipmentes = re.split(";");
				for(int i=1;i<enquipmentes.length;i++){
					enquipment = enquipmentes[i].split("\t");
					comboBoxv.add(enquipment[0]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	private void qureyResult() {


	}	
	private void select(int flag) {
		JFileChooser fileChooser = new JFileChooser("G:\\");
		int returnVal = fileChooser.showOpenDialog(fileChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			selectPath=fileChooser.getSelectedFile().getAbsolutePath();
			//System.out.println("You chose to open this file: " +selectPath);
			if(flag==1){
				textField.setText(selectPath);
			}else if(flag==2){
				textSField1.setText(selectPath);
			}

		}
	}
	private void build() {
		filePath=textSField1.getText().trim();
		packageName=textSField2.getText().trim();
		OperateTestExcel op=new OperateTestExcel(filePath);
		cases=op.resolve();
		TestCase testCase;
		String str = null;
		boolean notEx;
		for(int caseNum=0;caseNum<cases.size();caseNum++){
			testCase=cases.get(caseNum);
			notEx=testCase.getIsExecute().contentEquals("否");
			if(testCase!=null&&!notEx){
				if(str==null){
					str=testCase.getNum()+";";
				}else{
					str=str+testCase.getNum()+";";
				}

			}
		}
		save(str, CurData.PATH);
		//textArea.setText(textArea.getText()+"\r\n生成"+PATH+packageName+".txt");
	}
	private  void save(String str,String fileName) {
		
		try {						
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName+packageName+".txt"));					
			if(str!=null){
				bufferedWriter.write(str);
				bufferedWriter.flush();
				bufferedWriter.close();
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof CaseExecuter||o instanceof HomeLunch){
			list.updateUI();
		}else if(o instanceof EnquipmentObser){
			opCurData();		
			reViewComboBox();
			if(!comboBoxv.contains(comboBox.getSelectedItem().toString())){
				comboBox.setSelectedIndex(0);
				comboBox.updateUI();
			}	
		}

	}

}
