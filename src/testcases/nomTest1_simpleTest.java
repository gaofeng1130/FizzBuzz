package testcases;

import java.util.ArrayList;
import java.util.List;

// nom = 5, nsm = 2
public class nomTest1_simpleTest {
	
	int a = 0;
	int b = 0;
	
	public void method1() {
	}
	
	private int method2() {
		return 1;
	}
	
	protected List<Object> method3(int x) {
		List<Object> l = new ArrayList<Object>();
		l.add(x);
		return l;
	}
	
	static int getDefaultA() {
		return 10;
	}
	
	static int getDefaultB() {
		return 15;
	}

}

//nom = 2
class Clsss {
	
	double d = 0.1;
	double e = 0.2;
	double f = 0.3;
	
	private int method12() {
		return (int) (f / d);
	}
	
	// not counted in NOM
	public Clsss() {
		
	}
	
	public String method22(int a) {
		if (e > 0)
			return "yaaaaay";
		else
			return "blblblbl blah";
	}
	
}

// nom = 0
class Class3 {
	
	public Class3() {
		System.out.println("aoisdjaoisjdaoisjdaoisjdaosidj");
	}
	
}
