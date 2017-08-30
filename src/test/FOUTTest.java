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

public class FOUTTest {

	@Test
	public void foutTest1_simpleTest() throws ParseException, IOException {
		VisitClasses vc = new VisitClasses("foutTest1_simpleTest.java");
		CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + "foutTest1_simpleTest.java"));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getAggregatedList();
		List<Double> mlocValues = metrics.get("FOUT");
		double total = mlocValues.get(0);
		double max = mlocValues.get(1);
		double avg = mlocValues.get(2);
		
		assertEquals(10, total, 0.001);
		assertEquals(6, max, 0.001);
		assertEquals(5, avg, 0.001);
	}

}
