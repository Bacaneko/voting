package ru.bacaneco.voting.util.exception;

public class IllegalVoteException extends RuntimeException {
    public IllegalVoteException(String message) {
        super(message);
    }
}
