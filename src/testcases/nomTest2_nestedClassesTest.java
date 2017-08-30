package testcases;

// nom = 2, nsf = 1
public class nomTest2_nestedClassesTest {

	public void method1() {
	}

	public static void method2() {
		System.out.println("Static method it is ...");
	}

	// nom = 3, nsm = 0
	private class Cls1 {

		private void method11() {}

		public int method12() {return 0;}

		public double method13() {
			for (int i = 0; i < 10; i++) { System.out.println(i); } return 0.1;
		}

		// nom = 2, nsm = 0
		private class InsideClass2 {

			private void inside1() {
				System.out.println("heeey");
			}

			public String inside2() {
				return "hooooy";
			}

		}

	}

}

// nom = 4, nsf = 2
class OuterClass1 {

	private static void outer1() {
		System.out.println("this is outside class");
	}

	public static void outer2() {

		// ?
		Thread tr1 = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true)
					System.out.println("hi");
			}
		});

		tr1.run();

		Thread tr2 = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true)
					System.out.println("bye");
			}
		});

		tr2.run();

	}

}
