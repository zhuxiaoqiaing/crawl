package Util;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
/**
 * 
 *@param value --- snap url
 *@param imagefile ---the image where save 
 */
public class Snap{
	public static Robot robot;
	public static int width;
	public static int height;
	public  static boolean isFirst=true;
	public static final String baseDir=PropertyUtil.getPro("baseStore");
	/*
	 * init the directionary for saving the image after the snap
	 * get the screen width and height for the scrrensnot
	 */
static{
	File file=new File(baseDir);
	if(file.exists()){
		file.delete();
	}
	  file.mkdir();
	Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
	 width = (int) d.getWidth();
	 height = (int) d.getHeight();
}
/*
 *@note:snap the url and save in the form of image 
  */
	public static void snap(String url)throws Exception{
		      //System.out.println(baseDir);
				String imagefile=baseDir+UrlRegex.urlToFileName(url)+".jpg";
				System.out.println(imagefile);
				 robot = new Robot();
									robot.delay(3000);
			   Desktop.getDesktop().browse(new URL(url).toURI());
			// 最大化浏览器
		 		robot.keyRelease(KeyEvent.VK_F11);
		robot.delay(5000);
		Image image = robot.createScreenCapture(new Rectangle(50,100,width-100,
				height-120));
		//close the current page
		robot.delay(2000);
		//do not close the first page
		if(isFirst==false){
		VirtualKey.KeyPressWithCtrl(robot,KeyEvent.VK_W);}
		isFirst=false;
		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		// 保存图片
		ImageIO.write(bi, "jpg", new File(imagefile));
					}
	/*
	 * for each snap(String data)
	 */
public static void snap(List list)throws Exception{
Iterator it=list.iterator();
while(it.hasNext()){
	String url=parsePerUrl((String) it.next());
	System.out.println(url);
   if(null!=url)
	   snap(url);
}
}
/*
  *@param data the data from the map-reduce in the form of key----times----url
  *@return   String-the real url for the snap
 */
public static String parsePerUrl(String data){
	if(data.indexOf("----")==-1){
		return null;
	}
	String[] url=data.split("----");
	if(url.length<2){
		return null;
	}
	return url[2];
}
public static void main(String[] args) throws Exception {
	ArrayList<String> list=new ArrayList<String>();
	list.add("d----dd----http://www.baidu.com");
	list.add("dfe----fdfdf----http://focus.tianya.cn");
	list.add("dfsd----efefwe----http://www.taobao.com");
	snap(list);
	System.out.println("fsd");
	}
}
