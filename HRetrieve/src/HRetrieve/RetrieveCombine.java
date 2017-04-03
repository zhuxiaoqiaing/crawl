package HRetrieve;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
public class RetrieveCombine extends Reducer<KeyNumber,Text, KeyNumber,Text> {
public static  int rankNumber=10;
	@Override
protected void setup(Context context)
		throws IOException, InterruptedException {
	rankNumber=context.getConfiguration().getInt("rankNumber",10);
	System.out.println("rankNumber:"+rankNumber);
}
	@Override
	protected void reduce(KeyNumber keynumber, Iterable<Text> values,
		Context context)
			throws IOException, InterruptedException {
		int count=0;
		for(Text text:values){
			if(count<rankNumber){
				++count;
				//System.out.println(keynumber.getKeyword()+"----"+text);
			context.write(keynumber,text);
					}
			else{
				System.out.println("count:"+count+"conbine out");
				break;
			}
		}
		System.out.println("count:----"+count);
	}
}
