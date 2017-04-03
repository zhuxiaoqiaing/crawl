package Util;

import java.io.File;
import java.io.IOException;
public class LocalCommand  implements Command{
private static LocalCommand local=null;
public static final String RM="rm -rf ";
public static final String CP="cp ";
public static final String MKDIR="mkdir ";
public static synchronized LocalCommand getInstance(){
	if(null==local){
		local=new LocalCommand();
	}
	return local;
}
	@Override
	public void uploadFiles(String src, String dst) {
		// TODO Auto-generated method stub
			}
	@Override
	public void deleteFiles(String src) {
		// TODO Auto-generated method stub
		String command=RM+src;
		Execute(command);
					}
	public void deleteParentDir(String dir){
		File file=new File(dir);
		deleteFiles(file.getParent());
	}
public void cp(String src,String dst){
	String command=CP+src+" "+dst;
	System.out.println("start cp");
	Execute(command);
}
	@Override
	public void downloadFiles(String dst, String src) {
		// TODO Auto-generated method stub
		
	}
public void  RemoveExistDirctory(String dir){
String command=RM+dir;
Execute(command);
}
public void createDiretory(String dir){
	String command=MKDIR+dir;
	Execute(command);
}
public void export(){
	String command="export DISPLAY=localhost:0";
	Execute(command);
}
public void Execute(String command) {
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
		System.out.println("execute runtime error"+this);
		}
	}
public static void main(String[] args) {
	String downloadDir=PropertyUtil.getPro("downloadDir");
	//LocalCommand.getInstance().deleteParentDir("/usr/hadoop/Retrieve/image");;
	LocalCommand.getInstance().createDiretory(downloadDir);
}
}
