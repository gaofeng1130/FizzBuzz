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

public class NBDTest {

	@Test
	public void nbdTest1_simpleTest() throws ParseException, IOException {
		VisitClasses vc = new VisitClasses("nbdTest1_simpleTest.java");
		CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + "nbdTest1_simpleTest.java"));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getAggregatedList();
		List<Double> nbdValues = metrics.get("NBD");
		double total = nbdValues.get(0);
		double max = nbdValues.get(1);
		double avg = nbdValues.get(2);

		assertEquals(17, total, 0.001);
		assertEquals(4, max, 0.001);
		assertEquals(17.0 / 6, avg, 0.001);
	}

}
