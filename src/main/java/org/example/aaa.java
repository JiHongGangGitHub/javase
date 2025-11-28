package org.example;

import lombok.Data;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class aaa {
    /**
     * 将 List<Record> 按日展开为 List<Result>：列转行
     */
    public static List<Result> unpivotByDay(List<Record> records, YearMonth yearMonth) {
        // 检查输入数据是否为空，如果为空则返回空列表
        if (records == null || records.isEmpty()) return Collections.emptyList();

        // 获取指定年月的总天数（如4月有30天，2月有28或29天）
        int lastDay = yearMonth.lengthOfMonth();
        List<Result> results = new ArrayList<>();

        // 创建一个映射表，用于存储天数与对应getter方法的映射关系
        Map<Integer, Method> methodMap = new HashMap<>();
        Class<Record> clazz = Record.class;

        try {
            // 遍历1到31天，为每一天创建对应的getter方法名并获取Method对象
            for (int i = 1; i <= 31; i++) {
                String methodName = "getDay" + i + "Value";  // 构造getter方法名，如getDay1Value
                methodMap.put(i, clazz.getDeclaredMethod(methodName));  // 获取方法并存入映射表
            }
        } catch (NoSuchMethodException e) {
            // 如果某个方法不存在，则抛出运行时异常
            throw new RuntimeException("Missing getter method", e);
        }

        // 获取该月的第一天作为起始日期
        LocalDate startDate = yearMonth.atDay(1);

        // 遍历每条记录
        for (Record record : records) {
            String device = record.getDevice();  // 获取设备编号

            // 遍历该月的每一天
            for (int day = 1; day <= lastDay; day++) {
                try {
                    // 通过反射调用对应日期的getter方法获取值
                    BigDecimal value = (BigDecimal) methodMap.get(day).invoke(record);

                    // 如果值不为空且不为0，则创建结果对象并添加到结果列表
                    if (value != null && value.compareTo(BigDecimal.ZERO) != 0) {
                        LocalDate date = startDate.plusDays(day - 1);  // 计算具体日期
                        Result result = new Result();                 // 创建结果对象
                        result.setDevice(device);                     // 设置设备编号
                        result.setDate(date);                         // 设置日期
                        result.setValue(value);                       // 设置值
                        results.add(result);                          // 添加到结果列表
                    }
                } catch (Exception e) {
                    // 捕获反射调用过程中的异常并重新抛出
                    throw new RuntimeException("Error accessing day " + day + " value", e);
                }
            }
        }

        // 返回转换后的结果列表
        return results;
    }

    public static void main(String[] args) {
        // 使用示例
        YearMonth currentMonth = YearMonth.of(2025, 4); // 2025年4月（30天）

        Record r1 = new Record();
        r1.setDevice("D001");
        r1.setTotal(new BigDecimal("3000.00"));
        r1.setDay1Value(new BigDecimal("100.5"));
        r1.setDay2Value(new BigDecimal("110.2"));
        r1.setDay3Value(new BigDecimal("95.8"));
        r1.setDay6Value(new BigDecimal("115.8"));
        List<Record> records = Arrays.asList(r1);
        List<Result> result = unpivotByDay(records, currentMonth);
        result.forEach(System.out::println);
    }
}
@Data
class Record {
    private String device;
    private BigDecimal total;
    private BigDecimal day1Value;
    private BigDecimal day2Value;
    private BigDecimal day3Value;
    private BigDecimal day4Value;
    private BigDecimal day5Value;
    private BigDecimal day6Value;
    private BigDecimal day7Value;
    private BigDecimal day8Value;
    private BigDecimal day9Value;
    private BigDecimal day10Value;

    private BigDecimal day11Value;
    private BigDecimal day12Value;
    private BigDecimal day13Value;
    private BigDecimal day14Value;
    private BigDecimal day15Value;
    private BigDecimal day16Value;
    private BigDecimal day17Value;
    private BigDecimal day18Value;
    private BigDecimal day19Value;
    private BigDecimal day20Value;

    private BigDecimal day21Value;
    private BigDecimal day22Value;
    private BigDecimal day23Value;
    private BigDecimal day24Value;
    private BigDecimal day25Value;
    private BigDecimal day26Value;
    private BigDecimal day27Value;
    private BigDecimal day28Value;
    private BigDecimal day29Value;
    private BigDecimal day30Value;
    private BigDecimal day31Value;

}
@Data
class Result {
    private String device;
    private LocalDate date;
    private BigDecimal value;

}

