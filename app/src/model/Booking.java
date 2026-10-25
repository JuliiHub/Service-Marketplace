package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Booking {
    private final int id, customerId, serviceId;
    private final Integer paymentId; // nullable: a booking may have no payment yet
    private final Timestamp bookingDatetime, createdAt;
    private final String status, customerNote;
    private final BigDecimal chargedPrice;

    public Booking(int id, int customerId, int serviceId, Integer paymentId,
            Timestamp bookingDatetime, String status, BigDecimal chargedPrice,
            String customerNote, Timestamp createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.paymentId = paymentId;
        this.bookingDatetime = bookingDatetime;
        this.status = status;
        this.chargedPrice = chargedPrice;
        this.customerNote = customerNote;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public Timestamp getBookingDatetime() {
        return bookingDatetime;
    }

    public String getStatus() {
        return status;
    } // REQUESTED / CONFIRMED / COMPLETED / CANCELLED

    public BigDecimal getChargedPrice() {
        return chargedPrice;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}