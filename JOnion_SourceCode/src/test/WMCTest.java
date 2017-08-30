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

public class WMCTest 
{
	@Test
	public void wmcTest1_simpleTest() throws ParseException, IOException {
		String fileName = "vgTest1_simpleTest.java";
		VisitClasses vc = new VisitClasses(fileName);
		CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + fileName));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getMetricsList();
		List<Double> wmcValues = metrics.get("WMC");
		double total = wmcValues.get(0);
		//double max = wmcValues.get(1);
		//double avg = wmcValues.get(2);
		
		assertEquals(21, total, 0.001);
		//assertEquals(7, max, 0.001);
		//assertEquals(4.2, avg, 0.001);
	}
	
	@Test
	public void wmcTest2_multipleClasses() throws ParseException, IOException {
		String fileName = "vgTest2_multipleClasses.java";
		VisitClasses vc = new VisitClasses(fileName);
		CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + fileName));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getMetricsList();
		List<Double> wmcValues = metrics.get("WMC");
		double num1 = wmcValues.get(0);
		double num2 = wmcValues.get(1);
		double num3 = wmcValues.get(2);
		
		assertEquals(2, num1, 0.001);
		assertEquals(10, num2, 0.001);
		assertEquals(9, num3, 0.001);
	}
}
