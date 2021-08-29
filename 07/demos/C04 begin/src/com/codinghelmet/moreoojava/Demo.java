package com.codinghelmet.moreoojava;

import com.codinghelmet.moreoojava.states.DeviceStatus;
import com.codinghelmet.moreoojava.warranties.TimeLimitedWarranty;

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

        if (status.equals(DeviceStatus.allFine())) {
            this.claimMoneyBack(article, today);
        }
        else if (status.equals(DeviceStatus.notOperational())) {
            this.claimMoneyBack(article, today);
            this.claimExpress(article, today);
        }
        else if (status.equals(DeviceStatus.visiblyDamaged())) {
        }
        else if (status.equals(DeviceStatus.sensorFailed())) {
            this.claimMoneyBack(article, today);
            this.claimExtended(article, today, sensorFailureDate);
        }
        else if (status.equals(DeviceStatus.notOperational().add(DeviceStatus.visiblyDamaged()))) {
            this.claimExpress(article, today);
        }
        else if (status.equals(DeviceStatus.notOperational().add(DeviceStatus.sensorFailed()))) {
            this.claimMoneyBack(article, today);
            this.claimExpress(article, today);
            this.claimExtended(article, today, sensorFailureDate);
        }
        else if (status.equals(DeviceStatus.visiblyDamaged().add(DeviceStatus.sensorFailed()))) {
            this.claimExtended(article, today, sensorFailureDate);
        }
        else if (status.equals(DeviceStatus.notOperational().add(DeviceStatus.visiblyDamaged()).add(DeviceStatus.sensorFailed()))) {
            this.claimExpress(article, today);
            this.claimExtended(article, today, sensorFailureDate);
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

        this.claimWarranty(item, DeviceStatus.allFine(), Optional.empty());
        this.claimWarranty(item, DeviceStatus.visiblyDamaged(), Optional.empty());
        this.claimWarranty(item, DeviceStatus.notOperational(), Optional.empty());
        this.claimWarranty(item, DeviceStatus.notOperational().andVisiblyDamaged(), Optional.empty());

        LocalDate sensorExamined = LocalDate.now().minus(2, ChronoUnit.DAYS);
        this.claimWarranty(item, DeviceStatus.sensorFailed(), Optional.of(sensorExamined));
        this.claimWarranty(item, DeviceStatus.notOperational().andSensorFailed(), Optional.of(sensorExamined));
    }
}

























