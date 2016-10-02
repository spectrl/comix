package com.spectrl.comix.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Kavi @ SPECTRL Ltd. on 02/10/2016.
 */

public class HashUtils {

    private HashUtils() {
        throw new AssertionError("Instantiating utility class");
    }

    public static String md5Hex(String data) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(data.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String md5 = bigInt.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while (md5.length() < 32 ) {
            md5 = "0" + md5;
        }
        return md5;
    }
}
