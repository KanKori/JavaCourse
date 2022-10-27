package com.manufacture.robots;

import com.manufacture.ManufacturingFacility;
import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class FormingRobot implements Runnable {
    private static ManufacturingFacility manufacturingInstance;
    private final AtomicInteger programmingCompletionPercentage = manufacturingInstance.getProgrammingCompletionPercentage();
    private final AtomicInteger fuel = manufacturingInstance.getFuel();
    private static final int STEP;
    private static final int MIN_FUEL_GALLONS;
    private static final int MAX_FUEL_GALLONS;
    private static final int ONE_SECOND;
    private static final int FINISH_PROGRESS;
    private static final int START_PROGRESS;

    static {
        manufacturingInstance = ManufacturingFacility.getManufacturingInstance();
        STEP = 10;
        MIN_FUEL_GALLONS = 350;
        MAX_FUEL_GALLONS = 701;
        ONE_SECOND = 1000;
        FINISH_PROGRESS = 100;
        START_PROGRESS = 0;
    }

    @SneakyThrows
    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int progress = START_PROGRESS;
            if (programmingCompletionPercentage.get() >= FINISH_PROGRESS) {
                System.out.println("Robot 5 start");
            }
            while (programmingCompletionPercentage.get() >= FINISH_PROGRESS && progress < FINISH_PROGRESS) {
                int fuelNeed = random.nextInt(MIN_FUEL_GALLONS, MAX_FUEL_GALLONS);
                System.out.println("Forming Robot fuel need = " + fuelNeed);
                System.out.println("Current fuel = " + fuel.get());
                while (fuelNeed > fuel.get()) ;
                fuel.set(fuel.get() - fuelNeed);
                progress += STEP;
                System.out.println("Forming Robot progress = " + progress);
                Thread.sleep(ONE_SECOND);
                if (progress == FINISH_PROGRESS) {
                    programmingCompletionPercentage.set(START_PROGRESS);
                    manufacturingInstance.getDetailCompletionPercentage().set(START_PROGRESS);
                }
            }
        }
    }
}
