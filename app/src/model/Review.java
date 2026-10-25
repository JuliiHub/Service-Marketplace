package model;

import java.sql.Timestamp;

public class Review {
    private final int id, bookingId, score;
    private final String comment;
    private final Timestamp createdAt;

    public Review(int id, int bookingId, int score, String comment, Timestamp createdAt) {
        this.id = id;
        this.bookingId = bookingId;
        this.score = score;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getScore() {
        return score;
    } // 1 to 5

    public String getComment() {
        return comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}