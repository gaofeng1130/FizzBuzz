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

public class NOFTest {

	@Test
	public void NOFTest1_simpleTest() throws ParseException, IOException {
		VisitClasses vc = new VisitClasses("nofTest1_simpleTest.java");
		CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + "nofTest1_simpleTest.java"));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getMetricsList();
		
		List<Double> nofValues = metrics.get("NOF");
		assertEquals(5, nofValues.get(0), 0.001);
		assertEquals(0, nofValues.get(1), 0.001);
		assertEquals(1, nofValues.get(2), 0.001);
		assertEquals(1, nofValues.get(3), 0.001);
		assertEquals(4, nofValues.get(4), 0.001);
		
		nofValues = metrics.get("NSF");
		assertEquals(0, nofValues.get(0), 0.001);
		assertEquals(0, nofValues.get(1), 0.001);
		assertEquals(0, nofValues.get(2), 0.001);
		assertEquals(1, nofValues.get(3), 0.001);
		assertEquals(2, nofValues.get(4), 0.001);
	}
	
	@Test
	public void NOFTest2_nsfTest() throws ParseException, IOException {
		VisitClasses vc = new VisitClasses("nofTest2_nsfTest.java");
		CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + "nofTest2_nsfTest.java"));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getMetricsList();
		
		List<Double> nofValues = metrics.get("NOF");
		assertEquals(4, nofValues.get(0), 0.001);
		assertEquals(0, nofValues.get(1), 0.001);
		assertEquals(1, nofValues.get(2), 0.001);
		assertEquals(2, nofValues.get(3), 0.001);
		assertEquals(4, nofValues.get(4), 0.001);
		assertEquals(5, nofValues.get(5), 0.001);
		
		nofValues = metrics.get("NSF");
		assertEquals(4, nofValues.get(0), 0.001);
		assertEquals(0, nofValues.get(1), 0.001);
		assertEquals(1, nofValues.get(2), 0.001);
		assertEquals(2, nofValues.get(3), 0.001);
		assertEquals(4, nofValues.get(4), 0.001);
		assertEquals(5, nofValues.get(5), 0.001);
	}

}
