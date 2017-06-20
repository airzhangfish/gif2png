package com.ikags.gif2png;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.ikags.utils.CommonUtil;
import com.ikags.utils.gif.GifDecoder;

/**
 * 主要界面
 * @author airzhangfish
 *
 */
public class Gif2pngViewer extends JFrame implements ActionListener {

	String mVerion="-Version 1.0.0 in 2014-2-26";
	String titlename = "Ika Gif2Png converter    "+mVerion;
	// 关于
	private String aboutStr = "Ika Gif2Png converter\n  " + "Creator by airzhangfish \n " + mVerion+"\n " + " E-mail:52643971@qq.com\n http://www.ikags.com/";

	private static final long serialVersionUID = 1L;
	private JTabbedPane jtp;
	
	private JMenuBar jMenuBar1 = new JMenuBar();
	private JMenu jMenuFile = new JMenu("File");
	private JMenuItem jMenuFileLoadImage = new JMenuItem("Read GIF file...");
	private JMenuItem jMenuFileSaveBigImage = new JMenuItem("Export to one big PNG file...");
	private JMenuItem jMenuFileSaveSmallImage = new JMenuItem("Export to fame PNG files...");
	private JMenuItem jMenuFileExit = new JMenuItem("Exit");

//	private JMenu jMenuEdit = new JMenu("编辑");
//	private JMenuItem jMenuEditRefresh = new JMenuItem("刷新资源/重加载资源");
//	private JMenuItem jMenuEditIsfill = new JMenuItem("碰撞区域填充/线框切换");
	
	private JMenu jMenuHelp = new JMenu("Help");
	private JMenuItem jMenuHelpAbout = new JMenuItem("About");
	private JMenuItem jMenuHelpHomepage = new JMenuItem("Homepage");
	BigImagePanel mapPanel = new BigImagePanel();

	public void actionPerformed(ActionEvent actionEvent) {
		Object source = actionEvent.getSource();


		if (source == jMenuFileLoadImage) {
			FileDialog xs = new FileDialog(this, "load png file", FileDialog.LOAD);
			xs.setFile("*.gif*");
			xs.setVisible(true);
			String f = xs.getFile();
			String lastDir = xs.getDirectory();
			if (f != null) {
			GifDef.ImageFilepath=lastDir + f;
			loadGifImage(GifDef.ImageFilepath);
			}
		}
		
		
		
		// 保存图片文件
		if (source == jMenuFileSaveBigImage) {
			FileDialog xs = new FileDialog(this, "save PNG file", FileDialog.SAVE);
			xs.setFile("*.*");
			xs.setVisible(true);
			String f = xs.getFile();
			String lastDir = xs.getDirectory();
			if (f != null) {
				saveBigPNG(lastDir + f);
			}
		}
		// 保存图片文件
		if (source == jMenuFileSaveSmallImage) {
			FileDialog xs = new FileDialog(this, "save PNG file", FileDialog.SAVE);
			xs.setFile("*.*");
			xs.setVisible(true);
			String f = xs.getFile();
			String lastDir = xs.getDirectory();
			if (f != null) {
				saveSmallPNG(lastDir + f);
			}
		}

		// 关于
		if (source == jMenuHelpAbout) {
			JOptionPane.showMessageDialog(this, aboutStr, "About", JOptionPane.INFORMATION_MESSAGE);
		}
		// 软件退出
		if (source == jMenuFileExit) {
			System.exit(0);
		}
		// 打开作者主页
		if (source == jMenuHelpHomepage) {
			CommonUtil.browserURL("https://github.com/airzhangfish/gif2png");
		}
	}

	
	/**
	 * 读取图片
	 * @param path
	 */
	public void loadMapImage(String path){
//		try{
//		GifDef.mMapImage = ImageIO.read(new File(path));
//		}catch(Exception ex){
//			JOptionPane.showMessageDialog(this, "读取图片错误,信息为:\r\n"+ex.getMessage(), "错误", JOptionPane.INFORMATION_MESSAGE);
//			ex.printStackTrace();
//		}
	}
	
