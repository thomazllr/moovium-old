package com.thomazllr.moovium.exception;

public class AlreadyExistSeatReservationException extends AlreadyExistEntityException {

    public AlreadyExistSeatReservationException() {
        super("That seat is already reserved. Please try again later.");
    }
}
