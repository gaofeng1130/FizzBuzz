package testcases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class parTest1_simpleTest {

	int a1 = 0;
	int a2 = 0;
	int a3 = 0;

	// 3
	public parTest1_simpleTest(int newA1, int newA2, String helloStirng) {
		System.out.println(helloStirng);
	}

	// 3
	public double method1(ArrayList<String> aa, Scanner ss, PC1 pc1) {

		pc1 = new PC1("hello", a1 / 2.0);

		return aa.size() / 10;
	}
	
	// 0
	private void method4() {

	}
	
	// 1
	private void method5(int abcdefg) {
	}

	// 6
	private void method2(int i1, int i2, String s1, double d1, Double d2,
			ArrayList<ArrayList<ArrayList<String>>> hugeList) {
		return;
	}

	// 3
	private void method3(HashMap<String, PC1> hm, int[] intArr, long l) {
		System.out.println(hm.toString());
	}

	private class PC1 {

		int a = -1;

		// 2
		public PC1(String str, Double dd) {
			System.out.println("name is" + str);
		}

		// 4
		public int pcMethod1(int a, int b, int c, double d) {
			return a + b + c;
		}

	}

}
