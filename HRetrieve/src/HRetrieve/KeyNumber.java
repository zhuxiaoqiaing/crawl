package HRetrieve;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.WritableComparable;
public class KeyNumber implements WritableComparable<KeyNumber> {
private String keyword;
private int number;
	@Override
	public void readFields(DataInput in) throws IOException {
	keyword=in.readUTF();
	number=in.readInt();	
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public void write(DataOutput out) throws IOException {
	out.writeUTF(keyword);
	out.writeInt(number);
	}
	@Override
	public int compareTo(KeyNumber o) {
		if(this.keyword.equals(o.keyword)){
			return (this.number==o.number?0:(this.number>o.number?-1:1));
		}
		else{
			return keyword.compareTo(o.keyword);
		}
	}
	@Override
	public int hashCode() {
	return this.keyword.hashCode();
	}

}
