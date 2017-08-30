package visitors;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

public class VisitPackage {

	// Store Metrics
	StoreMetrics packageMetrics;

	// file list
	File[] files;

	// import part
	List<CompilationUnit> cus;

	// LMC
	int not = 0;

	public VisitPackage(String folderName) throws Exception {
		packageMetrics = new StoreMetrics(folderName);
		files = getFiles(folderName);
		cus = new ArrayList<CompilationUnit>();
		// LMC
		not = 0;
	}

	public List<CompilationUnit> getCUList() {
		return cus;
	}

	private File[] getFiles(String folderName) throws Exception {

		File dir = new File(folderName);

		if (dir.isDirectory()) {

			FilenameFilter javaFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					String lowercaseName = name.toLowerCase();
					if (lowercaseName.endsWith(".java")) {
						return true;
					} else {
						return false;
					}
				}
			};

			File[] files = dir.listFiles(javaFilter);
			return files;

		} else {
			throw new Exception("not a directory!");
		}
	}

	public void visitFile(File f) {
		CompilationUnit cu = null;
		try {
			cu = JavaParser.parse(f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// calculate class level metrics
		VisitClasses cv = new VisitClasses(f.getName());
		// integer value passed is nested level
		// System.out.println(f.getPath());
		cv.visit(cu, 0);
		
//		System.err.println(cu.toString());
//		System.err.println("__________________________________________________");

		// report: file-level
//		cv.returnMetrics().printMetrics(4);
//		System.out.println("");

		// aggregate class level metrics
		packageMetrics.aggregate(cv.returnMetrics());

		// calculate file level metrics

		NumClassInterfaceVisitor nociVisitor = new NumClassInterfaceVisitor();
		nociVisitor.visit(cu, 0);

		// number of classes, NOT
		// LMC
		// packageMetrics.addMetricValue("NOT", nociVisitor.getNumClasses());
		not += nociVisitor.getNumClasses();

		// number of interfaces, NOI
		packageMetrics.addMetricValue("NOI", nociVisitor.getNumInterfaces());

		// number of anonymous type declaration, ACD
		packageMetrics.addMetricValue("ACD", nociVisitor.getNumAnonymousTypeDec());

		// total lines of code, TLOC
		TotalLinesVisitor tlocVis = new TotalLinesVisitor();
		tlocVis.countLines(cu);
		packageMetrics.addMetricValue("TLOC", tlocVis.getNumCodeLine());

	}

	public StoreMetrics returnMetrics() {

		// calculate package level metrics

		// number of files in the package, NOF
		packageMetrics.addMetricValue("NOCU", files.length);

		for (File file : files) {
			visitFile(file);
			CompilationUnit cu = null;
			try {
				cu = JavaParser.parse(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			cus.add(cu);
		}
		
		// LMC
		packageMetrics.addMetricValue("NOT", not);

		// return all the metrics calculated and aggregated
		return packageMetrics;
	}

	private class TotalLinesVisitor extends VoidVisitorAdapter<Integer> {

		int numCodeLine = 0;

		public void countLines(CompilationUnit n) {
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

	private class NumClassInterfaceVisitor extends VoidVisitorAdapter<Integer> {
		int numClasses;
		int numInterfaces;
		int numAnonymousTypeDec;

		public NumClassInterfaceVisitor() {
			numClasses = 0;
			numInterfaces = 0;
			numAnonymousTypeDec = 0;
		}

		@Override
		public void visit(ClassOrInterfaceDeclaration n, Integer arg) {
			if (n.isInterface())
				numInterfaces++;
			else
				numClasses++;
			super.visit(n, arg);
		}

		@Override
		public void visit(ObjectCreationExpr n, Integer arg) {
			if (n.getAnonymousClassBody() != null)
				numAnonymousTypeDec++;
			super.visit(n, arg);
		}

		public int getNumClasses() {
			return numClasses;
		}

		public int getNumInterfaces() {
			return numInterfaces;
		}

		public int getNumAnonymousTypeDec() {
			return numAnonymousTypeDec;
		}
	}

}