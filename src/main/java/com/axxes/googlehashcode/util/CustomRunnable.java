package com.axxes.googlehashcode.util;

import java.util.function.Supplier;

import static com.axxes.googlehashcode.util.Util.writeString;

public class CustomRunnable implements Runnable {

    private final String fileName;
    private final Supplier<String> supplier;

    public CustomRunnable(final String fileName, final Supplier<String> supplier) {
        this.fileName = fileName;
        this.supplier = supplier;
    }

    @Override
    public void run() {
        writeString(fileName, supplier.get());
    }
}
