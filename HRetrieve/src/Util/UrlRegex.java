package Util;

import java.net.URLConnection;

public class UrlRegex {
	//cn.tianya.bbs:http/post-house-529820-1.shtml
public static String getUrlOfProtocolandHost(String url){
	StringBuffer baseurl = new StringBuffer();
	String[]parts=url.substring(0,url.indexOf(":")).split("\\.");
	baseurl.append("http://");
	for(int i=parts.length-1;i>=0;i--){
			baseurl.append(parts[i]);
			if(i!=0){
				baseurl.append(".");
			}
	}
	return baseurl.toString();
}
public static String getUrlofResourcePath(String url){
		return url.substring(url.indexOf("/"));
}
public static String CombineUrl(String url){
	return getUrlOfProtocolandHost(url)+getUrlofResourcePath(url);
}
public static void main(String[] args) {
	String url="cn.tianya.bbs:http/post-house-529820-1.shtml";
	System.out.println(CombineUrl(url));
	}
public static String getEncoding(URLConnection con){
    String contentype=con.getContentType();
		if(contentype.matches("[\\w\\W]*;[\\w\\W]*")){
    return contentype.split(";")[1].split("=")[1];
	}
	return "gbk";
}
public static String urlToFileName(String url){
	return url.replace('\\','.').replace('/','.').substring(7);
}
}
