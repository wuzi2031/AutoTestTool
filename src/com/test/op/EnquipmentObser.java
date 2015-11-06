package com.test.op;

import java.io.IOException;
import java.util.Observable;

public class EnquipmentObser extends Observable{
	private String oldRe=null;
	public EnquipmentObser() {
	}
	public void checkEnquipment() {
		new Thread(){
			@Override
			public void run() {
				String cmd = "adb devices";
				while(true){
					String re = null;
					try {
						Process proc = Runtime.getRuntime().exec(cmd);				
						try {
							re=new TestResult(proc,0).call();
							if(oldRe!=null){
								if(!re.contentEquals(oldRe)){
									setChanged();
									notifyObservers();
								}
							}					
							oldRe=re;
							Thread.sleep(3000);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}.start();

	}


}
