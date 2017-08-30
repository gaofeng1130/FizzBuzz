package test;

import static org.junit.Assert.assertEquals;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import visitors.VisitPackage;

public class NOITest 
{
	@Test
	public void noiTest1_simpleTest() throws Exception {
		String projectPath = "/Users/fenggao/Documents/workspace/TestedProject/src/";
		VisitPackage vp = new VisitPackage(projectPath + "a1/");
		//CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + fileName));
		//vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vp.returnMetrics().getMetricsList();
		List<Double> noiValues = metrics.get("NOI");
		double num1 = noiValues.get(0);
		double num2 = noiValues.get(1);
		//double avg = noiValues.get(2);
		
		assertEquals(0, num1, 0.001);
		assertEquals(4, num2, 0.001);
		//assertEquals(1, avg, 0.001);
	}
}
