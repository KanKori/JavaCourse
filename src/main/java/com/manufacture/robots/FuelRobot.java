package com.manufacture.robots;

import com.manufacture.ManufacturingFacility;
import lombok.SneakyThrows;

import java.util.Random;

public class FuelRobot implements Runnable {

    private static ManufacturingFacility manufacturingInstance;
    private static final int MIN_GALLONS;
    private static final int MAX_GALLONS;
    private static final int THREE_SECONDS;

    static {
        manufacturingInstance = ManufacturingFacility.getManufacturingInstance();
        MIN_GALLONS = 501;
        MAX_GALLONS = 1001;
        THREE_SECONDS = 3000;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (!manufacturingInstance.getCompleted().get()) {
            int foundFuel = new Random().nextInt(MIN_GALLONS, MAX_GALLONS);
            System.out.println("FuelRobot get " + foundFuel + " gallons of fuel");
            int currentFuel = manufacturingInstance.getFuel().addAndGet(foundFuel);
            System.out.println("Current fuel = " + currentFuel + " gallons");
            Thread.sleep(THREE_SECONDS);
        }
    }
}
