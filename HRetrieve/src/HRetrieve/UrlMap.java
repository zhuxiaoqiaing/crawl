package HRetrieve;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Util.UrlRegex
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

import com.sun.webkit.network.URLs;

import Util.EncodingUtil;
public class UrlMap extends Mapper<Object, Text, KeyNumber, Text> {
	// private static final Log LOG = LogFactory.getLog(spider.class);
	byte[] b = new byte[1024];
	public BufferedReader br;
	public int[] times;
	public List<String> list = new ArrayList<String>();
	public KeyNumber keyword = new KeyNumber();
	public Text wordvalue = new Text();
	public boolean isFirst[];
	Pattern p;
	Matcher m;
	@Override
	// LineRecordReader
	public void setup(Context context) throws IOException {
		// DistributedFileSystem.getCacheFiles(context.getConfiguration());
		URI[] url = DistributedCache.getCacheFiles(context.getConfiguration());
		FileSystem fs = FileSystem.get(URI.create("hdfs://m0:9000"),
				context.getConfiguration());
		InputStream allis = fs.open(new Path(url[0].getPath()));
		br = new BufferedReader(new InputStreamReader(allis));
		String regex = null;
		while (null != (regex = br.readLine())) {
			list.add(regex);
		}
		if (null != br)
			br.close();
		times = new int[list.size()];
		isFirst = new boolean[list.size()];
		for (int i = 0; i < isFirst.length; i++) {
			isFirst[i] = true;
		}
	}

	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
		// System.out.println("content----------"+value);
		String strurl = value.toString();
		// System.out.println("url:"+strurl);
		// LOG.info(strurl);
		InputStream is = null;
		URLConnection con = null;
		String encode = null;
		String line = null;
		try {
			URL urls = new URL(strurl);
			con=urls.openConnection();
			con.setConnectTimeout(10000);
			is = con.getInputStream();
		} catch (Exception e) {
			System.out.println(strurl + "---is not suitable");
			if(is!=null)
				is.close();
			return;
		}
		if (null == is) {
			return;
		}
		//System.out.println("start stream");
				try {
			// get the corrsponded encode
			encode = urlRegex.getEncoding(con);
		} catch (Exception e) {
			is.close();
			return;
		}
		//System.out.print("start parse");
		// translate the byte to char in special encode
		BufferedReader bi = new BufferedReader(
				new InputStreamReader(is, encode));
		while (null != (line = bi.readLine())) {
			// System.out.println("line:---"+s);
			String regex = null;
			// read keys from distributedcache file
			for (int i = 0; i < list.size(); i++) {
				regex = list.get(i);
				p=Pattern.compile(regex);
				m=p.matcher(line);
				if (m.find()) {
					++times[i];
					// LOG.info(line+"match:"+strurl);
				}// end if
			} // end for
		}// end while
		for (int i = 0; i < times.length; i++) {
			if (times[i] != 0) {
				if (times[i] == 1) {
					// if the keyword url is the first then write or go to the
					// next circle
					if (isFirst[i] == true) {
						isFirst[i] = false;
						keyword.setKeyword(list.get(i));
						keyword.setNumber(times[i]);
						wordvalue.set(times[i]+"----" + value.toString());
						context.write(keyword, wordvalue);
					}
				} else {
					keyword.setKeyword(list.get(i));
					keyword.setNumber(times[i]);
					wordvalue.set(times[i]+"----"+ value.toString());
					context.write(keyword, wordvalue);
				}
			}
		}// end if
			// close the stream
			//System.out.println("the stream is close");
		if (null != bi)
			bi.close();
		// make sure the times to zero for the next map
		for (int i = 0; i < times.length; i++) {
			times[i] = 0;
		}
	} // end function
}
