package com.test.op;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.TimeoutException;

public class SShot {
	private BufferedImage image = null;
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		AndroidDebugBridge.init(false); //
//		SShot screenshot = new SShot();
//		IDevice device = screenshot.getDevice();
//
//		for (int i = 0; i < 10; i++) {
//			Date date=new Date();
//			SimpleDateFormat df=new SimpleDateFormat("MM-dd-HH-mm-ss"); 
//			String nowTime = df.format(date);
//			screenshot.getScreenShot(device, "Robotium" + nowTime);
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}


	public void getScreenShot(IDevice device,String filename) {
		RawImage rawScreen = null;
		try {
			rawScreen = device.getScreenshot();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (AdbCommandRejectedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (rawScreen != null) {
			Boolean landscape = false;
			int width2 = landscape ? rawScreen.height : rawScreen.width;
			int height2 = landscape ? rawScreen.width : rawScreen.height;
			if (image == null) {
				image = new BufferedImage(width2, height2,
						BufferedImage.TYPE_INT_RGB);
			} else {
				if (image.getHeight() != height2 || image.getWidth() != width2) {
					image = new BufferedImage(width2, height2,
							BufferedImage.TYPE_INT_RGB);
				}
			}
			int index = 0;
			int indexInc = rawScreen.bpp >> 3;
		for (int y = 0; y < rawScreen.height; y++) {
			for (int x = 0; x < rawScreen.width; x++, index += indexInc) {
				int value = rawScreen.getARGB(index);
				if (landscape)
					image.setRGB(y, rawScreen.width - x - 1, value);
				else
					image.setRGB(x, y, value);
			}
		}
		try {
			ImageIO.write((RenderedImage) image, "PNG", new File("D:/Robotium/"
					+ filename + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}

	/**
	 * 获取得到device对象
	 * @return
	 */
	private IDevice getDevice(){
		IDevice device;
		AndroidDebugBridge bridge = AndroidDebugBridge
				.createBridge("adb", true);//如果代码有问题请查看API，修改此处的参数值试一下
		waitDevicesList(bridge);
		IDevice devices[] = bridge.getDevices();
		device = devices[0];
		return device;
	}

	/**
	 * 等待查找device
	 * @param bridge
	 */
	private void waitDevicesList(AndroidDebugBridge bridge) {
		int count = 0;
		while (bridge.hasInitialDeviceList() == false) {
			try {
				Thread.sleep(500); 
				count++;
			} catch (InterruptedException e) {
			}
			if (count > 240) {
				System.err.print("等待获取设备超时");
				break;
			}
		}
	}
}