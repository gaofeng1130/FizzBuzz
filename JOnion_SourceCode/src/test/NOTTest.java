package test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import visitors.VisitPackage;

public class NOTTest 
{
	@Test
	public void noiTest1_simpleTest() throws Exception {
		String projectPath = "/Users/fenggao/Documents/workspace/TestedProject/src/";
		VisitPackage vp = new VisitPackage(projectPath + "a1/");
		//CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + fileName));
		//vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vp.returnMetrics().getMetricsList();
		List<Double> notValues = metrics.get("NOT");
		double num1 = notValues.get(0);
		double num2 = notValues.get(1);
		double num3 = notValues.get(2);
		
		assertEquals(1, num1, 0.001);
		assertEquals(0, num2, 0.001);
		assertEquals(6, num3, 0.001);
	}
}
