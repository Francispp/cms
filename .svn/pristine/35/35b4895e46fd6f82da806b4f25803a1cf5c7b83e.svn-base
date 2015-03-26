package com.cyberway.common.utils;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageLoader;

/**
 * 
 * @author Dicky 原创
 * @time 2013-9-20 0:07:28
 * @version 1.0
 */
public class ImageUtil {

	private static final Logger LOG = Logger.getLogger(ImageUtil.class);
	/**
	 * 中心切割
	 * 缩放剪切图片
	 * @param source
	 * @param target
	 * @param width
	 * @param height
	 * @param wh 原始宽高
	 * @return
	 */
	public static boolean scalePicture(String source, String target, Integer width, Integer height){
		String temp = null;
		try {
			File sfile = new File(source);
			source = sfile.getPath();
			Double[] wh = getImageSize(source);
			Double width1 = wh[0];
			Double height1 = wh[1];
			if(width1==0.0){
				return false;
			}
			if(width==null || width==0){
				if(width1>height1){
					width = 1600;
					height = ((Double)(width*height1/width1)).intValue();
				}else{
					height = 1600;
					width = ((Double)(height*width1/height1)).intValue();
				}
			}
			int Longway = width>height ? width : height;
			if(Longway>width1 && Longway>height1){
				return false;
			}
			File tfile = new File(target);
			target = tfile.getPath();
			int index = target.lastIndexOf(".");
			temp = target.substring(0,index)+ "0"+target.substring(index);
			if(width1/height1 == width.doubleValue()/height.doubleValue()){
				LOG.info("-相同比例切割-");
				Thumbnails.of(source).size(width, height).outputQuality(1.0).toFile(new File(target));
			}else if(width1/height1 < width.doubleValue()/height.doubleValue()){
				LOG.info("-高超出范围-");
				Double newHeight = width*height1/width1;
				Double y0 = (newHeight - height)/2;
				if(width.doubleValue()!=width1){
					Thumbnails.of(source).size(width, newHeight.intValue()).outputQuality(1.0).toFile(new File(temp));
					Thumbnails.of(temp).sourceRegion(0, y0.intValue(), width, height).size(width, height).outputQuality(1.0).toFile(new File(target));
				}else{
					Thumbnails.of(source).sourceRegion(0, y0.intValue(), width, height).size(width, height).outputQuality(1.0).toFile(new File(target));
				}
			}else{
				LOG.info("-宽超出范围-");
				Double newWidth =  width1*height/height1;
				Double x0 = (newWidth - width)/2;
				if(height.doubleValue()!=height1){
					Thumbnails.of(source).size(newWidth.intValue(), height).outputQuality(1.0).toFile(new File(temp));
					Thumbnails.of(temp).sourceRegion(x0.intValue(), 0, width, height).size(width, height).outputQuality(1.0).toFile(new File(target));
				}else{
					Thumbnails.of(source).sourceRegion(x0.intValue(), 0, width, height).size(width, height).outputQuality(1.0).toFile(new File(target));
				}
			}
			return true;
		} catch (Exception e) {
			LOG.error("-图片转化失败-", e);
		} finally{
			if(temp!=null){
				File file = new File(temp);
				if(file.exists()){
					file.delete();
				}
			}
		}
		return false;
	}
	
	public static Double[] getImageSize(String source){
		double width = 0.0;
		double height = 0.0;
		try {
			File info = new File(source);
			BufferedImage bi = ImageIO.read(info);
			width = bi.getWidth();
			height = bi.getHeight();
		}catch (Exception e) {
			LOG.error("-获取宽高错误-", e);
		}
		return new Double[]{width, height};
	}
	
	/**
	 * 将图片变成JPG格式
	 * @param srcFile
	 * @param targetFile
	 */
	public static void convertJPG(File srcFile, String targetFile){
		InputStream is = null;
		try {
			ImageLoader loader = new ImageLoader();
			is = new FileInputStream(srcFile);
			loader.load(is);
			loader.save(targetFile, SWT.IMAGE_JPEG);
		} catch (FileNotFoundException e) {
			LOG.error("--", e);
		} finally {
			try{
				if(is != null)is.close();
			}catch (Exception e) {
			}
		}
	}

	public static void main(String[] args) {
		//ImageData im = rotate(new ImageData("E:/20130911426.jpg"), SWT.RIGHT);
		/*ImageData imageData = new ImageData("E:/20130911426.jpg");
		ImageData  new_ = imageData.scaledTo(800, 600);
		saveImage("E:/test5.jpg", new ImageData[]{new_}, SWT.IMAGE_JPEG);*/
		/*try {
			BufferedImage bi = ImageIO.read(new File("E:/20130911426.jpg"));
			System.out.println("width:"+bi.getWidth());
			System.out.println("height:"+bi.getHeight());
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/
		
		try {
			//旋转时使用旋转前的像素
			Thumbnails.of("E:/20130908409.jpg").size(1536, 2024).rotate(-90.0).toFile(new File("E:/test3.jpg"));//sourceRegion(358, 660, 920, 671)
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//new File("E:\\test002.jpg").renameTo(new File("F:/20130911426.jpg"));
	}
}
