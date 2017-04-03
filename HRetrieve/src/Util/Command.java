package Util;
import java.io.IOException;
public interface Command {
//upload file if the file exist then delete it	
public static void uploadFiles(String src,String dst);
//delete file
public static void deleteFiles(String src);
//download file
public static void downloadFiles(String dst,String src);
//execute the command by calling the linux shell
public static void Execute(String command);
}
