package test;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import Util.PropertyUtil;
import Util.Snap;
import Util.StoreFileDataToList;
import Util.UrlRegex;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.rtf.RtfWriter2;
public class TestDocument {
	 public static void main(String[] args) throws Exception {
		 Paragraph pra=new Paragraph("http://www.baidu.com");
		 Snap.snap("http://www.baidu.com");  
		 document.addElement(pra);
		 Image image=Image.getInstance("/usr/hadoop/Retrieve/image/www.baidu.com.jpg");
		 document.addElement(image);
		 Paragraph pra1=new Paragraph("http://www.ntu.edu.cn");
		 Snap.snap("http://www.baidu.com");
		 document.addElement(pra1);
		 Image image2=Image.getInstance("/usr/hadoop/Retrieve/image/www.ntu.edu.cn.jpg");
		 document.addElement(image2);
		 //System.out.println(image);
		 //document.close();
      	}
}
 class document
{
 public final static String separator=System.getProperty("line.separator");
 public static Document doc;
 public static void addElement(Element ele){
	doc=((doc==null)?createDocument():doc);
	 if(!doc.isOpen()){
		 doc.open();
	 }
	if(ele instanceof Image){
		Image png=(Image) ele;
		  png.scaleAbsolute(1024f-150f,768f-250f);
	       try {
	    	   //png.setAlignment(Image.MIDDLE);
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
	 int width = (int) d.getWidth();
	 int height = (int) d.getHeight();
	 doc = new Document(new Rectangle(width-150,height-250));
	 //doc=new Document(PageSize.A4);
	 try {
		RtfWriter2.getInstance(doc, 
				   new FileOutputStream(PropertyUtil.getPro("DataSource")));
	} catch (FileNotFoundException e) {
	System.out.println("the outputstream is not exist");
	}
	return doc;
 }
  public static void close(){
	  doc.close();
  }
  }
 