package hadoop;

public class sdf extends student{
	private int b=2;
	static{
		System.out.println("sdf static");
	}
public sdf(){
	System.out.println(a);
	System.out.println(b);
	System.out.println("constructor in sdf");
}
public static void main(String[] args) {
	sdf d=new sdf();
	}
}
class student{
public int a=1;
static {
	System.out.println("student static");
	
}
public student(){
	System.out.println();
	this.display();
	System.out.println(a);
	System.out.println("constructor in student");
}
public void display(){
final int x=3;	
}
}