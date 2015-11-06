package com.test.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

import com.test.dao.HistoryListItem;

public class HistoryListItemRenderer extends JPanel implements ListCellRenderer{

	private Border
	selectedBorder = BorderFactory.createLineBorder(Color.blue,1),
	emptyBorder = BorderFactory.createEmptyBorder(1,1,1,1);
	private JLabel packageName;
	private String time;
	private String[] his;
	
	public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus)
	{

		HistoryListItem item = (HistoryListItem)value;
		packageName=new JLabel();
		removeAll();
		this.add(packageName);
		his=item.getTime().split("-");
		time=his[0]+"年"+his[1]+"月"+his[2]+"日"+his[3]+"时"+his[4]+"分"+his[5]+"秒";
		packageName.setText(time+"        "+item.getPackgeName());
		if ( isSelected ) setBorder (selectedBorder);
		else setBorder(emptyBorder);
		return this;
	}
}
