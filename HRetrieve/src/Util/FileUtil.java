package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
	private Reader reader;
	private Writer writer;
	private String m = "R";

	public FileUtil(String Dir, String m) throws IOException {
		this.m = m;
		if (this.m == "R") {
			File file = new File(Dir);
			if (!file.getParentFile().getParentFile().exists()) {
				file.getParentFile().getParentFile().mkdir();
			}
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
			reader = new BufferedReader(new FileReader(Dir));
		} else if (this.m == "W") {
			File file = new File(Dir);
			if (!file.getParentFile().getParentFile().exists()) {
				file.getParentFile().getParentFile().mkdir();
			}
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
					writer = new BufferedWriter(new FileWriter(Dir));
		}
	}

	public String readLine() throws IOException {
		return ((BufferedReader) reader).readLine();
	}

	public void writeLine(String context) throws IOException {
		writer.write(context);
		writer.write("\r\n");
	}
  
	public void closeReader() throws IOException {
		if (reader != null)
			reader.close();
			}
publci void closeWriter(){
	if (writer != null)
		writer.close();
}
public void close()throws IOException{
	closeReader();
	closeWriter();
}
	public BufferedReader getReader() {
		return (BufferedReader) reader;
	}

	/**
	 * such as /usr/hadoop/sur-----get the sur
	 * 
	 * @param get
	 *            the path of the last part
	 */
	public static String getDirectoryName(String directory) {
		String directoryName = null;

		String[] us = directory.split("/");
		directoryName = us[us.length - 1];
		if (" ".equals(directoryName) || "".equals(directoryName)) {
			directoryName = us[us.length - 2];
		}
		return directoryName;
	}
	public static void zipFile(String srcf) throws IOException{
		File srcfile=new File(srcf);
		String zipf=null;
		String tmpzipf=srcf.substring(0,srcf.lastIndexOf("."))+".zip";
        zipf=(srcf.lastIndexOf(".")==-1)?srcf+".zip":tmpzipf;
		System.out.println(zipf);
		File zipfile=new File(zipf);
	    byte[] buf=new byte[2<<10];
	          //ZipOutputStream
	        ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipfile));
	                  FileInputStream in=new FileInputStream(srcfile);
	            out.putNextEntry(new ZipEntry(srcfile.getName()));
	            int len;
	            while((len=in.read(buf))>0){
	                out.write(buf,0,len);
	            }
	            out.closeEntry();
	            in.close();
	            out.close();
	            //alter the attributes of the dataSource
	            PropertyUtil.setPro("DataSource",zipf);
	        System.out.println("compress completely");
	   }
}// InputStream
