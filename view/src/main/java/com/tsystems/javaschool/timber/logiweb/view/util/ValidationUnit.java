package com.tsystems.javaschool.timber.logiweb.view.util;

/**
 * Created by tims on 3/3/2016.
 */
public class ValidationUnit {
    private boolean isValid = true;
    private String inputValue;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }
}
