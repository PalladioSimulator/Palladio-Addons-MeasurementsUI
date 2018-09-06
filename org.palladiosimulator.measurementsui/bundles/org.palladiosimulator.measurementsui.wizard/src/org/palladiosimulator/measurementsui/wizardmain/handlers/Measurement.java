package org.palladiosimulator.measurementsui.wizardmain.handlers;

public class Measurement {
    
    public static enum MeasurementType {
        feedThrough, timeDriven, varSizeAggregation;
    }
    
    private String name = "Ressource Demand";
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    private MeasurementType type = MeasurementType.varSizeAggregation;
    public MeasurementType getType() {
        return type;
    }
    public void setType(MeasurementType type) {
        this.type = type;
    }
    private int property1 = 1;
    public int getProperty1() {
        return property1;
    }
    public void setProperty1(int property1) {
        this.property1 = property1;
    }
    private int property2 = 10;
    public int getProperty2() {
        return property2;
    }
    public void setProperty2(int property2) {
        this.property2 = property2;
    }
    
    private boolean triggerSelfAdapting = false;
    public boolean isTriggerSelfAdapting() {
        return triggerSelfAdapting;
    }
    public void setTriggerSelfAdapting(boolean triggerSelfAdapting) {
        this.triggerSelfAdapting = triggerSelfAdapting;
    }
}
