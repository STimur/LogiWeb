package com.tsystems.javaschool.timber.logiweb.view.exceptions;

import com.tsystems.javaschool.timber.logiweb.view.util.ValidationUnit;

/**
 * Created by tims on 3/3/2016.
 */
public class DriverValidationException extends Exception {
    ValidationUnit nameValidationUnit;
    ValidationUnit surnameValidationUnit;
    ValidationUnit hoursOfWorkValidationUnit;
    private boolean isValid = true;

    public DriverValidationException(String message) {
        super(message);
        nameValidationUnit = new ValidationUnit();
        surnameValidationUnit = new ValidationUnit();
        hoursOfWorkValidationUnit = new ValidationUnit();
    }

    public ValidationUnit getNameValidationUnit() {
        return nameValidationUnit;
    }

    public ValidationUnit getSurnameValidationUnit() {
        return surnameValidationUnit;
    }

    public ValidationUnit getHoursOfWorkValidationUnit() {
        return hoursOfWorkValidationUnit;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
