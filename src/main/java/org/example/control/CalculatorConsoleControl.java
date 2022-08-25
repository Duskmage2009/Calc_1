package org.example.control;

import org.example.db.SessionFactoryManager;
import org.example.entity.CalculationsHistory;
import org.example.entity.Calculator;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class CalculatorConsoleControl {

    Calculator calculator;
    SessionFactoryManager sessionFactoryManager;

    String expression;
    double result;

    public CalculatorConsoleControl() {

        calculator = new Calculator();

        sessionFactoryManager = SessionFactoryManager.getSessionFactoryManager();

        view();
    }

    public void view() {
        System.out.println("Please put digit number 1 if you want get you want to calculate values");
        System.out.println("Please put digit number 2 if you want to get date from the database");
        System.out.println("Please put digit number 3 if you want to search results from database");

        Scanner scanner = new Scanner(System.in);
        int value = Integer.parseInt(scanner.nextLine());
        if (value != 1 && value != 2 && value != 3) {
            System.out.println("Please enter correct digits");
            view();
        } else {
            try {
                switch (value) {
                    case 1:
                        System.out.println("Please enter your expression");
                        expression = scanner.nextLine();
                        // delete white space and other non - visible characters
                        expression = expression.replaceAll("\\s+","");
                        result = calculator.decide(expression);

                        System.out.println(result);

                        saveExpression(expression, result);

                        break;
                    case 2:
                        System.out.println("Choose the expression");

                        List<CalculationsHistory> calculationsHistoryList = getHistory();


                        for (int i = 0; i < calculationsHistoryList.size(); i++) {
                            System.out.println((i + 1) + " " + calculationsHistoryList.get(i));
                        }

                        scanner.reset();
                        System.out.println("Enter the number of calculation witch you want to edit");
                        int num = Integer.parseInt(scanner.nextLine());

                        while (num > calculationsHistoryList.size()) {
                            System.out.println("The number is incorrect, try again");
                            num = Integer.parseInt(scanner.nextLine());
                        }

                        System.out.println("Enter expression: ");
                        // delete white space and other non - visible characters
                        expression = scanner.nextLine();
                        expression = expression.replaceAll("\\s+","");

                        result = calculator.decide(expression);

                        System.out.println(result);

                        updateExpression(calculationsHistoryList.get(num - 1), expression, result);

                        System.out.println("Calculation: " + calculationsHistoryList.get(num - 1) + " is updated!");

                        break;

                    case 3:
                        System.out.println("Input number: ");
                        double number = Integer.parseInt(scanner.nextLine());
                        System.out.println("Choose search mode: ");
                        System.out.println("1. >");
                        System.out.println("2. =");
                        System.out.println("3. <");

                        int mode = Integer.parseInt(scanner.nextLine());;

                        while (mode > 3 || mode < 1) {
                            System.out.println("The number is incorrect, try again");
                            mode = Integer.parseInt(scanner.nextLine());
                        }
                        search(number, mode);

                        break;

                }
            } finally {
                sessionFactoryManager.getSession().close();
            }
        }
    }

    private void search(double num, int mode) {

        List<CalculationsHistory> calculationsHistoryList;

        Session session = sessionFactoryManager.getSession().getCurrentSession();

        switch (mode) {
            case 1:

                session.beginTransaction();

                calculationsHistoryList = session.createQuery("from CalculationsHistory s where s.result > '" + num + "'").list();

                session.getTransaction().commit();

                if (calculationsHistoryList.size() == 0) {
                    System.out.println("There are no results");
                    break;
                }

                for (CalculationsHistory history : calculationsHistoryList) {
                    System.out.println(history);
                }

                break;

            case 2:

                session.beginTransaction();

                calculationsHistoryList = session.createQuery("from CalculationsHistory s where s.result = '" + num + "'").list();

                session.getTransaction().commit();

                if (calculationsHistoryList.size() == 0) {
                    System.out.println("There are no results");
                    break;
                }

                for (CalculationsHistory history : calculationsHistoryList) {
                    System.out.println(history);
                }
                break;

            case 3:

                session.beginTransaction();

                calculationsHistoryList = session.createQuery("from CalculationsHistory s where s.result < '" + num + "'").list();

                session.getTransaction().commit();

                if (calculationsHistoryList.size() == 0) {
                    System.out.println("There are no results");
                    break;
                }

                for (CalculationsHistory history : calculationsHistoryList) {
                    System.out.println(history);
                }

                break;
        }

    }

    private void saveExpression(String expression, double result) {

        CalculationsHistory calculationsHistory = new CalculationsHistory(expression, result);

        Session session = sessionFactoryManager.getSession().getCurrentSession();

        session.beginTransaction();

        session.save(calculationsHistory);

        session.getTransaction().commit();
    }

    private void updateExpression(CalculationsHistory calculationsHistory, String expression, double result) {

        Session session = sessionFactoryManager.getSession().getCurrentSession();

        session.beginTransaction();

        session.update(calculationsHistory);

        calculationsHistory.setExpression(expression);

        calculationsHistory.setResult(result);

        session.getTransaction().commit();
    }

    private List<CalculationsHistory> getHistory() {

        List<CalculationsHistory> buffCalculationsHistoryList;

        Session session = sessionFactoryManager.getSession().getCurrentSession();

        session.beginTransaction();

        buffCalculationsHistoryList = session.createQuery("from CalculationsHistory").list();

        session.getTransaction().commit();

        return buffCalculationsHistoryList;

    }

}
