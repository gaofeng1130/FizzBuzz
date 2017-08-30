package testcases;

public class mlocTest2_multipleClasses {

	public mlocTest2_multipleClasses() {
		// comment line 1
		// comment line 2
		// comment line 3
		int test1 = 0;
		System.out.println("some text");
	}

	private void test2m1() {
		// what about empty lines

		int a = 1 + 3;
	}

	private void test2m2() {
		String a0 = "hello if while this do is a" + "multiple line command to test";
		String a1 = a0 + " the end";
		System.out.println("hello test world!");
	}

	private class InnerClass1 {

		public InnerClass1() {
			int a = 0;
			double b = a + 0.1;
			System.out.println("class is ready.");
		}

		private void method1() {
			System.out.println("hello another time");
		}

	}

}

class Class1 {

	public Class1()
	{
		int a = 0;
		double b = a + 0.1;
		System.out.println("class is ready.");
	}

	public void method1() {System.out.println("hello another time");}

}

class Class2 {
	
	private void hiMethod() {
		
		Class1 cl1 = new Class1();
		cl1.method1();

	}
	
}
