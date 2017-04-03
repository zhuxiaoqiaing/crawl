package document;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import Util.HadoopCommand;
import Util.PropertyUtil;
import Util.Snap;
import Util.StoreFileDataToList;
import Util.UrlRegex;
import Util.VirtualKey;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.rtf.RtfWriter2;
import com.sun.glass.events.KeyEvent;
public class document
{
 public final static String separator=System.getProperty("line.separator");
 public static Document doc;
 public  static int width;
 public static int height;
 public static Font keyFont=new Font(Font.NORMAL,20,Font.BOLD,new Color(255,0,0));
 public static Font urlFont=new Font(Font.NORMAL,16,Font.BOLD,new Color(0,255,0));
 public static void addElement(Element ele){
	doc=((doc==null)?createDocument():doc);
	 if(!doc.isOpen()){
		 doc.open();
	 }
	if(ele instanceof Image){
		Image png=(Image) ele;
		   png.scaleAbsolute(width-100f,height-120f);
	       try {
			doc.add(png);
		} catch (DocumentException e) {
			System.out.println("document is not exist");
		}
	}
	else if(ele instanceof Paragraph){
		try {
			doc.add((Paragraph)ele);
		} catch (DocumentException e) {
		e.printStackTrace();
		}
	}
 }
  public static Document createDocument(){
	 Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
	  width = (int) d.getWidth();
	  height = (int) d.getHeight();
	 doc = new Document(new Rectangle(width-100f,height-120f));
	 try {
		RtfWriter2.getInstance(doc, 
				   new FileOutputStream(PropertyUtil.getPro("DataSource")));
	} catch (FileNotFoundException e) {
	System.out.println("the outputstream is not exist");
	}
	doc.addDocListener(new docListener());   
	return doc; 
 }
  public static void close(){
	  if(null!=doc)
	  doc.close();
  }
  public static void insertAllDocument(String dir){
	  System.out.println("start insert into document");
	  File file=new File(dir);
	  if(!file.exists()){
		file.mkdir();
			  }
	  File []files=file.listFiles();
	  for(File f:files){
		 		  insertDocument(f.getAbsolutePath());
				}
	  //close the document and send the email
	  try {
		  Robot r=new Robot();
		 Desktop.getDesktop().browse(new URL("http://www.baidu.com").toURI());
		 r.delay(2000);
		VirtualKey.KeyPressWithCtrl(r,KeyEvent.VK_W);
		Thread.sleep(1000);
		VirtualKey.KeyPressWithCtrl(r,KeyEvent.VK_W);
			} catch (Exception e) {
			e.printStackTrace();
	}
	  close();
	  System.out.println("insert into document is end");
	  }
    public static void insertDocument(String onefile)  {
    	List li=null;
    	try{
	  li=StoreFileDataToList.listFileData(onefile);}
    	catch(Exception e){
    		System.out.println("store file data to list is occur");
    		throw new RuntimeException("store file data to list is occur");
    	    	}
	 try {
		Snap.snap(li);
	} catch (Exception e) {
		System.out.println("snap is occur error");
		throw new RuntimeException("snap is occur error");
	}
	 String basedir=PropertyUtil.getPro("baseStore")+"/";
//	 File file=new File(basedir);
//	 	 	 if(!file.getParentFile().exists()){
//		 file.getParentFile().mkdir();
//	 }
//	 	 	 //clean the before directory
//	 	 	if(file.exists()){
//	 			 file.delete();
//	 		 }
//	 	 	else{
//	 	 		file.mkdir();
//	 	 	}
	 int count=0;
	Iterator<String> it=li.iterator();
	 while(it.hasNext()){
		 String v=it.next();
		 String[] values=v.split("----");
		 String key=values[0];
		 if("".equals(key)||" ".equals(key)||null==key){
			 continue;
		 }
		 String times=values[1];
		 String url=values[2];
		 System.out.println(url);
		 String filename=basedir+UrlRegex.urlToFileName(url)+".jpg";
		 //snap and put into the file
		 //add the keyword
		Paragraph pra=new Paragraph(key,keyFont);
		document.addElement(pra);
			//add the url
		 Paragraph va=new Paragraph(url+"----"+"times:"+times,urlFont);
		 document.addElement(va);
		 //add the image for the snap url
		 Image image=null;
		try {
			image = Image.getInstance(filename);
		} catch (Exception e) {
			System.out.println("can not add image");
		}
		 document.addElement(image);
			 }
	     	      	}
public static void main(String[] args) throws Exception {
	//String outputDir=PropertyUtil.getPro("outputDir");
	String downloadDir=PropertyUtil.getPro("downloadDir");
	//HadoopCommand.getCommandIntance().downloadFiles(outputDir+"part*",downloadDir);
	insertAllDocument(downloadDir);
}    
}
 