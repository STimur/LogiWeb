package com.tsystems.javaschool.timber.logiweb.view.exceptions;

import com.tsystems.javaschool.timber.logiweb.view.util.ValidationUnit;

/**
 * Created by tims on 3/3/2016.
 */
public class TruckValidationException extends Exception {
    ValidationUnit plateNumberValidationUnit;
    ValidationUnit shiftSizeValidationUnit;
    ValidationUnit capacityValidationUnit;
    private boolean isValid = true;

    public TruckValidationException(String message) {
        super(message);
        plateNumberValidationUnit = new ValidationUnit();
        shiftSizeValidationUnit = new ValidationUnit();
        capacityValidationUnit = new ValidationUnit();
    }

    public ValidationUnit getPlateNumberValidationUnit() {
        return plateNumberValidationUnit;
    }

    public ValidationUnit getShiftSizeValidationUnit() {
        return shiftSizeValidationUnit;
    }

    public ValidationUnit getCapacityValidationUnit() {
        return capacityValidationUnit;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
