package test;

import static org.junit.Assert.*;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import visitors.StoreMetrics;
import visitors.VisitClasses;
import visitors.VisitMethods;

public class MLOCTest /* extends VoidVisitorAdapter<Integer> */{

	@Test
	public void mlocTest1_simpleTest() throws ParseException, IOException {
		VisitClasses vc = new VisitClasses("mlocTest1_simpleTest.java");
		CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + "mlocTest1_simpleTest.java"));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getAggregatedList();
		List<Double> mlocValues = metrics.get("MLOC");
		double total = mlocValues.get(0);
		double max = mlocValues.get(1);
		double avg = mlocValues.get(2);
		double num = mlocValues.get(3);

		assertEquals(6, total, 0.001);
		assertEquals(4, max, 0.001);
		assertEquals(3, avg, 0.001);
		assertEquals(2, num, 0.001);

		// File f = new File("testcases/mlocTest1.java");
		// CompilationUnit cu = JavaParser.parse(f);
		// // calculate and return method level metrics
		// visit(cu, 0);
		// List<Double> mlocValues = sm.getMetricsListTest().get("MLOC");
	}

	@Test
	public void mlocTest2_nested() throws ParseException, IOException {
		VisitClasses vc = new VisitClasses("mlocTest2_multipleClasses.java");
		CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + "mlocTest2_multipleClasses.java"));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getAggregatedList();
		List<Double> mlocValues = metrics.get("MLOC");
		double total = mlocValues.get(0);
		double max = mlocValues.get(1);
		double avg = mlocValues.get(2);
		double num = mlocValues.get(3);

		assertEquals(16, total, 0.001);
		assertEquals(3, max, 0.001);
		assertEquals(16/8, avg, 0.001);
		assertEquals(8, num, 0.001);

	}

	// @Override
	// public void visit(ClassOrInterfaceDeclaration n, Integer arg) {
	// VisitMethods vm = new VisitMethods(n.getName());
	// vm.visit(n, 0);
	// sm = vm.returnMetrics();
	// // not important here
	// super.visit(n, arg + 1);
	// }

}
