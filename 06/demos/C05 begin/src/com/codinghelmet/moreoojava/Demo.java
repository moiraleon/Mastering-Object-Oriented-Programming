package com.codinghelmet.moreoojava;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class Demo {
    private void offerMoneyBack() { System.out.println("Offer money back."); }
    private void offerRepair() { System.out.println("Offer repair."); }
    private void offerSensorRepair() { System.out.println("Offer sensor replacement."); }

    private void claimWarranty(Article article, DeviceStatus status, Optional<LocalDate> sensorFailureDate) {
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
                this.claimExtended(article, today, sensorFailureDate);
                break;
            case NOT_OPERATIONAL_DAMAGED:
                this.claimExpress(article, today);
                break;
            case NOT_OPERATIONAL_SENSOR_FAILED:
                this.claimMoneyBack(article, today);
                this.claimExpress(article, today);
                this.claimExtended(article, today, sensorFailureDate);
                break;
            case DAMAGED_SENSOR_FAILED:
                this.claimExtended(article, today, sensorFailureDate);
                break;
            case NOT_OPERATIONAL_DAMAGED_SENSOR_FAILED:
                this.claimExpress(article, today);
                this.claimExtended(article, today, sensorFailureDate);
                break;
        }

        System.out.println("--------------------");
    }

    private void claimExtended(Article article, LocalDate today, Optional<LocalDate> sensorFailureDate) {
        sensorFailureDate
            .flatMap(date -> article.getExtendedWarranty().filter(date))
            .ifPresent(warranty -> warranty.on(today).claim(this::offerSensorRepair));
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

        this.claimWarranty(item, DeviceStatus.ALL_FINE, Optional.empty());
        this.claimWarranty(item, DeviceStatus.VISIBLY_DAMAGED, Optional.empty());
        this.claimWarranty(item, DeviceStatus.NOT_OPERATIONAL, Optional.empty());
        this.claimWarranty(item, DeviceStatus.NOT_OPERATIONAL.add(DeviceStatus.VISIBLY_DAMAGED), Optional.empty());

        LocalDate sensorExamined = LocalDate.now().minus(2, ChronoUnit.DAYS);
        this.claimWarranty(item, DeviceStatus.SENSOR_FAILED, Optional.of(sensorExamined));
        this.claimWarranty(item, DeviceStatus.NOT_OPERATIONAL.add(DeviceStatus.SENSOR_FAILED), Optional.of(sensorExamined));
    }
}
