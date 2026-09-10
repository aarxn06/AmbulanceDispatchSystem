package com.ambulance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AmbulanceDispatchService {

    private final List<Ambulance> ambulances;
    private final List<EmergencyRequest> emergencyHistory;
    private final PriorityQueue<EmergencyRequest> waitingQueue;

    public AmbulanceDispatchService() {

        ambulances = new ArrayList<>();
        emergencyHistory = new ArrayList<>();

        waitingQueue = new PriorityQueue<>(
                Comparator.comparingInt(
                        request -> request.getPriority().getPriorityValue()
                )
        );
    }

    public void addAmbulance(Ambulance ambulance) {

        if (ambulance == null) {
            throw new IllegalArgumentException("Ambulance cannot be null.");
        }

        ambulances.add(ambulance);
    }

    public void submitEmergency(EmergencyRequest request)
            throws InvalidEmergencyException {

        validateEmergency(request);

        emergencyHistory.add(request);

        Ambulance ambulance = findBestAmbulance(request);

        if (ambulance != null) {
            assignAmbulance(request, ambulance);
        } else {

            request.setStatus(EmergencyStatus.WAITING);
            waitingQueue.offer(request);

            System.out.println(
                    "No ambulance currently available. Emergency added to waiting queue."
            );
        }
    }

    private void validateEmergency(EmergencyRequest request)
            throws InvalidEmergencyException {

        if (request == null) {
            throw new InvalidEmergencyException(
                    "Emergency request cannot be null."
            );
        }

        if (request.getPatientId() == null ||
                request.getPatientId().isBlank()) {

            throw new InvalidEmergencyException(
                    "Patient ID cannot be empty."
            );
        }

        if (request.getPickupLocation() == null ||
                request.getPickupLocation().isBlank()) {

            throw new InvalidEmergencyException(
                    "Pickup location cannot be empty."
            );
        }

        if (request.getDestinationHospital() == null ||
                request.getDestinationHospital().isBlank()) {

            throw new InvalidEmergencyException(
                    "Destination hospital cannot be empty."
            );
        }

        if (request.getPriority() == null) {
            throw new InvalidEmergencyException(
                    "Emergency priority must be specified."
            );
        }
    }

    private Ambulance findBestAmbulance(EmergencyRequest request) {

        return ambulances.stream()
                .filter(Ambulance::isAvailable)
                .filter(ambulance ->
                        ambulanceSuitableForEmergency(
                                ambulance,
                                request
                        )
                )
                .min(Comparator.comparingDouble(
                        Ambulance::getDistanceFromPatient
                ))
                .orElse(null);
    }

    private boolean ambulanceSuitableForEmergency(
            Ambulance ambulance,
            EmergencyRequest request) {

        if (request.getPriority() == EmergencyPriority.CRITICAL) {

            return ambulance.getType() == AmbulanceType.ICU
                    || ambulance.getType() ==
                    AmbulanceType.ADVANCED_LIFE_SUPPORT;
        }

        if (request.getPriority() == EmergencyPriority.HIGH) {
            return ambulance.getType() != AmbulanceType.BASIC;
        }

        return true;
    }

    private void assignAmbulance(
            EmergencyRequest request,
            Ambulance ambulance) {

        ambulance.setStatus(AmbulanceStatus.DISPATCHED);

        request.setAssignedAmbulance(ambulance);
        request.setStatus(EmergencyStatus.AMBULANCE_ASSIGNED);

        double eta =
                calculateETA(ambulance.getDistanceFromPatient());

        request.setEstimatedArrivalTime(eta);

        System.out.println("\nAmbulance allocated successfully.");
        System.out.println("Patient: " + request.getPatientId());
        System.out.println("Ambulance: " + ambulance.getAmbulanceId());
        System.out.println("Driver: " + ambulance.getDriverName());

        System.out.println(
                "Estimated arrival: " +
                String.format("%.2f", eta) +
                " minutes"
        );
    }

    public double calculateETA(double distance) {

        double averageSpeed = 60.0;

        return (distance / averageSpeed) * 60;
    }

    public void updateAmbulanceStatus(
            String ambulanceId,
            AmbulanceStatus newStatus) {

        Ambulance ambulance = findAmbulanceById(ambulanceId);

        if (ambulance == null) {
            System.out.println("Ambulance not found.");
            return;
        }

        ambulance.setStatus(newStatus);

        if (newStatus == AmbulanceStatus.HOSPITAL_ARRIVED) {

            ambulance.setStatus(AmbulanceStatus.AVAILABLE);

            processWaitingQueue();
        }
    }

    private Ambulance findAmbulanceById(String ambulanceId) {

        for (Ambulance ambulance : ambulances) {

            if (ambulance.getAmbulanceId()
                    .equalsIgnoreCase(ambulanceId)) {

                return ambulance;
            }
        }

        return null;
    }

    private void processWaitingQueue() {

        if (waitingQueue.isEmpty()) {
            return;
        }

        EmergencyRequest nextEmergency =
                waitingQueue.peek();

        Ambulance ambulance =
                findBestAmbulance(nextEmergency);

        if (ambulance != null) {

            waitingQueue.poll();

            assignAmbulance(
                    nextEmergency,
                    ambulance
            );
        }
    }

    public List<EmergencyRequest> getEmergencyHistory() {
        return emergencyHistory;
    }

    public int getWaitingQueueSize() {
        return waitingQueue.size();
    }

    public List<Ambulance> getAmbulances() {
        return ambulances;
    }
}