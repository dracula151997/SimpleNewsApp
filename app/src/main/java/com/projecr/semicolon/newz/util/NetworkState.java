package com.projecr.semicolon.newz.util;

public class NetworkState {

    public enum Status{
        RUNNING,
        SUCCESS,
        FAILED
    }

    private Status status;
    private String message;

    public static final NetworkState LOADING;
    public static final NetworkState LOADED;

    public NetworkState(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    static {
        LOADING = new NetworkState(Status.RUNNING, "running");
        LOADED = new NetworkState(Status.SUCCESS, "success");
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
