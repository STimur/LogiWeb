package com.tsystems.javaschool.timber.logiweb.utility;

import com.tsystems.javaschool.timber.logiweb.exceptions.IntegerOutOfRangeException;

/**
 * Created by tims on 2/29/2016.
 */
public class IntegerParser {
    public static int parseNumber(String input, int min, int max) throws IntegerOutOfRangeException {
        int num = Integer.valueOf(input);
        if (num < min)
            throw new IntegerOutOfRangeException();
        if (num > max)
            throw new IntegerOutOfRangeException();
        return num;
    }
}
