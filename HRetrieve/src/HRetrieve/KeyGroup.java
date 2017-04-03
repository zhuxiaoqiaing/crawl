package HRetrieve;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class KeyGroup extends WritableComparator {

	public KeyGroup(){
		super(KeyNumber.class,true);
	}
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		KeyNumber key1=(KeyNumber) a;
		KeyNumber key2=(KeyNumber) b;
	return key1.getKeyword().compareTo(key2.getKeyword());
	}
	
}
