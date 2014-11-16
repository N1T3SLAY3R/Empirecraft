package com.n1t3slay3r.empirecraft.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * SLAPI = Saving/Loading API API for Saving and Loading Objects. You can use
 * this API in your projects, but please credit the original author of it.
 *
 * @author Tomsik68<tomsik68@gmail.com>
 */
public class SLAPI {

    public static <T extends Object> void save(T obj, File path) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(obj);
            oos.flush();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Object> T load(File path) throws Exception {
        T result;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            result = (T) ois.readObject();
        }
        return result;
    }
}