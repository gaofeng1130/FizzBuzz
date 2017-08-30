package visitors;

import java.lang.reflect.Modifier;
import java.util.List;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

public class VisitClasses extends VoidVisitorAdapter<Integer> {

	// Store Metrics
	StoreMetrics fileMetrics;

	public VisitClasses(String fileName) {
		fileMetrics = new StoreMetrics(fileName);
	}

	public StoreMetrics returnMetrics() {
		return fileMetrics;
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Integer nestedLevel) {

		// visit methods inside the class and calculate metrics for method level
		VisitMethods visMethods = new VisitMethods(n.getName());
		visMethods.visit(n, 0);

		// aggregate method level metrics
		fileMetrics.aggregate(visMethods.returnMetrics());

		// class lines of code, CLOC
		ClassLinesVisitor clcVisitor = new ClassLinesVisitor();
		clcVisitor.countLines(n, nestedLevel);
		fileMetrics.addMetricValue("CLOC", clcVisitor.getNumCodeLine());

		// calculating number of fields and methods
		FieldAndMethodVisitor fieldMethodVis = new FieldAndMethodVisitor();
		fieldMethodVis.visit(n, 0);

		// number of fields, NOF
		fileMetrics.addMetricValue("NOF", fieldMethodVis.numFields);

		// number of static fields, NSF
		fileMetrics.addMetricValue("NSF", fieldMethodVis.numStaticFields);

		// number of methods, NOM
		fileMetrics.addMetricValue("NOM", fieldMethodVis.numMethods);

		// number of static methods, NSM
		fileMetrics.addMetricValue("NSM", fieldMethodVis.numStaticMethods);

		// number of overriden methods, NORM
		fileMetrics.addMetricValue("NORM", fieldMethodVis.numOverridenMethods);

		// sum of VG, WMC
		// double wmc = fileMetrics.getAggregatedList().get("VG").get(0);
		List<Double> vgList = visMethods.returnMetrics().getMetricsList().get("VG");
		double wmc = 0.0;
		if (vgList != null) {
			for (double vg : vgList) {
				wmc = vg + wmc;
			}
		}
		fileMetrics.addMetricValue("WMC", wmc);

		// calculate metrics for inner class
		super.visit(n, nestedLevel + 1);
	}

	private class ClassLinesVisitor extends VoidVisitorAdapter<Integer> {

		int numCodeLine = 0;

		public void countLines(ClassOrInterfaceDeclaration n, int nestedLevel) {
			String[] lines = n.toString().split("\n");
			for (int i = 0; i < lines.length; i++) {
				String line = lines[i].trim();
				if (!line.trim().isEmpty()) {
					numCodeLine++;
				}
				line = line.replaceAll("\".*\"", "\"-\"");
				boolean openedBlock = line.endsWith("{");
				String[] keywords = { "do", "if", "else", "for", "while" };
				int splitIndex = 0;
				while (splitIndex >= 0) {
					splitIndex = -1;
					for (String keyword : keywords) {
						if (line.indexOf(keyword) >= 0
								&& !Character.isLetter(line.substring(splitIndex + keyword.length()).charAt(0))) {
							splitIndex = line.indexOf(keyword);
							line = line.substring(splitIndex + keyword.length());
							break;
						}
					}
					if (splitIndex >= 0) {
						if (!openedBlock) {
							numCodeLine++;
						} else {
							openedBlock = false;
						}
					}
				}
			}
		}

		public int getNumCodeLine() {
			return numCodeLine;
		}
	}

	private class FieldAndMethodVisitor extends VoidVisitorAdapter<Integer> {
		int numFields;
		int numStaticFields;
		int numMethods;
		int numStaticMethods;
		int numOverridenMethods;

		public FieldAndMethodVisitor() {
			numFields = 0;
			numStaticFields = 0;
			numMethods = 0;
			numStaticMethods = 0;
			numOverridenMethods = 0;
		}

		// prevent processing nested metrics
		@Override
		public void visit(ClassOrInterfaceDeclaration n, Integer nestedLevel) {
			if (nestedLevel == 0) {
				super.visit(n, nestedLevel + 1);
			}
		}

		@Override
		public void visit(FieldDeclaration n, Integer arg) {
			numFields++;
			if (Modifier.isStatic((n.getModifiers()))) {
				numStaticFields++;
			}
			super.visit(n, arg);
		}

		@Override
		public void visit(MethodDeclaration n, Integer arg) {

			numMethods++;

			if (Modifier.isStatic((n.getModifiers()))) {
				numStaticMethods++;
			}

			List<AnnotationExpr> annots = n.getAnnotations();

			if (annots != null) {
				for (AnnotationExpr annotExpr : annots) {
					if (annotExpr.getName().getName().equals("Override"))
						numOverridenMethods++;
				}
			}

			super.visit(n, arg);
		}
	}
}