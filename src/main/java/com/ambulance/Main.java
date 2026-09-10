package com.ambulance;

public class Main {

    public static void main(String[] args) {

        AmbulanceDispatchService dispatchService =
                new AmbulanceDispatchService();

        Ambulance ambulance1 =
                new Ambulance(
                        "AMB101",
                        AmbulanceType.BASIC,
                        "Rahul",
                        4.5
                );

        Ambulance ambulance2 =
                new Ambulance(
                        "AMB102",
                        AmbulanceType.ADVANCED_LIFE_SUPPORT,
                        "Arun",
                        7.0
                );

        Ambulance ambulance3 =
                new Ambulance(
                        "AMB103",
                        AmbulanceType.ICU,
                        "David",
                        10.0
                );

        dispatchService.addAmbulance(ambulance1);
        dispatchService.addAmbulance(ambulance2);
        dispatchService.addAmbulance(ambulance3);

        try {

            EmergencyRequest emergency1 =
                    new EmergencyRequest(
                            "P101",
                            "Road Accident",
                            "VIT Main Gate",
                            "CMC Hospital",
                            EmergencyPriority.CRITICAL
                    );

            EmergencyRequest emergency2 =
                    new EmergencyRequest(
                            "P102",
                            "Minor Injury",
                            "Katpadi",
                            "Apollo Hospital",
                            EmergencyPriority.NORMAL
                    );

            dispatchService.submitEmergency(
                    emergency1
            );

            dispatchService.submitEmergency(
                    emergency2
            );

        } catch (InvalidEmergencyException exception) {

            System.out.println(
                    "Error: " +
                    exception.getMessage()
            );
        }

        System.out.println("\n--- Ambulance Status ---");

        for (Ambulance ambulance :
                dispatchService.getAmbulances()) {

            System.out.println(ambulance);
        }

        System.out.println("\n--- Emergency History ---");

        for (EmergencyRequest request :
                dispatchService.getEmergencyHistory()) {

            System.out.println(request);
        }
    }
}