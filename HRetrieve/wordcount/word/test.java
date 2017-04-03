package word;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
public class test {
public static class map extends Mapper<Object,Text,Text,Text>{
	@Override
	protected void map(Object key, Text value,
			Mapper<Object, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
			super.map(key, value, context);
	}
	}
public static class reduce extends Reducer<Text,Text,Text,Text>{

	@Override
	protected void reduce(Text arg0, Iterable<Text> arg1,
			Reducer<Text, Text, Text, Text>.Context arg2) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		super.reduce(arg0, arg1, arg2);
	}
	}
public static void main(String[] args) {
	Configuration conf=new Configuration();
	//Job job=new Job("wordcount",conf);
}
}
