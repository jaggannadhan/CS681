package edu.umb.cs681.hw18;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;


public class Main {
    public static void groupSchedulesByZipCode() {
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

    public static void groupSchedulesTrashDay() {
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

    public static void groupSchedulesByDistirct() {
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


    public static void main(String[] args) {
        groupSchedulesByZipCode(); // Processing 1
        groupSchedulesTrashDay(); // Processing 2
        groupSchedulesByDistirct(); // Processing 3
    }
}