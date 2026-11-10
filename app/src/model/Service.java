package model;

import java.math.BigDecimal;

public class Service {
    private final int id, merchantId, categoryId, duration;
    private final String title, description;
    private final BigDecimal listPrice;
    private final boolean offered;

    public Service(int id, int merchantId, int categoryId, String title, String description,
            BigDecimal listPrice, int duration, boolean offered) {
        this.id = id;
        this.merchantId = merchantId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.listPrice = listPrice;
        this.duration = duration;
        this.offered = offered;
    }

    public int getId() {
        return id;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isOffered() {
        return offered;
    }

    @Override
    public String toString() {
        return title;
    } // shown in combo boxes
}