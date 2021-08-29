package com.codinghelmet.moreoojava.states;

public class DeviceStatus {
    public static DeviceStatus allFine() { return new DeviceStatus(0); }
    public static DeviceStatus notOperational() { return new DeviceStatus(1); }
    public static DeviceStatus visiblyDamaged() { return new DeviceStatus(2); }
    public static DeviceStatus sensorFailed() { return new DeviceStatus(4); }

    private final int representation;

    private DeviceStatus(int representation) {
        this.representation = representation;
    }

    public DeviceStatus andNotOperational() { return this.add(notOperational()); }
    public DeviceStatus andVisiblyDamaged() { return this.add(visiblyDamaged()); }
    public DeviceStatus andSensorFailed() { return this.add(sensorFailed()); }

    public DeviceStatus add(DeviceStatus status) {
        return new DeviceStatus(this.representation | status.representation);
    }

    private boolean isSupersetOf(DeviceStatus other) {
        return (this.representation & other.representation) == other.representation;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DeviceStatus && this.equals((DeviceStatus)other);
    }

    private boolean equals(DeviceStatus other) {
        return this.representation == other.representation;
    }

    @Override
    public int hashCode() {
        return this.representation;
    }

    @Override
    public String toString() {
        String result = "";
        String separator = "";

        if (this.isSupersetOf(notOperational())) {
            result += separator + "Not operational";
            separator = " + ";
        }

        if (this.isSupersetOf(visiblyDamaged())) {
            result += separator + "Damaged";
            separator = " + ";
        }

        if (this.isSupersetOf(sensorFailed())) {
            result += separator + "Sensor failed";
        }

        return result;
    }
}


























