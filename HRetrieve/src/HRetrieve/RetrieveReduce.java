package HRetrieve;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
	public  class RetrieveReduce extends Reducer<KeyNumber, Text, Text, Text> {
		public int rankNumber=10;
			Text k=new Text();
			protected void setup(Context context)
					throws IOException, InterruptedException {
				rankNumber=context.getConfiguration().getInt("rankNumber",10);
				System.out.println("rankNumber:"+rankNumber);
			}
			@Override
		protected void reduce( KeyNumber keynum, Iterable<Text> value, Context context)
				throws IOException, InterruptedException {
				int count=0;
				System.out.println("-------in-------");
				System.out.println("keyword:"+keynum.getKeyword());
				System.out.println("number:"+keynum.getNumber());
				Iterator<Text> it=value.iterator();
		      while(it.hasNext()){
		    	  if(count<rankNumber){
		    			 count++;
		    	  k.set(keynum.getKeyword());
		    	  Text text=it.next();
		    	  //System.out.println(keynum.getNumber());
		    	  System.out.println("content:"+text);
		    	context.write(k,text);
		    		    	  }
		    	  else{
		    		  break;
		    	  }
		      }
		      System.out.println("-----out------");
		}
	}


