package com.ambulance;

public class Ambulance {

    private String ambulanceId;
    private AmbulanceType type;
    private String driverName;
    private double distanceFromPatient;
    private AmbulanceStatus status;

    public Ambulance(String ambulanceId,
                     AmbulanceType type,
                     String driverName,
                     double distanceFromPatient) {

        this.ambulanceId = ambulanceId;
        this.type = type;
        this.driverName = driverName;
        this.distanceFromPatient = distanceFromPatient;
        this.status = AmbulanceStatus.AVAILABLE;
    }

    public String getAmbulanceId() {
        return ambulanceId;
    }

    public AmbulanceType getType() {
        return type;
    }

    public String getDriverName() {
        return driverName;
    }

    public double getDistanceFromPatient() {
        return distanceFromPatient;
    }

    public void setDistanceFromPatient(double distanceFromPatient) {
        this.distanceFromPatient = distanceFromPatient;
    }

    public AmbulanceStatus getStatus() {
        return status;
    }

    public void setStatus(AmbulanceStatus status) {
        this.status = status;
    }

    public boolean isAvailable() {
        return status == AmbulanceStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return ambulanceId +
                " | " + type +
                " | Driver: " + driverName +
                " | Distance: " + distanceFromPatient + " km" +
                " | Status: " + status;
    }
}
