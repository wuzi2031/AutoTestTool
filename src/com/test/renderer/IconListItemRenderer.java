package com.test.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

import com.test.dao.IconListItem;

/* 可以显示图标的ListCell绘制器 */
public class IconListItemRenderer extends JLabel implements ListCellRenderer
{
	private Border
	selectedBorder = BorderFactory.createLineBorder(Color.blue,1),
	emptyBorder = BorderFactory.createEmptyBorder(1,1,1,1);
	private String enName;
	public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus)
	{

		IconListItem item = (IconListItem)value;
		this.setIcon(item.getIcon());
		enName=item.getEnName();
		if(item.getEnName().length()<30){
			for(int i=0;i<30-item.getEnName().length();i++){
				enName=enName+" ";
			}			
		}
		if(item.getState()==null){
			this.setText(enName+"        "+item.getPackgeName()+"        "+item.getText());
		}else{
			this.setText(enName+"        "+item.getPackgeName()+"        "+item.getText()+"        "+item.getState());
		}
		

		if ( isSelected ) setBorder (selectedBorder);
		else setBorder(emptyBorder);
		return this;
	}
}