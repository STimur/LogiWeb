package com.tsystems.javaschool.timber.logiweb.view.util;

import com.tsystems.javaschool.timber.logiweb.view.exceptions.IntegerOutOfRangeException;

import java.util.regex.PatternSyntaxException;

/**
 * Created by tims on 2/29/2016.
 */
public class InputParser {
    public static int parseNumber(String input, int min, int max) throws IntegerOutOfRangeException {
        int num = 0;
        try {
            num = Integer.valueOf(input);
        } catch (NumberFormatException ex) {
            throw new IntegerOutOfRangeException(input);
        }
        if (num < min)
            throw new IntegerOutOfRangeException(input);
        if (num > max)
            throw new IntegerOutOfRangeException(input);
        return num;
    }

    public static String parsePlateNumber(String input) throws PatternSyntaxException {
        String plateNumberRegex = "[a-zA-Z]{2}[0-9]{5}";
        if (input.matches(plateNumberRegex))
            return input;
        throw new PatternSyntaxException(input, plateNumberRegex, -1);
    }

    public static String parseLettersOnlyString(String input) throws PatternSyntaxException {
        String lettersOnlyRegex = "[a-zA-Z]*";
        if (input.matches(lettersOnlyRegex))
            return input;
        throw new PatternSyntaxException("not a plate number", lettersOnlyRegex, -1);
    }
}
