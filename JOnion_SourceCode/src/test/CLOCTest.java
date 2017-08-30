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

public class CLOCTest {

	@Test
	public void clocTest1_nestedClassesTest() throws ParseException, IOException {
		VisitClasses vc = new VisitClasses("nomTest2_nestedClassesTest.java");
		CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + "nomTest2_nestedClassesTest.java"));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getMetricsList();

		List<Double> clocValues = metrics.get("CLOC");

		assertEquals(28, clocValues.get(0), 0.001);
		assertEquals(21, clocValues.get(1), 0.001);
		assertEquals(8, clocValues.get(2), 0.001);
		assertEquals(21, clocValues.get(3), 0.001);
	}

}
