package model;

public class BusinessMerchant {
    private final int merchantId; // same value as merchant.merchant_id
    private final String legalName, businessRegistrationNo, taxNumber, contactPerson;

    public BusinessMerchant(int merchantId, String legalName, String businessRegistrationNo,
            String taxNumber, String contactPerson) {
        this.merchantId = merchantId;
        this.legalName = legalName;
        this.businessRegistrationNo = businessRegistrationNo;
        this.taxNumber = taxNumber;
        this.contactPerson = contactPerson;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getBusinessRegistrationNo() {
        return businessRegistrationNo;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }
}