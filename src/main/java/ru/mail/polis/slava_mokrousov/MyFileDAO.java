package ru.mail.polis.slava_mokrousov;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.nio.file.Files.readAllBytes;

/**
 * Created by Slava on 17.01.2018.
 */
public class MyFileDAO implements MyDAO {
    @NotNull
    private final File dir;
    private final HashMap<String, byte[]> myCache;

    public MyFileDAO(@NotNull final File dir) {
        this.dir = dir;
        myCache = new HashMap<>(10000);
    }

    @NotNull
    private File getFile(@NotNull final String key) {
        return new File(dir, key);
    }

    @NotNull
    @Override
    public byte[] get(@NotNull final String key) throws NoSuchElementException, IllegalArgumentException, IOException {
        if (myCache.containsKey(key)) {
            return myCache.get(key);
        }
        final File file = getFile(key);
        if (!file.exists()) {
            throw new NoSuchElementException();
        }
        final byte[] value = readAllBytes(file.toPath());
        myCache.put(key, value);

        return value;
    }

    @Override
    public void upsert(@NotNull final String key, @NotNull final byte[] value) throws IllegalArgumentException, IOException {
        try (OutputStream os = new FileOutputStream(getFile(key))) {
            os.write(value);
        }
        myCache.remove(key);
    }

    @Override
    public void delete(@NotNull final String key) throws IllegalArgumentException, IOException {
        //noinspection ResultOfMethodCallIgnored
        getFile(key).delete();
        myCache.remove(key);
    }
}
