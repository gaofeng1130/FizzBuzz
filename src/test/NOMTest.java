package test;

import static org.junit.Assert.*;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import visitors.VisitClasses;

public class NOMTest {

	@Test
	public void nomTest1_simpleTest() throws ParseException, IOException {
		VisitClasses vc = new VisitClasses("nomTest1_simpleTest.java");
		CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + "nomTest1_simpleTest.java"));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getMetricsList();
		
		List<Double> nomValues = metrics.get("NOM");
		assertEquals(5, nomValues.get(0), 0.001);
		assertEquals(2, nomValues.get(1), 0.001);
		assertEquals(0, nomValues.get(2), 0.001);
		
		nomValues = metrics.get("NSM");
		assertEquals(2, nomValues.get(0), 0.001);
		assertEquals(0, nomValues.get(1), 0.001);
		assertEquals(0, nomValues.get(2), 0.001);
	}
	
	@Test
	public void nomTest2_nestedClassesTest() throws ParseException, IOException {
		VisitClasses vc = new VisitClasses("nomTest2_nestedClassesTest.java");
		CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + "nomTest2_nestedClassesTest.java"));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getMetricsList();
		
		List<Double> nomValues = metrics.get("NOM");
		assertEquals(2, nomValues.get(0), 0.001);
		assertEquals(3, nomValues.get(1), 0.001);
		assertEquals(2, nomValues.get(2), 0.001);
		assertEquals(4, nomValues.get(3), 0.001);
		
		nomValues = metrics.get("NSM");
		assertEquals(1, nomValues.get(0), 0.001);
		assertEquals(0, nomValues.get(1), 0.001);
		assertEquals(0, nomValues.get(2), 0.001);
		assertEquals(2, nomValues.get(3), 0.001);
	}

}
