package com.codinghelmet.moreoojava;

public class DeviceStatus {
    public static final DeviceStatus allFine() { return new DeviceStatus(0); }
    public static final DeviceStatus notOperational() { return new DeviceStatus(1); }
    public static final DeviceStatus visiblyDamaged() { return new DeviceStatus(2); }
    public static final DeviceStatus sensorFailed() { return new DeviceStatus(4); }

    private final int representation;

    private DeviceStatus(int representation) {
        this.representation = representation;
    }

    public DeviceStatus add(DeviceStatus status) {
        return new DeviceStatus(this.representation | status.representation);
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
}

