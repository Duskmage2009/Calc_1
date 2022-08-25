package org.example.entity.validator;

import org.example.entity.validator.exceptions.IllegalSymbolsInExpressionException;
import org.example.entity.validator.exceptions.IncorrectExpressionException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExpressionValidatorTest {

    ExpressionValidator expressionValidator;

    @Before
    public void init() {
        expressionValidator = new ExpressionValidator();
    }

    @Test
    public void correctExpressionWithOnlySymbolsReturnsTrue() {
        assertTrue(expressionValidator.validate("3+2.12+5"));
    }

    @Test
    public void correctExpressionWithMinusMustReturnTrue() {
        assertTrue(expressionValidator.validate("-(3+2.12)+5"));
    }

    @Test (expected = IllegalSymbolsInExpressionException.class)
    public void correctExpressionWithMathSymbolAndMinusMustReturnFalse() {
        assertFalse(expressionValidator.validate("3+-2.12+5"));
    }

    @Test (expected = IllegalSymbolsInExpressionException.class)
    public void expressionWithMathSymbolsAndMinusMustReturnFalse() {
        assertFalse(expressionValidator.validate("3+--2.12+5"));
    }

    @Test (expected = IncorrectExpressionException.class)
    public void expressionWithLettersMustReturnFalse() {
        assertFalse(expressionValidator.validate("3+2.12фйцs"));
    }

    @Test (expected = IncorrectExpressionException.class)
    public void expressionWithTheNumberWithMoreThanOneDotMustReturnFalse() {
        assertFalse(expressionValidator.validate("4*24+2.24.1+2"));
    }

    @Test(expected = IncorrectExpressionException.class)
    public void expressionWithUncorrectedCountOfBracketsMustReturnTrue() {
        assertFalse(expressionValidator.validate("-(3+2.12+5"));
    }

}