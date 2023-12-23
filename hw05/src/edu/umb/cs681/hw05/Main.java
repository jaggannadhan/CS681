package edu.umb.cs681.hw05;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;


class Parallel1 implements Runnable {
    private String threadName;

    public Parallel1(String threadName) {
        this.threadName = threadName;
    }

    public void groupSchedulesByZipCode() {
        Path path = Paths.get("trash-schedules-by-address.csv");

        try (Stream<String> rows = Files.lines(path)) {
            List<List<String>> schedules = rows.skip(1).map(row -> {
                return Stream.of(row.split(",")).map(str -> str.substring(0)).collect(Collectors.toList());
            }).collect(Collectors.toList());

            Map<String, List<List<String>>> areaCategory = schedules.stream().collect(Collectors.groupingBy((List<String> str)->str.get(4)));
            System.out.println(areaCategory.get("02136"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        groupSchedulesByZipCode();
    }
}

class Parallel2 implements Runnable {
    private String threadName;

    public Parallel2(String threadName) {
        this.threadName = threadName;
    }

    public void groupSchedulesTrashDay() {
        Path path = Paths.get("trash-schedules-by-address.csv");

        try (Stream<String> rows = Files.lines(path)) {
            List<List<String>> schedules = rows.skip(1).map(row -> {
                return Stream.of(row.split(",")).map(str -> str.substring(0)).collect(Collectors.toList());
            }).collect(Collectors.toList());

            // System.out.println(schedules.get(0));
            Map<String, List<List<String>>> dayCategory = schedules.stream().collect(Collectors.groupingBy((List<String> str)->str.get(4)));
            System.out.println(dayCategory.get("T"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        groupSchedulesTrashDay();
    }
}

class Parallel3 implements Runnable {
    private String threadName;

    public Parallel3(String threadName) {
        this.threadName = threadName;
    }

    public void groupSchedulesByDistirct() {
        Path path = Paths.get("trash-schedules-by-address.csv");

        try (Stream<String> rows = Files.lines(path)) {
            List<List<String>> schedules = rows.skip(1).map(row -> {
                return Stream.of(row.split(",")).map(str -> str.substring(0)).collect(Collectors.toList());
            }).collect(Collectors.toList());

            // System.out.println(schedules.get(0));
            Map<String, List<List<String>>> dayCategory = schedules.stream().collect(Collectors.groupingBy((List<String> str)->str.get(4)));
            System.out.println(dayCategory.get("8"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        groupSchedulesByDistirct();
    }
}

public class Main {    
    public static void main(String[] args) {
        System.out.println("Main thread started!");

        Parallel1 parallel1 = new Parallel1("Thread1");
        Parallel2 parallel2 = new Parallel2("Thread2");
        Parallel3 parallel3 = new Parallel3("Thread3");

        Thread thread1 = new Thread(parallel1);
        Thread thread2 = new Thread(parallel2);
        Thread thread3 = new Thread(parallel3);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch(Exception e) {
            System.out.println(e);
        }
        

        System.out.println("Main thread ended!");
    }
}