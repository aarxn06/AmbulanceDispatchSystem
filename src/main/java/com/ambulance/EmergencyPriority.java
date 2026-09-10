package com.ambulance;

public enum EmergencyPriority {
    CRITICAL(1),
    HIGH(2),
    MODERATE(3),
    NORMAL(4);

    private final int priorityValue;

    EmergencyPriority(int priorityValue) {
        this.priorityValue = priorityValue;
    }

    public int getPriorityValue() {
        return priorityValue;
    }
}   