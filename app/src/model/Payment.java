package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Payment {
    private final int id;
    private final BigDecimal amountPaid, platformCommission;
    private final String paymentMethod, paymentStatus;
    private final Timestamp paymentDate;

    public Payment(int id, BigDecimal amountPaid, String paymentMethod, String paymentStatus,
            BigDecimal platformCommission, Timestamp paymentDate) {
        this.id = id;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.platformCommission = platformCommission;
        this.paymentDate = paymentDate;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    } // PENDING / COMPLETED / FAILED / REFUNDED

    public BigDecimal getPlatformCommission() {
        return platformCommission;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }
}