	public void loadGifImage(String path) {
		try {
			GifDef.mFrameImageList.removeAllElements();
			GifDecoder d = new GifDecoder();
			d.read(path);
			int n = d.getFrameCount();
			for (int i = 0; i < n; i++) {
				BufferedImage frame = d.getFrame(i); // frame i
				int time = d.getDelay(i); // display duration of frame in milliseconds
				// do something with frame
				if (frame != null) {
					//System.out.println((i + 1) + "_frame:w=" + frame.getWidth() + "," + frame.getHeight() + ",delay=" + time);
					GifDef.mFrameImageList.add(frame);
				}
			}
			if(GifDef.mFrameImageList.size()>0){
				BufferedImage frame=GifDef.mFrameImageList.elementAt(0);
				System.out.println("framesize="+n+",(" + frame.getWidth() + "x" + frame.getHeight()+")");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "read image file error ,infomation :\r\n" + ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
		}
	}
	


	//保存单帧图片
	public void saveBigPNG(String path) {
		try {
			if(GifDef.mFrameImageList.size()==0){
				return;
			}
			System.out.println("savePNG="+GifDef.mBigmapWidth+"x"+GifDef.mBigmapHeight);
			BufferedImage bfimg = new BufferedImage(GifDef.mBigmapWidth, GifDef.mBigmapHeight, BufferedImage.TYPE_INT_ARGB);
				Graphics gg = bfimg.getGraphics();
//				Graphics2D g2 = (Graphics2D) gg;
//				BigImagePanel.drawImages(0, 0, g2);
				
				int size=GifDef.mFrameImageList.size();
				int linoffX=0;
				int linoffY=0;
				int maxwidth=0;
				int lastHeight=0;
				for(int i=0;i<size;i++){
					BufferedImage frame=GifDef.mFrameImageList.elementAt(i);
				
					gg.drawImage(frame, linoffX, lastHeight, null);	
					linoffX=linoffX+frame.getWidth();
					linoffY=Math.max(linoffY, frame.getHeight());
					maxwidth=Math.max(linoffX, maxwidth);
					if(linoffX>1000){
						lastHeight=lastHeight+linoffY+5;
						linoffX=0;
						linoffY=0;
					}
				}
				File pngfile=new File(path+".png");
			    ImageIO.write(bfimg, "png", pngfile);
				
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "save PNG error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	//保存单帧图片
	public void saveSmallPNG(String path) {
		try {
			if(GifDef.mFrameImageList.size()==0){
				return;
			}
				int size=GifDef.mFrameImageList.size();
				for(int i=0;i<size;i++){
					BufferedImage frame=GifDef.mFrameImageList.elementAt(i);
					BufferedImage bfimg = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
					Graphics gg = bfimg.getGraphics();
					gg.drawImage(frame, 0, 0, null);	
					File pngfile=new File(path+"_"+i+".png");
					System.out.println("savePNG_id="+i+","+frame.getWidth()+"x"+ frame.getHeight());
				    ImageIO.write(bfimg, "png", pngfile);	
				    
				}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "save PNG error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Gif2pngViewer() {

		this.setSize(800, 600); // 窗体的大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true); // 窗体
		this.setTitle(titlename); // 设置标题

		enableInputMethods(true);

		jMenuFile.add(jMenuFileLoadImage);
		jMenuFile.add(jMenuFileSaveBigImage);
		jMenuFile.add(jMenuFileSaveSmallImage);
		jMenuFile.add(jMenuFileExit);
		jMenuFileLoadImage.addActionListener(this);
		jMenuFileSaveBigImage.addActionListener(this);
		jMenuFileSaveSmallImage.addActionListener(this);
		jMenuFileExit.addActionListener(this);
		
		
		jMenuHelp.add(jMenuHelpAbout);
		jMenuHelpAbout.addActionListener(this);
		jMenuHelp.add(jMenuHelpHomepage);
		jMenuHelpHomepage.addActionListener(this);
		// 总工具栏

		jMenuBar1.add(jMenuFile);
		jMenuBar1.add(jMenuHelp);
		this.setJMenuBar(jMenuBar1);

		Container contents = getContentPane();
		jtp = new JTabbedPane(JTabbedPane.TOP);
		jtp.addTab("GIF2PNG", mapPanel);
		contents.add(jtp);
		setVisible(true);
	}

	public static void main(String args[]) {
		CommonUtil.setMySkin(2);
		new Gif2pngViewer();
	}
	

}
