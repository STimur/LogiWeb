package com.tsystems.javaschool.timber.logiweb.test.unit;

import com.tsystems.javaschool.timber.logiweb.exceptions.IntegerOutOfRangeException;
import com.tsystems.javaschool.timber.logiweb.utility.IntegerParser;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tims on 2/29/2016.
 */
public class IntegerParserTest {

    @Test
    public void ParseNumber_ValidInput() throws IntegerOutOfRangeException {
        assertEquals(20, IntegerParser.parseNumber("20", 1, 4000));
    }

    @Test
    public void ParseNumber_ValidNegativeInput() throws IntegerOutOfRangeException {
        assertEquals(-5, IntegerParser.parseNumber("-5", -10, 10));
    }

    @Test(expected = IntegerOutOfRangeException.class)
    public void ParseNumber_OutOfRange() throws IntegerOutOfRangeException {
        assertEquals(20, IntegerParser.parseNumber("5000", 1, 4000));
    }
}