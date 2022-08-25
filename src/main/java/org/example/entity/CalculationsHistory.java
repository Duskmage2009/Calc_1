package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "calculations_history")
public class CalculationsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "expression")
    private String expression;

    @Column(name = "result")
    private double result;

    public CalculationsHistory() {

    }

    public CalculationsHistory(String expression, double result) {
        this.expression = expression;
        this.result = result;
    }

    @Override
    public String toString() {
        return "expression= ['" + expression + '\'' +
                ", result='" + result + '\'' +
                ']';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
