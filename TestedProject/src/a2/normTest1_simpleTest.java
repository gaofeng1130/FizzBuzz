package a2;

import japa.parser.ast.expr.ConditionalExpr;

import java.util.ArrayList;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import a3.*;

public class normTest1_simpleTest extends VoidVisitorAdapter
{
	@Override
    public void visit(ConditionalExpr n, Object arg) {
        n.getCondition().accept(this, arg);
        n.getThenExpr().accept(this, arg);
        n.getElseExpr().accept(this, arg);
    }
	
	@Override
    public void visit(MemberValuePair n, Object arg) {
        n.getValue().accept(this, arg);
    }
	
	public class tgg extends VoidVisitorAdapter
	{
		@Override
		public void visit(MemberValuePair n, Object arg) {
	        n.getValue().accept(this, arg);
	    }
	}
}

class safrsdf extends VoidVisitorAdapter
{
	@Override
	public void visit(MemberValuePair n, Object arg) {
        n.getValue().accept(this, arg);
    }
}
