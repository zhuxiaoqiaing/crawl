  package HRetrieve;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import document.document;
import Util.FileUtil;
import Util.HadoopCommand;
import Util.InjectUrl;
import Util.LocalCommand;
import Util.PropertyUtil;
public class StartClass extends Configured implements Tool {
	private static String urldir=PropertyUtil.getPro("urlDir");
	public static String directoryName;
  public static String  outputDir=PropertyUtil.getPro("outputDir");;
  public static String downloadDir=PropertyUtil.getPro("downloadDir");
  //set the internal when operate the download or uplaod file or it make inconformity
  public static int internal=1000*60;
  public static int rankNumber=Integer.parseInt(PropertyUtil.getPro("rankNumber"));
	static{
		directoryName=FileUtil.getDirectoryName(urldir);
			}
	@Override
	public int run(String[] args) throws Exception {
		//delete the output directory for fear of the error which existed the file
		Configuration conf = new Configuration();
		//delete the parent dir that use to store the file and dir which is create by program
		LocalCommand.getInstance().deleteParentDir(downloadDir);
		HadoopCommand.getCommandIntance().deleteFiles(outputDir);
		//wait for the complete the hdfs 
		Thread.sleep(internal);
		//save the url from the database into fixed file directory
		InjectUrl.saveUrl();
		//upload the file which contains url to the hdfs
		HadoopCommand.getCommandIntance().uploadFiles(urldir,"/usr/");
		Thread.sleep(internal*2);
		//upload the file which contains key which can fetch from the url content
		HadoopCommand.getCommandIntance().uploadFiles("/usr/hadoop/keys.txt","/usr/");
		//wait for the file upload or when you start the map reduce it will report the file not exist
		Thread.sleep(internal);
		conf.setInt("mapred.job.reuse.jvm.num.tasks",-1);
		// set the mapred.map.tasks
		//conf.setInt("mapred.map.tasks",Integer.parseInt(args[2]));
		// increase the map sort memory
		conf.setInt("io.sort.mb",120);
		conf.setFloat("mapred.reduce.slowstart.completed.maps",0.9f);
				// set the number of generate urls
		conf.setInt("rankNumber",rankNumber);
		//map task run at most 3 tasks
		//conf.setInt("mapred.tasktracker.map.tasks.maximum",3);
	   //conf.setInt("mapred.inmem.merge.threshold",0);
		//conf.setFloat("mapred.job.reduce.input.buffer.percent",1.0f);
		// conf.setBoolean("mapred.compress.map.output", true);
		// set the output compression
		// conf.setBoolean("mapred.output.compress", true);
		// conf.setClass("mapred.output.compression.codec",GzipCodec.class,CompressionCodec.class);
		// set teh distributedCache file
		DistributedCache.addCacheFile(new URI(
	   "hdfs://m0:9000/usr/keys.txt#keys.txt"), conf);
		DistributedCache.createSymlink(conf);
		Job job = new Job(conf, "url");
		job.setJobName("HRetrieve example");
	    // do not use map s[eculativeExectution
		job.setMapSpeculativeExecution(false);
		job.setReduceSpeculativeExecution(true);
		// set the number of reduce
		job.setNumReduceTasks(3);
		job.setJarByClass(StartClass.class);
		job.setMapperClass(UrlMap.class);
		//job.setCombinerClass(RetrieveCombine.class);
		job.setPartitionerClass(KeyPartitioner.class);
		job.setReducerClass(RetrieveReduce.class);
		//job.setSpeculativeExecution(false);
		job.setMapOutputKeyClass(KeyNumber.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setGroupingComparatorClass(KeyGroup.class);
		// use distributed cache
		// use symbol link
		//IdentityMapper
		FileInputFormat.addInputPath(job, new Path("hdfs://m0:9000/usr/StoreUrl"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://m0:9000"+outputDir));
		return (job.waitForCompletion(true) ? 0 : 1);
	}
	public static void main(String[] args) throws Exception {
		int exitCodec = ToolRunner.run(new StartClass(), args);
		//download the mapreduce outoutdir
		//System.exit(exitCodec);
		if(exitCodec==0){
			LocalCommand.getInstance().createDiretory(downloadDir);
			Thread.sleep(internal/2);
			LocalCommand.getInstance().export();
			Thread.sleep(1000*2);
			HadoopCommand.getCommandIntance().downloadFiles(outputDir+"part*",downloadDir);
			Thread.sleep(internal);
			document.insertAllDocument(downloadDir);
			
		}
					}
}
//Configuration
//RPC
//Server
//TaskScheduler
//TaskTracker
//Responser
