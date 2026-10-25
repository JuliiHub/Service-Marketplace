package model;

import java.sql.Timestamp;

public class Customer {
    private final int id;
    private final String email, firstName, lastName;
    private final Timestamp registeredAt;

    public Customer(int id, String email, String firstName, String lastName, Timestamp registeredAt) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registeredAt = registeredAt;
    }

    // short form used by the earlier CustomerDao
    public Customer(int id, String firstName, String lastName) {
        this(id, null, firstName, lastName, null);
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}