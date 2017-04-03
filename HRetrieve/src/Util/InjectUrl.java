package Util;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Util.ConnectionUtil;
import Util.FileUtil;
import Util.PropertyUtil;
import Util.UrlRegex;
public class InjectUrl {
	public static final int countMax=Integer.parseInt(PropertyUtil.getPro("MAXUrl"));
public static boolean saveUrl() throws SQLException, IOException{
		String urlDir=PropertyUtil.getPro("urlDir");
	FileUtil fileutil=new FileUtil(urlDir+"data.txt","W");
	File file=new File(urlDir);
   if(file.exists()){
   	file.delete();
  }
	file.mkdir();

	System.out.println(urlDir);
	//acount the total number of url 
	int count=0;
	//file count
	int filenumber=0;
	//the per file contains how many urls
	int innercount=0;
	int allcount=0;
	//the part file that contains the numbers of urls
	int FileCount=0;
	try{
	FileCount=Integer.parseInt(PropertyUtil.getPro("FileCount"));}
	catch(Exception e){
		FileCount=6;
	}
	int partSize=0;
	Connection con=DataBaseUtil.getConnection();
		String sql="select id from webpage limit 100";
	String sqlc="select count(*) from webpage limit 100";
	PreparedStatement pc=con.prepareStatement(sqlc);
	if(null==pc)
		return false;
	ResultSet rc=pc.executeQuery();
	if(rc.next()){
		allcount=rc.getInt(1);
	}
	partSize=(allcount+FileCount-1)/FileCount;
	System.out.println("partSize:"+partSize);
	System.out.println(allcount+":----"+allcount);
	PreparedStatement pst=con.prepareStatement(sql);
		if(null==pst)
		return false;
	ResultSet rs=pst.executeQuery();
	if(null==rs){
		System.out.println("rs is null");
		return false;
	}
	System.out.println("start inject url");
	while(rs.next()){
			if(count==countMax){
			break;
					}
		//make the fixed number url into the different files
		if(innercount==partSize){
			filenumber++;
			//close the before stream
			fileutil.close();
			//open the new stream
			fileutil=new FileUtil(urlDir+"data"+filenumber+".txt","W");
			innercount=0;
		}
		String id=rs.getString(1);
		String url=UrlRegex.CombineUrl(id);
		//if the url can not access to then filter it and do not put it to the file
		//if(FilterUrl.parseUrl(url)==false){
		//	continue;
		//}
		fileutil.writeLine(url);
		innercount++;
		count++;
	}
	if(null!=fileutil)
	fileutil.close();
	System.out.println("save url successfully");
	System.out.println("allurl"+countMax);
	System.out.println("url"+count);
			return true;
}
public static void main(String[] args) throws Exception, IOException {
	System.out.println("main start");
saveUrl();
}
}
