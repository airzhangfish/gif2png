package com.ikags.utils;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * 系统工具
 * @author airzhangfish
 *
 */
public class CommonUtil {

	/**
	 * 打开默认浏览器指定地址
	 * @param url
	 */
	public static void browserURL(String url){
		if (java.awt.Desktop.isDesktopSupported()) {
			try {
				// 创建一个URI实例
				java.net.URI uri = java.net.URI.create(""+url);
				java.awt.Desktop dp = java.awt.Desktop.getDesktop();
				if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
					dp.browse(uri);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 设置swing的外观. 暂时nember为0~2,三种.一般用2比较好看
	 * @param number
	 */
	public static void setMySkin(int number) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			if (number == 0) {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			} else if (number == 1) {
				// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				UIManager.setLookAndFeel("com.oyoaha.swing.plaf.oyoaha.OyoahaLookAndFeel");
			} else if (number == 2) {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} else {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static File createFile(String fileName) {
		File file = new File(fileName);
		if (file.isDirectory()) {
			return null;
		}
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return file;
	}
	
}
