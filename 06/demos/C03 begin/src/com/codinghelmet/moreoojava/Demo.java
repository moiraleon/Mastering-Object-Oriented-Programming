package com.codinghelmet.moreoojava;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Demo {
    private void offerMoneyBack() { System.out.println("Offer money back."); }
    private void offerRepair() { System.out.println("Offer repair."); }
    private void offerSensorRepair() { System.out.println("Offer sensor replacement."); }

    private void claimWarranty(Article article, DeviceStatus status) {
        LocalDate today = LocalDate.now();

        switch (status) {
            case ALL_FINE:
                article.getMoneyBackGuarantee().on(today).claim(this::offerMoneyBack);
                break;
            case NOT_OPERATIONAL:
                article.getMoneyBackGuarantee().on(today).claim(this::offerMoneyBack);
                article.getExpressWarranty().on(today).claim(this::offerRepair);
                break;
            case VISIBLY_DAMAGED:
                break;
            case SENSOR_FAILED:
                article.getMoneyBackGuarantee().on(today).claim(this::offerMoneyBack);
                article.getExtendedWarranty().on(today).claim(this::offerSensorRepair);
                break;
        }

        System.out.println("--------------------");
    }

    public void run() {
        LocalDate sellingDate = LocalDate.now().minus(40, ChronoUnit.DAYS);
        Warranty moneyBack = new TimeLimitedWarranty(sellingDate, Duration.ofDays(60));
        Warranty warranty = new TimeLimitedWarranty(sellingDate, Duration.ofDays(365));

        Part sensor = new Part(sellingDate);
        Warranty sensorWarranty = new TimeLimitedWarranty(sellingDate, Duration.ofDays(90));

        Article item = new Article(moneyBack, warranty).install(sensor, sensorWarranty);

        this.claimWarranty(item, DeviceStatus.ALL_FINE);
        this.claimWarranty(item, DeviceStatus.VISIBLY_DAMAGED);
        this.claimWarranty(item, DeviceStatus.NOT_OPERATIONAL);
        this.claimWarranty(item, DeviceStatus.SENSOR_FAILED);
    }
}
