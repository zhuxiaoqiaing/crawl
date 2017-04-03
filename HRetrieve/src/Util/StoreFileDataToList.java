package Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StoreFileDataToList {
	
	public static List listFileData(String filename) throws IOException{
		int count=0;
		FileUtil fu=new FileUtil(filename,"R");
		ArrayList list=new ArrayList<String>();
		BufferedReader r=fu.getReader();
		String str=null;
		while(null!=(str=r.readLine())){
		String[] vals=str.split("\t");
		//process the bad recorder
       if(vals.length<2){
    	   continue;
       }
       if("".equals(vals[0])||" ".equals(vals[0])||null==vals[0]){
    	   continue;
       }
       String[] vas=vals[1].split("----");
       if(vas.length<2){
    	   continue;
       }
      list.add(vals[0]+"----"+vals[1]);
		//System.out.println(str);
			}
		fu.close();
		return list;
	}
		public static void main(String[] args) throws IOException {
		//亲情	8----http://bbs.tianya.cn/post-863-7551-1.shtml
		List list=StoreFileDataToList.listFileData("/usr/hadoop/Retrieve/results/part-r-00000");
		System.out.println(list);
	}
}
