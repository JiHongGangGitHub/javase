package org.example;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Person {
    private LocalDateTime time;
    private String adcuid;
    private String meterId;
    private BigDecimal a;
    private BigDecimal b;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getAdcuid() {
        return adcuid;
    }

    public void setAdcuid(String adcuid) {
        this.adcuid = adcuid;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public BigDecimal getA() {
        return a;
    }

    public void setA(BigDecimal a) {
        this.a = a;
    }

    public BigDecimal getB() {
        return b;
    }

    public void setB(BigDecimal b) {
        this.b = b;
    }
    // 计算 a - b 的差值（净累积量）
    public BigDecimal getNetCumulative() {
        return a.subtract(b);
    }
    public Person(String[] fields) {
        this.time = LocalDateTime.parse(fields[0]);
        this.adcuid = fields[1];
        this.meterId = fields[2];
        this.a = new BigDecimal(fields[3]);
        this.b = new BigDecimal(fields[4]);
    }
}
