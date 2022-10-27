package com.manufacture.robots;

import com.manufacture.ManufacturingFacility;
import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgrammerRobot implements Runnable {
    private static ManufacturingFacility manufacturingInstance;
    private AtomicInteger detailCompletionPercentage = manufacturingInstance.getDetailCompletionPercentage();
    private AtomicInteger programmingCompletionPercentage = manufacturingInstance.getProgrammingCompletionPercentage();
    private static final int MIN_PERCENT;
    private static final int MAX_PERCENT;
    private static final int FAIL_PERCENT;
    private static final int ONE_SECOND;
    private static final int FINISH_PROGRESS;
    private static final int START_PROGRESS;
    private static final int MIN_FAIL_BOUND;
    private static final int MAX_FAIL_BOUND;

    static {
        manufacturingInstance = ManufacturingFacility.getManufacturingInstance();
        MIN_PERCENT = 25;
        MAX_PERCENT = 36;
        FAIL_PERCENT = 30;
        ONE_SECOND = 1000;
        FINISH_PROGRESS = 100;
        START_PROGRESS = 0;
        MIN_FAIL_BOUND = 1;
        MAX_FAIL_BOUND = 101;
    }

    @SneakyThrows
    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            if (programmingCompletionPercentage.get() == START_PROGRESS && detailCompletionPercentage.get() >= FINISH_PROGRESS
            ) {
                System.out.println("Programmer Robot starts");
            }
            while (programmingCompletionPercentage.get() < FINISH_PROGRESS && detailCompletionPercentage.get() >= FINISH_PROGRESS) {
                int programmingPercent = random.nextInt(MIN_PERCENT, MAX_PERCENT);
                System.out.println("Programmer Robot " + programmingPercent + " percents of process");
                int failChance = random.nextInt(MIN_FAIL_BOUND, MAX_FAIL_BOUND);
                if (failChance <= FAIL_PERCENT) {
                    System.out.println("Failed");
                    programmingCompletionPercentage.set(START_PROGRESS);
                } else {
                    programmingCompletionPercentage.getAndAdd(programmingPercent);
                }
                System.out.println("Programmer Robot process percent = " + programmingCompletionPercentage.get());
                Thread.sleep(ONE_SECOND);
            }
        }
    }
}
