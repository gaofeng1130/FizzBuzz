package main;

import java.io.File;
import java.io.IOException;

import visitors.StoreMetrics;
import visitors.VisitClasses;
import visitors.VisitPackage;
import visitors.VisitProject;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

public class MainRun {

	public static void main(String[] args) throws Exception {
		
		// //String address =
		// "/Users/emad/Documents/MSED_Workspace/Calculate Code Metrics/testcase/test001.java";
		// //String address =
		// "/Users/emad/Documents/MSED_Workspace/Calculate Code Metrics/src/visitors/VisitPackage.java";
		// String address =
		// "/Users/emad/Documents/MSED_Workspace/Calculate Code Metrics/src/testcases/nbdTest1_test.java";
		// CompilationUnit cu = JavaParser.parse(new File(address));
		// VisitClasses cv = new VisitClasses("nbdTest_test.java");
		// // // integer value passed is nested level
		// cv.visit(cu, 0);
		// cv.returnMetrics().printMetrics();
		// // return;

		// VisitPackage vf = new
		// VisitPackage("/Users/emad/Documents/MSED_Workspace/Calculate Code Metrics/testcase/");
		// vf.returnMetrics().printMetrics();

		String[] projectDirs = { "/Users/emad/Documents/MSED_Workspace/JAIM-0.4b/",
				"/Users/emad/Documents/MSED_Workspace/jtcgui-8/",
				"/Users/emad/Documents/MSED_Workspace/proguard-code/src/" };

		VisitProject vp = new VisitProject(projectDirs[2]);
		StoreMetrics st = vp.returnMetrics();
		
		// report: project-level
		st.printMetrics();
		
		// VisitClasses vc = new
		// VisitClasses("/Users/emad/Documents/MSED_Workspace/proguard-code/src/proguard/WordReader.java");
		// vc.returnMetrics().printMetrics();

	}

}
