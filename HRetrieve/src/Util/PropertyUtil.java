package Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class PropertyUtil {
public static Properties pro=new Properties();
public static String pr="config.properties";
static{
	try {
		pro.load(PropertyUtil.class.getClassLoader().getResourceAsStream(pr));
		} catch (FileNotFoundException e) {
		System.out.println("file not exception");
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println("io exception");
	}
}
public static String getPro(String name){
	return pro.getProperty(name);
		}
public static void setPro(String name,String value){
	pro.setProperty(name,value);
//	try {
//		pro.store(new FileOutputStream(pr),"update"+name+"---"+value);
//	} catch (FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
}
public static void main(String[] args) {
//System.out.println(pro);
	//String s1=getPro("username");
	//   System.out.println(s1);
		String s=getPro("url");
		System.out.println(s);
}
}

