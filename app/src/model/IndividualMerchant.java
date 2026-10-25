package model;

import java.sql.Date;

public class IndividualMerchant {
    private final int merchantId; // same value as merchant.merchant_id
    private final String firstName, lastName, nationalId;
    private final Date dateOfBirth; // nullable

    public IndividualMerchant(int merchantId, String firstName, String lastName,
            String nationalId, Date dateOfBirth) {
        this.merchantId = merchantId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.dateOfBirth = dateOfBirth;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}