package HRetrieve;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class KeyComparator extends WritableComparator {
	
		public KeyComparator(){
			super(KeyNumber.class,true);
		}
		@Override
		public int compare(WritableComparable a, WritableComparable b) {
			KeyNumber key1=(KeyNumber) a;
			KeyNumber key2=(KeyNumber) b;
			return a.compareTo(b);
			}
		}
