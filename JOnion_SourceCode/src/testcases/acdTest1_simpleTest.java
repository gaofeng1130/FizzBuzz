package testcases;

public class acdTest1_simpleTest 
{
	//constructor 1
	public acdTest1_simpleTest()
	{
		Thread th=new Thread(new Runnable() {
			  @Override
			  public void run() {
			    // This implements Runnable.run
			  }
		});
	}
	
	//constructor 2
	public acdTest1_simpleTest(int a)
	{
		Thread pInstance = new Thread() {
			  public void read() {
			   System.out.println("anonymous ProgrammerInterview");
			  }
			 };
	}
}

