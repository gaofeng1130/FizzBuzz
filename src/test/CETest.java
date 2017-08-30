package test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import visitors.VisitProject;

public class CETest 
{
	@Test
	public void ceTest1_multiplePackages() throws Exception {
		//String fileName = "acdTest2_multipleClasses.java";
		String projectPath = "/Users/fenggao/Documents/workspace/TestedProject";
		VisitProject vpro = new VisitProject(projectPath);
		//CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + fileName));
		//vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vpro.returnMetrics().getAggregatedList();
		List<Double> acValues = metrics.get("CE");
		double total = acValues.get(0);
		double max = acValues.get(1);
		double avg = acValues.get(2);
		
		assertEquals(4, total, 0.001);
		assertEquals(2, max, 0.001);
		assertEquals(1.33333, avg, 0.001);
		//a1 ce = 2; a2 ce = 1; a3 ce = 1;
	}
}
