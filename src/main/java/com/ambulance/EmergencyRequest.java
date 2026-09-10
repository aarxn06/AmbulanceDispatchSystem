package com.ambulance;

public class EmergencyRequest {

    private String patientId;
    private String emergencyType;
    private String pickupLocation;
    private String destinationHospital;     

    private EmergencyPriority priority;
    private EmergencyStatus status;

    private Ambulance assignedAmbulance;
    private double estimatedArrivalTime;

    public EmergencyRequest(String patientId,
                            String emergencyType,
                            String pickupLocation,
                            String destinationHospital,
                            EmergencyPriority priority) {

        this.patientId = patientId;
        this.emergencyType = emergencyType;
        this.pickupLocation = pickupLocation;
        this.destinationHospital = destinationHospital;
        this.priority = priority;

        this.status = EmergencyStatus.WAITING;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getEmergencyType() {
        return emergencyType;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDestinationHospital() {
        return destinationHospital;
    }

    public EmergencyPriority getPriority() {
        return priority;
    }

    public EmergencyStatus getStatus() {
        return status;
    }

    public void setStatus(EmergencyStatus status) {
        this.status = status;
    }

    public Ambulance getAssignedAmbulance() {
        return assignedAmbulance;
    }

    public void setAssignedAmbulance(Ambulance assignedAmbulance) {
        this.assignedAmbulance = assignedAmbulance;
    }

    public double getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }

    public void setEstimatedArrivalTime(double estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    @Override
    public String toString() {

        String ambulanceInfo = assignedAmbulance == null
                ? "Waiting"
                : assignedAmbulance.getAmbulanceId();

        return "Patient: " + patientId +
                " | Emergency: " + emergencyType +
                " | Priority: " + priority +
                " | Ambulance: " + ambulanceInfo +
                " | Status: " + status;
    }
}