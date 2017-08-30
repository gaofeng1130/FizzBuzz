package visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreMetrics {

	static {
		// add metrics that need different aggregation method
		// key is metric's name and value is aggregation method
		HashMap<String, String> aggregateMethod = new HashMap<>();
		// add special cases here ...
	}

	// key is metric's name and value is list of calculated metrics
	HashMap<String, List<Double>> metricsList;

	// for aggregation of metrics
	HashMap<String, List<Double>> aggregatedList;

	// this string has the name of the owner of metrics which can be:
	// method name, class name, file name, package name
	String owner;

	public StoreMetrics(String ownerName) {
		metricsList = new HashMap<>();
		aggregatedList = new HashMap<>();
		owner = ownerName;
	}

	public void printMetrics() {
		System.out.println(" >>> Metrics List " + owner);
		for (Map.Entry<String, List<Double>> m : metricsList.entrySet()) {
			System.out.printf("%5s => [ ", m.getKey());
			boolean first = true;
			for (double d : m.getValue())
				if (first) {
					System.out.printf("%8.2f", d);
					first = false;
				} else {
					System.out.printf(" %8.2f", d);
				}
			System.out.printf(" ]\n");
		}
		System.out.println(" >>> Aggregated List " + owner);
		for (Map.Entry<String, List<Double>> m : aggregatedList.entrySet()) {
			System.out.printf("%5s => [ ", m.getKey());
			boolean first = true;
			for (double d : m.getValue())
				if (first) {
					System.out.printf("%8.2f", d);
					first = false;
				} else {
					System.out.printf(" %8.2f", d);
				}
			System.out.printf(" ]\n");
		}
	}
	
	public void printMetrics(int pad) {
		
		String prFloat = "%" + (pad + 3) + ".2f";
		
		System.out.println(" >>> Metrics List " + owner);
		for (Map.Entry<String, List<Double>> m : metricsList.entrySet()) {
			System.out.printf("%5s => [ ", m.getKey());
			boolean first = true;
			for (double d : m.getValue())
				if (first) {
					System.out.printf(prFloat, d);
					first = false;
				} else {
					System.out.printf(" "+prFloat, d);
				}
			System.out.printf(" ]\n");
		}
		System.out.println(" >>> Aggregated List " + owner);
		for (Map.Entry<String, List<Double>> m : aggregatedList.entrySet()) {
			System.out.printf("%5s => [ ", m.getKey());
			boolean first = true;
			for (double d : m.getValue())
				if (first) {
					System.out.printf(prFloat, d);
					first = false;
				} else {
					System.out.printf(" "+prFloat, d);
				}
			System.out.printf(" ]\n");
		}
	}

	public void addMetricValue(String name, double value) {
		// check if the list for the metric already exists
		if (metricsList.containsKey(name)) {
			// just add to list
			metricsList.get(name).add(value);
		} else {
			// create new list for metric
			ArrayList<Double> list = new ArrayList<>();
			list.add(value);
			metricsList.put(name, list);
		}
	}

	public void addMetricValueToAggratedList(String name, List<Double> values) {
		// check if the list for the metric already exists
		if (aggregatedList.containsKey(name)) {
			// just add to list
			List<Double> newList = new ArrayList<>();
			List<Double> olderList = aggregatedList.get(name);
			double olderTotal = olderList.get(0);
			double olderMax = olderList.get(1);
			// double olderAverage = olderList.get(2);
			double olderNum = olderList.get(3);

			newList.add(olderTotal + values.get(0));
			if (values.get(1) > olderMax) {
				newList.add(values.get(1));
			} else {
				newList.add(olderMax);
			}
			newList.add((olderTotal + values.get(0)) / (olderNum + values.get(3)));
			newList.add(olderNum + values.get(3));

			aggregatedList.put(name, newList);
		} else {
			aggregatedList.put(name, values);
		}
	}

	public HashMap<String, List<Double>> getMetricsList() {
		return metricsList;
	}

	public HashMap<String, List<Double>> getAggregatedList() {
		return aggregatedList;
	}

	public void aggregate(StoreMetrics subMetrics) {
		HashMap<String, List<Double>> metrics = subMetrics.getMetricsList();
		HashMap<String, List<Double>> aggregated = subMetrics.getAggregatedList();

		for (String key : metrics.keySet()) {
			List<Double> values = metrics.get(key);
			double total = 0;
			double max = 0;
			for (double value : values) {
				if (value > max) {
					max = value;
				}
				total = value + total;
			}
			double num = values.size();
			double average = total / num;
			List<Double> results = new ArrayList<>();
			results.add(total);
			results.add(max);
			results.add(average);
			results.add(num);
			this.addMetricValueToAggratedList(key, results);
		}
		for (String key : aggregated.keySet()) {
			List<Double> values = aggregated.get(key);
			// System.out.println("key " + key + values.get(0));
			this.addMetricValueToAggratedList(key, values);
		}
	}

}
