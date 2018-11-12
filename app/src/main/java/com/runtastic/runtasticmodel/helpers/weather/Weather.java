package com.runtastic.runtasticmodel.helpers.weather;

public class Weather {
    private Main main;

    public String name;

    public double getMinTemp() { return main.getMinTemp(); }
    public double getMaxTemp() { return main.getMaxTemp(); }
    public String getName() { return name; }
    public double getTemp() { return main.getTemp(); }
}
