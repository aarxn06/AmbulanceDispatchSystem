package com.ambulance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AmbulanceDispatchServiceTest {

    @Test
    void shouldAddAmbulance() {

        AmbulanceDispatchService service =
                new AmbulanceDispatchService();

        Ambulance ambulance =
                new Ambulance(
                        "AMB101",
                        AmbulanceType.BASIC,
                        "Rahul",
                        5
                );

        service.addAmbulance(ambulance);

        assertEquals(
                1,
                service.getAmbulances().size()
        );
    }


    @Test
    void shouldAssignAvailableAmbulance()
            throws InvalidEmergencyException {

        AmbulanceDispatchService service =
                new AmbulanceDispatchService();

        Ambulance ambulance =
                new Ambulance(
                        "AMB101",
                        AmbulanceType.BASIC,
                        "Rahul",
                        5
                );

        service.addAmbulance(ambulance);

        EmergencyRequest request =
                new EmergencyRequest(
                        "P101",
                        "Minor Injury",
                        "VIT",
                        "CMC",
                        EmergencyPriority.NORMAL
                );

        service.submitEmergency(request);

        assertNotNull(
                request.getAssignedAmbulance()
        );

        assertEquals(
                AmbulanceStatus.DISPATCHED,
                ambulance.getStatus()
        );
    }


    @Test
    void criticalEmergencyShouldNotUseBasicAmbulance()
            throws InvalidEmergencyException {

        AmbulanceDispatchService service =
                new AmbulanceDispatchService();

        Ambulance basic =
                new Ambulance(
                        "AMB101",
                        AmbulanceType.BASIC,
                        "Rahul",
                        2
                );

        Ambulance icu =
                new Ambulance(
                        "AMB102",
                        AmbulanceType.ICU,
                        "Arun",
                        8
                );

        service.addAmbulance(basic);
        service.addAmbulance(icu);

        EmergencyRequest request =
                new EmergencyRequest(
                        "P102",
                        "Cardiac Emergency",
                        "Katpadi",
                        "CMC",
                        EmergencyPriority.CRITICAL
                );

        service.submitEmergency(request);

        assertEquals(
                "AMB102",
                request.getAssignedAmbulance()
                        .getAmbulanceId()
        );
    }


    @Test
    void unavailableAmbulanceShouldCreateWaitingRequest()
            throws InvalidEmergencyException {

        AmbulanceDispatchService service =
                new AmbulanceDispatchService();

        EmergencyRequest request =
                new EmergencyRequest(
                        "P103",
                        "Accident",
                        "Vellore",
                        "CMC",
                        EmergencyPriority.HIGH
                );

        service.submitEmergency(request);

        assertEquals(
                EmergencyStatus.WAITING,
                request.getStatus()
        );

        assertEquals(
                1,
                service.getWaitingQueueSize()
        );
    }


    @Test
    void invalidPatientIdShouldThrowException() {

        AmbulanceDispatchService service =
                new AmbulanceDispatchService();

        EmergencyRequest request =
                new EmergencyRequest(
                        "",
                        "Accident",
                        "Vellore",
                        "CMC",
                        EmergencyPriority.HIGH
                );

        assertThrows(
                InvalidEmergencyException.class,
                () -> service.submitEmergency(request)
        );
    }


    @Test
    void etaShouldBeCalculatedCorrectly() {

        AmbulanceDispatchService service =
                new AmbulanceDispatchService();

        double eta =
                service.calculateETA(10);

        assertEquals(
                10,
                eta,
                0.01
        );
    }
}