package test;

import static org.junit.Assert.assertEquals;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import visitors.VisitClasses;
import visitors.VisitPackage;

public class NORMTest 
{
	@Test
	public void normTest1_simpleTest() throws Exception {
		String packagePath = "/Users/fenggao/Documents/workspace/TestedProject/src/a2/";
		String fileName = "normTest1_simpleTest.java";
		VisitClasses vc = new VisitClasses(fileName);
		CompilationUnit cu = JavaParser.parse(new File(packagePath + fileName));
		vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vc.returnMetrics().getMetricsList();
		List<Double> normValues = metrics.get("NORM");
		double num1 = normValues.get(0);
		double num2 = normValues.get(1);
		double num3 = normValues.get(2);
		
		assertEquals(2, num1, 0.001);
		assertEquals(1, num2, 0.001);
		assertEquals(1, num3, 0.001);
	}
}
