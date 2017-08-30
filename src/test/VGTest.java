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

public class VGTest 
{
	@Test
	public void vgTest1_simpleTest() throws ParseException, IOException {
		String fileName = "vgTest1_simpleTest.java";
		VisitClasses vc = new VisitClasses(fileName);
		CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + fileName));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getAggregatedList();
		List<Double> vgValues = metrics.get("VG");
		double total = vgValues.get(0);
		double max = vgValues.get(1);
		double avg = vgValues.get(2);
		
		assertEquals(21, total, 0.001);
		assertEquals(7, max, 0.001);
		assertEquals(4.2, avg, 0.001);
	}
	
	@Test
	public void vgTest2_multipleClasses() throws ParseException, IOException {
		String fileName = "vgTest2_multipleClasses.java";
		VisitClasses vc = new VisitClasses(fileName);
		CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + fileName));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getAggregatedList();
		List<Double> vgValues = metrics.get("VG");
		double total = vgValues.get(0);
		double max = vgValues.get(1);
		double avg = vgValues.get(2);
		
		assertEquals(21, total, 0.001);
		assertEquals(7, max, 0.001);
		assertEquals(4.2, avg, 0.001);
	}
}
