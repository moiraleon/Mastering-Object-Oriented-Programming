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
                this.claimMoneyBack(article, today);
                break;
            case NOT_OPERATIONAL:
                this.claimMoneyBack(article, today);
                this.claimExpress(article, today);
                break;
            case VISIBLY_DAMAGED:
                break;
            case SENSOR_FAILED:
                this.claimMoneyBack(article, today);
                this.claimExtended(article, today);
                break;
            case NOT_OPERATIONAL_DAMAGED:
                this.claimExpress(article, today);
                break;
            case NOT_OPERATIONAL_SENSOR_FAILED:
                this.claimMoneyBack(article, today);
                this.claimExpress(article, today);
                this.claimExtended(article, today);
                break;
            case DAMAGED_SENSOR_FAILED:
                this.claimExtended(article, today);
                break;
            case NOT_OPERATIONAL_DAMAGED_SENSOR_FAILED:
                this.claimExpress(article, today);
                this.claimExtended(article, today);
                break;
        }

        System.out.println("--------------------");
    }

    private void claimExtended(Article article, LocalDate today) {
        article.getExtendedWarranty().on(today).claim(this::offerSensorRepair);
    }

    private void claimExpress(Article article, LocalDate today) {
        article.getExpressWarranty().on(today).claim(this::offerRepair);
    }

    private void claimMoneyBack(Article article, LocalDate today) {
        article.getMoneyBackGuarantee().on(today).claim(this::offerMoneyBack);
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
