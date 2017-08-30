package test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import visitors.VisitProject;

public class ITest
{
	@Test
	public void iTest1_multiplePackages() throws Exception {
		//String fileName = "acdTest2_multipleClasses.java";
		String projectPath = "/Users/fenggao/Documents/workspace/TestedProject";
		VisitProject vpro = new VisitProject(projectPath);
		//CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + fileName));
		//vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vpro.returnMetrics().getAggregatedList();
		List<Double> acValues = metrics.get("I");
		double total = acValues.get(0);
		double max = acValues.get(1);
		double avg = acValues.get(2);
		
		assertEquals(1.25, total, 0.001);
		assertEquals(0.666666, max, 0.001);
		assertEquals(0.41666, avg, 0.001);
		//a1 ce = 2; a2 ce = 1; a3 ce = 1;
		//a1 ca = 1; a2 ca = 2; a3 ca = 3;
		//a1 i = 0.6666; a2 i = 0.3333; a3 i = 0.25;
	}
}
