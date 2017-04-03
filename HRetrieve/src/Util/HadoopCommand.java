package Util;

import java.io.IOException;

import document.document;
public class HadoopCommand implements Command {
private static String command1="hadoop fs";
private static String upload="-put";
private static String download="-get";
private static String delete="-rmr";
private static String hdfsSuffix="hdfs://m0:9000";
private static HadoopCommand hadoopc;
public static Command getCommandIntance(){
	if(null==hadoopc){
		hadoopc=new HadoopCommand();
	}
	return hadoopc;
}
	@Override
	public  void uploadFiles(String src, String dst) {
		// delete the files before upload
		String directory=FileUtil.getDirectoryName(src);
		System.out.println(directory);
		deleteFiles(dst+directory);
		try {
			Thread.sleep(1000*10);
		} catch (InterruptedException e) {
			System.out.println("thread sleep in"+this);
		}
		String command=command1+" "+upload+" "+src+" "+hdfsSuffix+dst;
		System.out.println("upload the command"+command);
		Execute(command);
				}
	@Override
	public  void deleteFiles(String src) {
		//File file=new File(hdfsSuffix+src);
		String command=command1+" "+delete+" "+hdfsSuffix+src;
		System.out.println("delete file"+command);
		Execute(command);
		System.out.println("delete it right");
	}
	@Override
	public void downloadFiles(String dst, String src) {
		String command=command1+" "+download+" "+hdfsSuffix+dst+" "+src;
			Execute(command);
	}
	@Override
	public  void Execute(String command) {
		System.out.println(command);
			try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
		System.out.println("execute runtime error"+this);
		}
	
			//System.out.println("the command :"+command+"is executed successfully");
		}
public static void main(String[] args) {
////	HadoopCommand.getCommandIntance().downloadFiles("/usr/StoreUrl","/usr/hadoop/s");
//	//HadoopCommand.getCommandIntance().uploadFiles("/usr/hadoop/StoreUrl","/usr/");
	//HadoopCommand.getCommandIntance().downloadFiles("/usr/retrieve-results/part*","/usr/hadoop/Retrieve/results");
	String outputDir=PropertyUtil.getPro("outputDir");
	String downloadDir=PropertyUtil.getPro("downloadDir");
	HadoopCommand.getCommandIntance().downloadFiles(outputDir+"part-r-00000",downloadDir);
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("start doing document");
	//start the document and send mail
	document.insertAllDocument(downloadDir);
	System.out.println("stop document");
}
}
