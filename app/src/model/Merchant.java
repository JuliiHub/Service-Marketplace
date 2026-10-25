package model;

import java.sql.Timestamp;

public class Merchant {
    private final int id;
    private final String email, displayName, verificationStatus;
    private final Timestamp registeredAt;

    public Merchant(int id, String email, String displayName,
            Timestamp registeredAt, String verificationStatus) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.registeredAt = registeredAt;
        this.verificationStatus = verificationStatus;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    } // PENDING / VERIFIED / REJECTED

    @Override
    public String toString() {
        return displayName;
    }
}