package com.test.exui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;  
	private String  path;  

	public ImagePanel(String path)  

	{  
		this.path = path;
		// acquire the image  

		try  

		{  

			image=ImageIO.read(new File(path));  


		}catch(IOException e)  

		{  

			e.getStackTrace();  

		}  

	}  



	//重载  

	public void paintComponent(Graphics g)  

	{  

		super.paintComponent(g);  



		if(image==null) return;  



		int imageWidth=image.getWidth(this);  

		int imageHeight=image.getHeight(this);  



		//draw the image in the upper left corner  

		g.drawImage(image, 0, 0, null);  



		//tile the image across the panel  



		for(int i=0;i*imageWidth<=getWidth();i++)  

			for(int j=0;j*imageHeight<=getHeight();j++)  

				if(i+j>0)  

					g.copyArea(0, 0, imageWidth, imageHeight, i*imageWidth, j*imageHeight);  
	}


	@Override
	public void mouseClicked(MouseEvent e) {

	}


	@Override
	public void mouseEntered(MouseEvent e) {

	}


	@Override
	public void mouseExited(MouseEvent e) {

	}


	@Override
	public void mousePressed(MouseEvent e) {

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {  

		}  

	}  

}  


