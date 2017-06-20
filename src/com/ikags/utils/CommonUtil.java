package com.ikags.utils;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * ϵͳ����
 * @author airzhangfish
 *
 */
public class CommonUtil {

	/**
	 * ��Ĭ�������ָ����ַ
	 * @param url
	 */
	public static void browserURL(String url){
		if (java.awt.Desktop.isDesktopSupported()) {
			try {
				// ����һ��URIʵ��
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
	 * ����swing�����. ��ʱnemberΪ0~2,����.һ����2�ȽϺÿ�
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
