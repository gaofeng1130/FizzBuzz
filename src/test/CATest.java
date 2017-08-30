package test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import visitors.VisitProject;

public class CATest 
{
	@Test
	public void caTest1_multiplePackages() throws Exception {
		//String fileName = "acdTest2_multipleClasses.java";
		String projectPath = "/Users/fenggao/Documents/workspace/TestedProject";
		VisitProject vpro = new VisitProject(projectPath);
		//CompilationUnit cu = JavaParser.parse(new File(AllTests.folder + fileName));
		//vc.visit(cu, 0);
		Map<String, List<Double>> metrics = vpro.returnMetrics().getAggregatedList();
		List<Double> acValues = metrics.get("CA");
		double total = acValues.get(0);
		double max = acValues.get(1);
		double avg = acValues.get(2);
		
		assertEquals(6, total, 0.001);
		assertEquals(3, max, 0.001);
		assertEquals(2, avg, 0.001);
		//a1 ca = 1; a2 ca = 2; a3 ca = 3;
	}
}
