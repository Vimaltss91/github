 package demojenkin;

 interface face{
	 void print();
	 int i=5;
	 
	 
 }
 
public class interfaces implements face{

	public void print(){
		
		System.out.println("Interface");
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
interfaces i1= new interfaces();
i1.print();
		
	}

}
