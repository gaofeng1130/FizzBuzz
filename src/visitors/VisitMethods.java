package visitors;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.LabeledStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.visitor.GenericVisitorAdapter;
import japa.parser.ast.visitor.VoidVisitorAdapter;

public class VisitMethods extends VoidVisitorAdapter<Integer> {

	String className;

	StoreMetrics classMetrics;

	public VisitMethods(String clsName) {
		className = clsName;
		classMetrics = new StoreMetrics(clsName);
	}

	public StoreMetrics returnMetrics() {
		// TODO maybe we need to check if the storeMetrics is not null a
		// checkNull method needed
		// classMetrics.printMetrics();
		// System.out.println(classMetrics.owner);
		return classMetrics;
	}

	// prevent processing nested metrics
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Integer nestedLevel) {
		if (nestedLevel == 0) {
			super.visit(n, nestedLevel + 1);
		}
	}

	@Override
	public void visit(MethodDeclaration n, Integer nestedLevel) {

		// >>>>>>>>>>>> calculation for methods of class <<<<<<<<<<<<<<

		// method lines of code, MLOC
		LineCounterVisitor lcVisitor = new LineCounterVisitor();
		if (n.getBody() != null)
			lcVisitor.countLines(n.getBody(), nestedLevel);
		// get the calculated metrics
		classMetrics.addMetricValue("MLOC", lcVisitor.getNumCodeLine());

		// number of method calls, FOUT
		FanOutVisitor foutVisitor = new FanOutVisitor();
		foutVisitor.visit(n, nestedLevel);
		classMetrics.addMetricValue("FOUT", foutVisitor.getFanOut());

		// nested block depth, NBD
		NBDVisitor nbdVisitor = new NBDVisitor();
		// should give it the inner block statement of method
		int nbd = 0;
		if (n.getBody() != null)
			nbd = nbdVisitor.visit(n.getBody(), null);
		classMetrics.addMetricValue("NBD", nbd);

		// McCabe cyclomatic complexity, VG
		MCVisitor vgVisitor = new MCVisitor();
		int vg = 0;
		if (n.getBody() != null) {
			vgVisitor.visit(n.getBody(), 0);
			vg = vgVisitor.returnMCComplexity();
		}
		classMetrics.addMetricValue("VG", vg);

		// number of parameters, PAR
		int par = 0;
		if (n.getParameters() != null)
			par = n.getParameters().size();
		classMetrics.addMetricValue("PAR", par);

		super.visit(n, nestedLevel);
	}

	// treat constructor as another method of class
	@Override
	public void visit(ConstructorDeclaration n, Integer nestedLevel) {

		// >>>>>>>>>>>> calculation for methods of class <<<<<<<<<<<<<<

		// method lines of code, MLOC
		LineCounterVisitor lcVisitor = new LineCounterVisitor();
		if (n.getBlock() != null)
			lcVisitor.countLines(n.getBlock(), nestedLevel);
		// get the calculated metrics
		classMetrics.addMetricValue("MLOC", lcVisitor.getNumCodeLine());

		// number of method calls, FOUT
		FanOutVisitor foutVisitor = new FanOutVisitor();
		foutVisitor.visit(n, nestedLevel);
		classMetrics.addMetricValue("FOUT", foutVisitor.getFanOut());

		// nested block depth, NBD
		NBDVisitor nbdVisitor = new NBDVisitor();
		// should give it the inner block statement of method
		int nbd = 0;
		if (n.getBlock() != null)
			nbd = nbdVisitor.visit(n.getBlock(), null);
		classMetrics.addMetricValue("NBD", nbd);

		// McCabe cyclomatic complexity, VG
		MCVisitor vgVisitor = new MCVisitor();
		int vg = 0;
		if (n.getBlock() != null) {
			vgVisitor.visit(n.getBlock(), 0);
			vg = vgVisitor.returnMCComplexity();
		}
		classMetrics.addMetricValue("VG", vg);

		// number of parameters, PAR
		int par = 0;
		if (n.getParameters() != null)
			par = n.getParameters().size();
		classMetrics.addMetricValue("PAR", par);

		super.visit(n, nestedLevel);
	}

	private class LineCounterVisitor extends VoidVisitorAdapter<Integer> {

		int numCodeLine = 0;

		public void countLines(BlockStmt body, int nestedLevel) {
			// when if or for does not have curley brackets, it is counted as
			// one line !!!
			String[] lines = body.toString().split("\n");
			for (int i = 0; i < lines.length; i++) {
				String line = lines[i].trim();
				if (i == 0 && line.trim().equals("{"))
					continue;
				if (i == lines.length - 1 && line.trim().equals("}"))
					continue;
				if (!line.trim().isEmpty()) {
					numCodeLine++;
				}
				// it is an ad-hoc solution to remove strings with
				line = line.replaceAll("\".*\"", "\"-\"");
				boolean openedBlock = line.endsWith("{");
				String[] keywords = { "do", "if", "else", "for", "while" };
				int splitIndex = 0;
				while (splitIndex >= 0) {
					splitIndex = -1;
					for (String keyword : keywords) {
						// first check if the keyword is found in the string and
						// then check that it is not part of another word
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

	private class FanOutVisitor extends VoidVisitorAdapter<Integer> {

		int fout;

		public FanOutVisitor() {
			fout = 0;
		}

		@Override
		public void visit(MethodCallExpr n, Integer arg) {
			fout++;
			super.visit(n, arg);
		}

		public int getFanOut() {
			return fout;
		}
	}

	private class NBDVisitor extends GenericVisitorAdapter<Integer, Integer> {

		public Integer visitStatement(Statement n, Integer arg) {
			if (n == null) {
				return 0;
			} else if (n instanceof BlockStmt) {
				BlockStmt bs = (BlockStmt) n;
				return this.visit(bs, arg) + 1;
			} else if (n instanceof DoStmt) {
				DoStmt ds = (DoStmt) n;
				int dsDepth = this.visitStatement(ds.getBody(), arg);
				return dsDepth;
			} else if (n instanceof ForeachStmt) {
				ForeachStmt fes = (ForeachStmt) n;
				int fesDepth = this.visitStatement(fes.getBody(), arg);
				return fesDepth;
			} else if (n instanceof ForStmt) {
				ForStmt fs = (ForStmt) n;
				int fsDepth = this.visitStatement(fs.getBody(), arg);
				return fsDepth;
			} else if (n instanceof IfStmt) {
				IfStmt ifs = (IfStmt) n;
				int thenDepth = this.visitStatement(ifs.getThenStmt(), arg);
				int elseDepth = this.visitStatement(ifs.getElseStmt(), arg);
				return Math.max(thenDepth, elseDepth);
			} else if (n instanceof LabeledStmt) {
				// it is like label: command or label: {commands}
				LabeledStmt lbls = (LabeledStmt) n;
				int lblsDepth = this.visitStatement(lbls.getStmt(), arg);
				return lblsDepth;
			} else if (n instanceof SwitchStmt) {
				SwitchStmt sws = (SwitchStmt) n;
				return this.visit(sws, arg) + 1;
			} else if (n instanceof SynchronizedStmt) {
				SynchronizedStmt syns = (SynchronizedStmt) n;
				int synsDepth = this.visit(syns.getBlock(), arg);
				return synsDepth;
			} else if (n instanceof TryStmt) {
				TryStmt ts = (TryStmt) n;
				return this.visit(ts, arg);
			} else if (n instanceof WhileStmt) {
				WhileStmt ws = (WhileStmt) n;
				int wsDepth = this.visitStatement(ws.getBody(), arg);
				return wsDepth;
			} else {
				// assumed other cases are single depth statements
				return 1;
			}
		}

		@Override
		public Integer visit(BlockStmt n, Integer arg) {
			if (n == null)
				return 0;
			if (n.getStmts() == null) {
				return 1;
			}
			int maxDepth = 0;
			for (Statement stmt : n.getStmts()) {
				int stmtDepth = this.visitStatement(stmt, arg);
				if (stmtDepth > maxDepth)
					maxDepth = stmtDepth;
			}
			return maxDepth;
		}

		@Override
		public Integer visit(SwitchStmt n, Integer arg) {
			int maxDepth = 0;
			if (n.getEntries() != null) {
				for (SwitchEntryStmt ses : n.getEntries()) {
					if (ses.getStmts() != null) {
						for (Statement stmt : ses.getStmts()) {
							int stDepth = this.visitStatement(stmt, arg);
							if (stDepth > maxDepth)
								maxDepth = stDepth;
						}
					}
				}
			}
			return Math.max(maxDepth, 1);
		}

		@Override
		public Integer visit(TryStmt n, Integer arg) {
			// this class may need some more careful calculation for clause
			// depths ...
			int maxDepth = 0;
			int tryDepth = this.visit(n.getTryBlock(), arg);
			int fnlDepth = this.visit(n.getFinallyBlock(), arg);
			maxDepth = Math.max(tryDepth, fnlDepth);
			if (n.getCatchs() != null) {
				for (CatchClause cc : n.getCatchs()) {
					int ccDepth = this.visit(cc.getCatchBlock(), arg);
					if (ccDepth > maxDepth)
						maxDepth = ccDepth;
				}
			}
			return maxDepth + 1;
		}
	}
	
	private class MCVisitor extends VoidVisitorAdapter<Object> {

		int MCComplexity;

		public MCVisitor() {
			MCComplexity = 1;
		}

		private int returnMCComplexity() {
			return MCComplexity;
		}

		@Override
		public void visit(CatchClause n, Object arg) {

			MCComplexity++;
			super.visit(n, arg);
		}

		@Override
		public void visit(DoStmt n, Object arg) {

			inspectExpression(n.getCondition());
			MCComplexity++;
			super.visit(n, arg);
		}

		@Override
		public void visit(ExpressionStmt n, Object arg) {

			inspectExpression(n.getExpression());
			super.visit(n, arg);
		}

		@Override
		public void visit(ForStmt n, Object arg) {

			inspectExpression(n.getCompare());
			MCComplexity++;
			super.visit(n, arg);
		}

		@Override
		public void visit(IfStmt n, Object arg) {

			inspectExpression(n.getCondition());
			MCComplexity++;
			super.visit(n, arg);
		}

		@Override
		public void visit(SwitchStmt n, Object arg) {
			inspectExpression(n.getSelector());
			MCComplexity++;
			super.visit(n, arg);
		}

		@Override
		public void visit(WhileStmt n, Object arg) {

			inspectExpression(n.getCondition());
			MCComplexity++;
			super.visit(n, arg);
		}

		@Override
		public void visit(ConditionalExpr n, Object arg) {

			inspectExpression(n.getCondition());
			MCComplexity++;
			super.visit(n, arg);
		}

		/**
		 * Count occurrences of && and || (conditional and or) Fix for BUG
		 * 740253
		 * 
		 * @param ex
		 */
		private void inspectExpression(Expression ex) {
			if ((ex != null)) {
				char[] chars = ex.toString().toCharArray();

				for (int i = 0; i < chars.length - 1; i++) {
					char next = chars[i];
					if ((next == '&' || next == '|')) {
						if ((next == chars[i + 1])) {
							i++;
							MCComplexity++;
						} else {
							MCComplexity++;
						}

					}
				}
			}
		}
	}
}