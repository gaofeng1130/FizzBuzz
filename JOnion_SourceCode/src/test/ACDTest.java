package test;

import static org.junit.Assert.assertEquals;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import visitors.VisitClasses;
import visitors.VisitPackage;
import visitors.VisitProject;

public class ACDTest 
{
	@Test
	public void acdTest1_simpleTest() throws Exception {
		String projectPath = "/Users/fenggao/Documents/workspace/TestedProject";
		VisitPackage vp = new VisitPackage(projectPath + "/src/a1");
		//CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + fileName));
		//vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vp.returnMetrics().getMetricsList();
		List<Double> acdValues = metrics.get("ACD");
		double number1 = acdValues.get(0);
		//double max = acdValues.get(1);
		//double avg = acdValues.get(2);
		
		assertEquals(2, number1, 0.001);
		//assertEquals(1, max, 0.001);
		//assertEquals(1, avg, 0.001);
	}
	
	@Test
	public void acdTest2_multipleClasses() throws Exception {
		//String fileName = "acdTest2_multipleClasses.java";
		String projectPath = "/Users/fenggao/Documents/workspace/TestedProject";
		VisitProject vpro = new VisitProject(projectPath);
		//CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + fileName));
		//vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vpro.returnMetrics().getAggregatedList();
		List<Double> acdValues = metrics.get("ACD");
		double total = acdValues.get(0);
		double max = acdValues.get(1);
		double avg = acdValues.get(2);
		
		assertEquals(6, total, 0.001);
		assertEquals(2, max, 0.001);
		//assertEquals(1.2, avg, 0.001);
	}
}
