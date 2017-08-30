package testcases;

import java.util.Scanner;

public class foutTest1_simpleTest {

	double t11;
	int t12;

	public foutTest1_simpleTest() {
		t11 = 0;
		t12 = 100;
		// 1
		System.out.println("hello");
		// 1
		System.out.println(t12 + " ");
		// 2
		t11 = Math.sqrt(t12) + "hi".charAt(0);
		// 0
		// calling Class constructor is not calculated as method call which is correct
		Double dd = new Double(t12);
		// 2
		System.out.println(dd.toString());

		// total: 6
	}

	public int method1() {

		// 0
		Scanner sc = new Scanner(System.in);

		// 3
		String s = sc.nextLine().trim().substring(10);

		// 1
		System.out.println(s + " " + s);
		return 0;

		// total: 4
	}

}
