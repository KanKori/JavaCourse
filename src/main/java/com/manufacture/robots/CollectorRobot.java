package com.manufacture.robots;

import com.manufacture.ManufacturingFacility;
import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectorRobot implements Runnable {
    private static ManufacturingFacility manufacturingInstance;
    private final AtomicInteger detailCompletionPercentage = manufacturingInstance.getDetailCompletionPercentage();
    private static final int MIN_CREATING_PERCENT;
    private static final int MAX_CREATING_PERCENT;
    private static final int FINISH_PROGRESS;
    private static final int TWO_SECONDS;

    static {
        manufacturingInstance = ManufacturingFacility.getManufacturingInstance();
        MIN_CREATING_PERCENT = 11;
        MAX_CREATING_PERCENT = 21;
        FINISH_PROGRESS = 100;
        TWO_SECONDS = 2000;
    }

    @SneakyThrows
    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            while (detailCompletionPercentage.get() < FINISH_PROGRESS) {
                int creatingPercent = random.nextInt(MIN_CREATING_PERCENT, MAX_CREATING_PERCENT);
                System.out.println("Collector Robot creating detail = " + creatingPercent);
                int currentCreatingProcess = detailCompletionPercentage.addAndGet(creatingPercent);
                System.out.println("Current creating detail = " + currentCreatingProcess);
                Thread.sleep(TWO_SECONDS);
            }
        }
    }
}
