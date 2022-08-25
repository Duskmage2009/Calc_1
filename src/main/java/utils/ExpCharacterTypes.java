package utils;

/*
Types:

0 - numbers: 0,1,2,3,4,5,6,7,8,9
1 - symbols: + - * /
2 - right bracket: )
3 - left bracket: (
4 - dot: .

 */

public class ExpCharacterTypes {

    public static int[] afterNum = {0,1,2,4};

    public static int[] afterSymbols = {0,3};

    public static int[] afterRightBracket = {1,2}; // )

    public static int[] afterLeftBracket = {0,1,3}; // (

    public static int[] afterDot = {0};
}
