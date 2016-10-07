package com.spectrl.comix.api;

import com.spectrl.comix.util.HashUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kavi @ SPECTRL Ltd. on 02/10/2016.
 */

public class MarvelAuthorizationInterceptor implements Interceptor {
    private final static Logger LOGGER = Logger.getLogger(MarvelAuthorizationInterceptor.class.getName());

    private final static String PARAM_TIMESTAMP = "ts";
    private final static String PARAM_API_KEY   = "apikey";
    private final static String PARAM_HASH      = "hash";

    private final String publicKey;
    private final String privateKey;

    public MarvelAuthorizationInterceptor(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        final String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
        String hash = "";
        try {
            hash = HashUtils.md5Hex(timestamp + privateKey + publicKey);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(PARAM_TIMESTAMP, timestamp)
                .addQueryParameter(PARAM_API_KEY, publicKey)
                .addQueryParameter(PARAM_HASH, hash)
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
