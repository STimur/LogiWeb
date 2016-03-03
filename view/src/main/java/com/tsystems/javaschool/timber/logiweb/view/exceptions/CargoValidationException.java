package com.tsystems.javaschool.timber.logiweb.view.exceptions;

import com.tsystems.javaschool.timber.logiweb.view.util.ValidationUnit;

/**
 * Created by tims on 3/3/2016.
 */
public class CargoValidationException extends Exception {
    ValidationUnit nameValidationUnit;
    ValidationUnit weightValidationUnit;
    private boolean isValid = true;

    public CargoValidationException(String message) {
        super(message);
        nameValidationUnit = new ValidationUnit();
        weightValidationUnit = new ValidationUnit();
    }

    public ValidationUnit getNameValidationUnit() {
        return nameValidationUnit;
    }

    public ValidationUnit getWeightValidationUnit() {
        return weightValidationUnit;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
