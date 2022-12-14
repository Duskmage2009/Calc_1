package org.example.entity;

import org.example.entity.validator.ExpressionValidator;

import java.util.Stack;

/**
 * Hello world!
 */
public class Calculator {

    public double decide(String expression) {
        ExpressionValidator validator = new ExpressionValidator();
        if (validator.validate(expression)) {
            String prepared = preparingExpression(expression);
            String rpn = expressionToRPN(prepared);
            return rpnToAnswer(rpn);
        }
        throw new ArithmeticException("");
    }

    private String preparingExpression(String expression) {
        String preparedExpression = new String();
        for (int token = 0; token < expression.length(); token++) {
            char symbol = expression.charAt(token);
            if (symbol == '-') {
                if (token == 0)
                    preparedExpression += '0';
                else if (expression.charAt(token - 1) == '(')
                    preparedExpression += '0';
            }
            preparedExpression += symbol;
        }
        return preparedExpression;
    }

    public static String expressionToRPN(String Expr) {
        String current = "";
        Stack<Character> stack = new Stack<>();

        int priority;
        for (int i = 0; i < Expr.length(); i++) {
            priority = getPriority(Expr.charAt(i));
            if (priority == 0) current += Expr.charAt(i);
            if (priority == 1) stack.push(Expr.charAt(i));

            if (priority > 1) {
                current += ' ';
                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority) current += stack.pop();
                    else break;
                }
                stack.push(Expr.charAt(i));
            }
            if (priority == -1) {
                current += ' ';
                while (getPriority(stack.peek()) != 1) current += stack.pop();
                stack.pop();
            }
        }
        while (!stack.empty()) current += stack.pop();
        return current;
    }

    private double rpnToAnswer(String rpn) {
        String operand = new String();
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') continue;

            if (getPriority(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) break;
                }

                stack.push(Double.parseDouble(operand));
                operand = new String();
            }
            if (getPriority(rpn.charAt(i)) > 1) {
                double a = stack.pop(), b = stack.pop();

                if (rpn.charAt(i) == '+') stack.push(b + a);
                if (rpn.charAt(i) == '-') stack.push(b - a);
                if (rpn.charAt(i) == '*') stack.push(b * a);
                if (rpn.charAt(i) == '/') stack.push(b / a);
            }
        }
        return stack.pop();
    }

    private static int getPriority(char token) {
        if (token == '*' || token == '/') return 3;
        if (token == '+' || token == '-') return 2;
        if (token == '(') return 1;
        if (token == ')') return -1;
        else return 0;
    }
}