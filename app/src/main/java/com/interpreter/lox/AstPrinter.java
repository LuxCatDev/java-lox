package com.interpreter.lox;

import com.interpreter.lox.Expr.Binary;
import com.interpreter.lox.Expr.Grouping;
import com.interpreter.lox.Expr.Literal;
import com.interpreter.lox.Expr.Unary;

class AstPrinter implements Expr.Visitor<String> {

	public static void main(String[] args) {
		Expr expression = new Binary(new Unary(new Token(TokenType.MINUS, "-", null, 1), new Literal(123)),
				new Token(TokenType.STAR, "*", 1, 0), new Grouping(new Literal(45.67)));

		System.out.println(new AstPrinter().print(expression));
	}

	String print(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public String visitBinaryExpr(Binary expr) {
		return parenthesize(expr.operator.lexeme, expr.left, expr.right);
	}

	@Override
	public String visitGroupingExpr(Grouping expr) {
		return parenthesize("group", expr.expression);
	}

	@Override
	public String visitLiteralExpr(Literal expr) {
		if (expr.value == null)
			return "nil";
		return expr.value.toString();
	}

	@Override
	public String visitUnaryExpr(Unary expr) {
		return parenthesize(expr.operator.lexeme, expr.right);
	}

	private String parenthesize(String name, Expr... exprs) {
		StringBuilder builder = new StringBuilder();

		builder.append("(").append(name);
		for (Expr expr : exprs) {
			builder.append(" ");
			builder.append(expr.accept(this));
		}

		builder.append(")");

		return builder.toString();
	}
}
