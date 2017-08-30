package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import visitors.StoreMetrics;
import visitors.VisitPackage;

public class TLOCTest {

	@Test
	public void tlocTest1_test1() throws Exception {
		VisitPackage vp = new VisitPackage(AllTests.folder + "tlocTest");
		
		StoreMetrics packageMetrics = vp.returnMetrics();
		
		Map<String, List<Double>> met = packageMetrics.getMetricsList();
		Map<String, List<Double>> agg = packageMetrics.getAggregatedList();
		
		List<Double> tlocValues = met.get("TLOC");

		assertEquals(23, tlocValues.get(0), 0.001);
	}

}
