package com.github.elegion.utils;

import android.util.Log;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * @author Daniel Serdyukov
 */
public final class IOUtils {

    public static final int EOF = -1;

    public static final int BUFFER_SIZE = 64 * 1024;

    private IOUtils() {
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            Log.wtf(IOUtils.class.getSimpleName(), e);
        }
    }

    public static String toString(InputStream is) throws IOException {
        final StringBuilder result = new StringBuilder();
        final Reader reader = new InputStreamReader(is, Charset.defaultCharset());
        final char[] buffer = new char[BUFFER_SIZE];
        try {
            int bytes;
            while ((bytes = reader.read(buffer)) != EOF) {
                result.append(buffer, 0, bytes);
            }
        } finally {
            closeQuietly(reader);
        }
        return result.toString();
    }

    public static String toStringQuietly(InputStream is) {
        try {
            return toString(is);
        } catch (IOException e) {
            return "";
        }
    }

}
