package com.spectrl.comix.api;

import com.ryanharter.auto.value.moshi.MoshiAdapterFactory;
import com.squareup.moshi.JsonAdapter;

/**
 * Created by Kavi @ SPECTRL Ltd. on 27/09/2016.
 */

@MoshiAdapterFactory
public abstract class JsonAdapterFactory implements JsonAdapter.Factory {

    // Static factory method to access the package
    // private generated implementation
    public static JsonAdapter.Factory create() {
        return new AutoValueMoshi_JsonAdapterFactory();
    }
}
