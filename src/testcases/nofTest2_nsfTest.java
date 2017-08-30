package testcases;

// nof = 4, nsf = 4;
public class nofTest2_nsfTest {

	static int a = 0;
	static double d = 0.1;
	static int[] x = new int[100];
	static String s = "hello";

	// nof = 0, nsf = 0
	private class Cls1 {

		public Cls1() {
			String s = "test string";
			System.out.println(s);
		}

		public void m1Cls1() {

		}

	}

	// nof = 1, nsf = 1
	private class Cls2 {

		static final int a = 10000;

		public Cls2() {
		}

	}

	// nof = 2, nsf = 2
	private class Cls3 {

		static final String s3 = "static string";
		static final int aaaa = 4958495;

		public Cls3() {
		}
	}

}

// nof = 4, nsf = 4
class Cls444 {

	static double a = 0;
	static int b = 3;
	static String ss = "";
	static double k = 0.123;

}

// nof = 5, nsf = 5
class Cls22 {

	static final int a = 10000;
	static final int a1 = 10000;
	static final int a11 = 10000;
	static final int a111 = 10000;
	static final int a1111 = 10000;

	public Cls22() {
	}

}
