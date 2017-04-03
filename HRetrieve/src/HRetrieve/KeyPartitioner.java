package HRetrieve;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;
public  class KeyPartitioner extends Partitioner<KeyNumber,Text> {
	@Override
	public int getPartition(KeyNumber key, Text value, int partitionsNumber) {
		System.out.println("teh partitions is using");
		return Math.abs(key.getKeyword().hashCode())%partitionsNumber;
		
	}

}
