package com.manufacture;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ManufacturingFacility {

    private static ManufacturingFacility manufacturingInstance;
    private final AtomicInteger fuel = new AtomicInteger(0);
    private final AtomicInteger detailCompletionPercentage = new AtomicInteger(0);
    private final AtomicInteger programmingCompletionPercentage = new AtomicInteger(0);
    private final AtomicBoolean completed = new AtomicBoolean(false);

    private ManufacturingFacility() {
    }

    public static ManufacturingFacility getManufacturingInstance() {
        if (manufacturingInstance == null) {
            synchronized (ManufacturingFacility.class) {
                if (manufacturingInstance == null) {
                    manufacturingInstance = new ManufacturingFacility();
                }
            }
        }
        return manufacturingInstance;
    }


    public AtomicInteger getFuel() {
        return fuel;
    }

    public AtomicInteger getDetailCompletionPercentage() {
        return detailCompletionPercentage;
    }

    public AtomicInteger getProgrammingCompletionPercentage() {
        return programmingCompletionPercentage;
    }

    public AtomicBoolean getCompleted() {
        return completed;
    }
}
