package testcases;

// nof = 5, nsf = 0;
public class nofTest1_simpleTest {

	int a = 0;
	int aa = 0;
	int aaa = 0;
	double d = 0.1;
	int[] x = new int[100];
	
	// nof = 0, nsf = 0
	private class Cls1 {

		public Cls1() {
			String s = "test string";
			System.out.println(s);
		}

		public void m1Cls1() {

		}

	}

	// nof = 1, nsf = 0
	private class Cls2 {

		int a;

		public Cls2() {
			a = 0;
		}

	}

	// nof = 1, nsf = 1
	private class Cls3 {

		static final String s3 = "static string";

		public Cls3() {
		}
	}

}

// nof = 4, nsf = 2
class Cls4 {

	double a = 0;
	static int b = 3;
	String ss = "";
	static double k = 0.123;

}
