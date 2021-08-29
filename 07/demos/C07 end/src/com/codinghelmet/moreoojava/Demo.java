package com.codinghelmet.moreoojava;

import com.codinghelmet.moreoojava.rules.ExhaustiveRulesBuilder;
import com.codinghelmet.moreoojava.rules.PartitioningRulesBuilder;
import com.codinghelmet.moreoojava.states.DeviceStatus;
import com.codinghelmet.moreoojava.states.OperationalStatus;
import com.codinghelmet.moreoojava.warranties.TimeLimitedWarranty;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.function.Supplier;

public class Demo {
    private void offerMoneyBack() { System.out.println("Offer money back."); }
    private void offerRepair() { System.out.println("Offer repair."); }
    private void offerSensorRepair() { System.out.println("Offer sensor replacement."); }

    private void claimWarranty(Supplier<ClaimingRulesBuilder> rulesBuilderFactory, Article article, DeviceStatus status) {
        LocalDate today = LocalDate.now();

        rulesBuilderFactory.get()
            .onMoneyBack(s -> this.claimMoneyBack(article, today))
            .onClaimExpress(s -> this.claimExpress(article, today))
            .onClaimExtended(s -> this.claimExtended(article, today, s.getFailureDetectionDate()))
            .build()
            .applicableTo(status)
            .ifPresent(action -> action.apply());

        System.out.println("--------------------");
    }

    private void claimExtended(Article article, LocalDate today, LocalDate sensorFailureDate) {
        article.getExtendedWarranty().filter(sensorFailureDate)
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

        Supplier<ClaimingRulesBuilder> builderFactory = () -> new PartitioningRulesBuilder();

        this.claimWarranty(builderFactory, item, DeviceStatus.allFine());
        this.claimWarranty(builderFactory, item, DeviceStatus.visiblyDamaged());
        this.claimWarranty(builderFactory, item, DeviceStatus.notOperational());
        this.claimWarranty(builderFactory, item, DeviceStatus.notOperational().andVisiblyDamaged());

        LocalDate sensorExamined = LocalDate.now().minus(2, ChronoUnit.DAYS);
        this.claimWarranty(builderFactory, item, DeviceStatus.sensorFailed(sensorExamined));
        this.claimWarranty(builderFactory, item, DeviceStatus.notOperational().andSensorFailed(sensorExamined));
    }
}
