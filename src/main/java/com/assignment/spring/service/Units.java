package com.assignment.spring.service;

public enum Units {

    CELSIUS("c"),
    FAHRENHEIT("f");

    private String unit;

    private Units(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return this.unit;
    }

}
