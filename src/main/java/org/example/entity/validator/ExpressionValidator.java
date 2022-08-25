package org.example.entity.validator;

import org.example.entity.validator.exceptions.IllegalSymbolsInExpressionException;
import org.example.entity.validator.exceptions.IncorrectExpressionException;
import utils.ExpCharacterTypes;

import java.util.ArrayList;

public class ExpressionValidator {

    private ArrayList<ExpCharacter> expCharacters = new ArrayList<>();

    private int index = 0;

    private int dotsInNumCounter = 0;

    private String expression;

    private int rightBracketsCounter = 0;
    private int leftBracketsCounter = 0;

    private int checkForMathSymbols = 0;

    public boolean validate(String expression) {

        this.expression = expression;

        char[] chars = expression.toCharArray();

        if (!init(chars)) {
            throw new IncorrectExpressionException("incorrect symbol: [" + expression.substring(index, index + 1) + "] in expression: " + expression);
        }

        return validateExpression(expCharacters);
    }


    private boolean validateExpression(ArrayList<ExpCharacter> expCharacters) {

        for (int i = 0; i < expCharacters.size(); i++) {
            if (expCharacters.get(i + 1).getType() != -1) {

                if (expCharacters.get(i).getType() == 4) {
                    if (!isNumberCorrect()) {
                        throw new IncorrectExpressionException("second dot: [" + expCharacters.get(i).getC() + "] in expression: " + expression);
                    }
                } else if (expCharacters.get(i).getType() != 0 && expCharacters.get(i).getType() != 4){
                    dotsInNumCounter = 0;
                }

                if (!checkByNextType(expCharacters.get(i).getAfterCharTypes(), expCharacters.get(i + 1).getType())) {
                    throw new IllegalSymbolsInExpressionException("incorrect symbol: [" + expCharacters.get(i).getC() + "] in expression: " + expression);
                }
            } else {
                return true;
            }
        }

        return true;
    }

    private boolean isNumberCorrect() {

        dotsInNumCounter++;

        return dotsInNumCounter <= 1;
    }

    private boolean checkByNextType(int[] expCharacterAfterCharTypes, int afterExpCharacterType) {

        for (int expCharacterAfterCharType : expCharacterAfterCharTypes) {
            if (expCharacterAfterCharType == afterExpCharacterType) {
                return true;
            }
        }

        return false;
    }

    private boolean init(char[] chars) {
        for (char c : chars) {
            ExpCharacter expCharacter = new ExpCharacter(c);

            if (indicateCharType(expCharacter) != -1) {
                expCharacters.add(expCharacter);
            } else {
                return false;
            }
            index++;
        }

        if (checkForMathSymbols < 1) {
            throw new IncorrectExpressionException("at least one math symbol (+ - * /) must be in expression: " + expression);
        }

        if (rightBracketsCounter - leftBracketsCounter != 0) {
            throw new IncorrectExpressionException("brackets are incorrect in expression: " + expression);
        }

        if (expCharacters.get(expCharacters.size() - 1).getType() != 0 && expCharacters.get(expCharacters.size() - 1).getType() != 2) {
            throw new IncorrectExpressionException("incorrect last symbol ["+ expCharacters.get(expCharacters.size() - 1).getC() + "] in expression: " + expression);
        }

        index = 0;

        ExpCharacter lastExpCharacter = new ExpCharacter('L');
        lastExpCharacter.setType(-1);
        expCharacters.add(lastExpCharacter);

        return true;
    }

    private int indicateCharType(ExpCharacter expCharacter) {

        char c = expCharacter.getC();

        if (Character.getType(c) == 9) {
            expCharacter.setType(0);
            expCharacter.setAfterCharTypes(ExpCharacterTypes.afterNum);
            return 0;
        }

        if (c == '+' || c == '-' || c == '*' || c == '/') {
            expCharacter.setType(1);
            expCharacter.setAfterCharTypes(ExpCharacterTypes.afterSymbols);
            checkForMathSymbols++;
            return 1;
        }

        if (c == ')') {
            expCharacter.setType(2);
            expCharacter.setAfterCharTypes(ExpCharacterTypes.afterRightBracket);
            rightBracketsCounter++;
            return 2;
        }

        if (c == '(') {
            expCharacter.setType(3);
            expCharacter.setAfterCharTypes(ExpCharacterTypes.afterLeftBracket);
            leftBracketsCounter++;
            return 3;
        }


        if (c == '.') {
            expCharacter.setType(4);
            expCharacter.setAfterCharTypes(ExpCharacterTypes.afterDot);
            return 4;
        }

        return -1;
    }
}
