package model;

import java.sql.Timestamp;

public class CustomerSavedService {
    private final int customerId, serviceId;
    private final Timestamp savedAt;

    public CustomerSavedService(int customerId, int serviceId, Timestamp savedAt) {
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.savedAt = savedAt;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public Timestamp getSavedAt() {
        return savedAt;
    }
}