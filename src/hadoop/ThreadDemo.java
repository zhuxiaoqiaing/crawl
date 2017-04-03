package hadoop;

public class ThreadDemo {
public static void main(String[] args) {
	final business si=new business();
     new Thread(){
    	 public void run(){
    		 for(int i=0;i<50;i++)
    		 si.sub();
    	 }
    	 
     }.start();
     for(int i=0;i<50;i++){
    	 si.main();
     }
}
}
class business{
	boolean isSub=true;
	public synchronized void sub(){
		while(!isSub){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	for(int i=0;i<10;i++){
		System.out.println(Thread.currentThread().getName()+"---"+i);
	}
	isSub=false;
	this.notify();
	}
	public synchronized void main(){
		while(isSub){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int i=0;i<10;i++){
			System.out.println(Thread.currentThread().getName()+"---"+i);
		}
		isSub=true;
		this.notify();
	}

}
