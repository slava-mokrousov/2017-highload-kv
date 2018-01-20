package ru.mail.polis.slava_mokrousov;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Created by Slava on 16.01.2018.
 */
public interface MyDAO {
    @NotNull
    byte[] get(@NotNull  String key) throws NoSuchElementException,IllegalArgumentException,IOException;

    void upsert(@NotNull String key, @NotNull byte[] value) throws IllegalArgumentException,IOException;

    @NotNull
    void delete(@NotNull  String key) throws IllegalArgumentException,IOException;
}
