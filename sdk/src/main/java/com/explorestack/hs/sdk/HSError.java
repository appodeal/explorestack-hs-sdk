package com.explorestack.hs.sdk;

import androidx.annotation.NonNull;

public class HSError {

    public static HSError NoServices = new HSError(1, "No services provided");
    public static HSError NoConnectors = new HSError(2, "No connectors provided");
    public static HSError NoIAPValidateHandlers = new HSError(3, "No IAP validators found");
    public static HSError NoIAPValidateTimeout = new HSError(4, "IAP validation timeout");

    public static HSError forComponent(@NonNull HSComponent component, @NonNull String message) {
        return new HSError(1000, String.format("[%s]: %s", component.getName(), message));
    }

    private int code;
    @NonNull
    private String message;

    private HSError(int code, @NonNull String message) {
        this.code = code;
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return "HSError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
