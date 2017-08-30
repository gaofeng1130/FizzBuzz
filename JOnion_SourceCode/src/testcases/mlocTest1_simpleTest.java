package testcases;

public class mlocTest1_simpleTest {
	
	int t11;
	int t12;
	
	public mlocTest1_simpleTest() {
		t11 = 0;
		t12 = 100;
		System.out.println("hello");
		System.out.println(t12 + " ");
	}
	
	public int mlocMethod1() {
		System.out.println("hello world");
		return t11;
	}

}
