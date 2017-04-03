package hadoop;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class TestsInputFormat
{
  public static class TokenizerMapper
    extends Mapper<Object, Text, Text, IntWritable>
  {
    private static final IntWritable one = new IntWritable(1);
    private Text word = new Text();
    
    public void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context)
      throws IOException, InterruptedException
    {
      StringTokenizer itr = new StringTokenizer(value.toString());
      while (itr.hasMoreTokens())
      {
        this.word.set(itr.nextToken());
        context.write(this.word, one);
      }
    }
  }
  
  public static class IntSumReducer
    extends Reducer<Text, IntWritable, Text, IntWritable>
  {
    private IntWritable result = new IntWritable();
    
    public void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context)
      throws IOException, InterruptedException
    {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      this.result.set(sum);
      context.write(key, this.result);
    }
  }
  
  public static void main(String[] args)
    throws Exception
  {
    Configuration conf = new Configuration();
//    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
//    if (otherArgs.length != 2)
//    {
//      System.err.println("Usage: wordcount <in> <out>");
//      System.exit(2);
//    }
    Job job = new Job(conf, "word count");
    job.setJarByClass(TestsInputFormat.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
   // JobTracker
    //Server
    //RPC
    
    //TaskTracker
    //Child
  }
}


