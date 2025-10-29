package org.example;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) {
        List<String> lines = Arrays.asList(
                "2024-06-01T00:00,ADCU001,METER001,118.20,94.73",
                "2024-06-01T00:00,ADCU001,METER002,137.82,105.82",
                "2024-06-01T00:00,ADCU001,METER003,124.31,85.32",
                "2024-06-02T00:00,ADCU001,METER001,133.47,107.94",
                "2024-06-02T00:00,ADCU001,METER002,156.13,119.66",
                "2024-06-02T00:00,ADCU001,METER003,142.54,96.47",
                "2024-06-03T00:00,ADCU001,METER001,148.74,121.15",
                "2024-06-03T00:00,ADCU001,METER002,174.44,133.50",
                "2024-06-03T00:00,ADCU001,METER003,160.77,107.62",

                // ADCU002（新加入）
                "2024-06-01T00:00,ADCU002,METER101,200.00,180.00",
                "2024-06-01T00:00,ADCU002,METER102,300.00,250.00",
                "2024-06-01T00:00,ADCU002,METER103,150.00,100.00",
                "2024-06-02T00:00,ADCU002,METER101,215.50,188.30",
                "2024-06-02T00:00,ADCU002,METER102,320.00,260.00",
                "2024-06-02T00:00,ADCU002,METER103,165.00,110.00",
                "2024-06-03T00:00,ADCU002,METER101,230.00,195.00",
                "2024-06-03T00:00,ADCU002,METER102,340.00,270.00",
                "2024-06-03T00:00,ADCU002,METER103,180.00,120.00"
        );
        ArrayList<Person> people = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String[] split = lines.get(i).split(",");
            people.add(new Person(split));
        }

        Map<String, Map<String, Map<LocalDateTime, BigDecimal>>> result = people.stream()
                .collect(
                        Collectors.groupingBy(
                                data -> data.getAdcuid(),
                                Collectors.groupingBy(
                                        data -> data.getMeterId(),
                                        Collectors.collectingAndThen(
                                                Collectors.toList(),
                                                list -> {
                                                    // 排序
                                                    list.sort(Comparator.comparing(d -> d.getTime()));
                                                    Map<LocalDateTime, BigDecimal> dailyUsage = new LinkedHashMap<>();
                                                    for (int i = 0; i < list.size(); i++) {
                                                        Person current = list.get(i);
                                                        BigDecimal todayNet = current.getNetCumulative();
                                                        BigDecimal yesterdayNet = i == 0 ? todayNet : list.get(i - 1).getNetCumulative();
                                                        BigDecimal daily = todayNet.subtract(yesterdayNet); // 增量
                                                        dailyUsage.put(current.getTime(), daily);
                                                    }
                                                    return dailyUsage;
                                                }
                                        )
                                )));
        Map<String, BigDecimal> totalByAdcu = result.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, // ADCU ID
                        entry -> entry.getValue().values().stream() // 获取所有 meter 的数据
                                .flatMap(map -> map.values().stream()) // 获取所有日期的值
                                .reduce(BigDecimal.ZERO, BigDecimal::add) // 求和
                ));

// 打印每个 ADCU ID 的总和
        totalByAdcu.forEach((adcuid, total) -> {
            System.out.println("ADCU ID: " + adcuid + " -> Total: " + total);
        });
    }
}