package com.spectrl.comix.collection.data.repository;

import rx.Completable;
import rx.Observable;

/**
 * Created by Kavi @ SPECTRL Ltd. on 15/07/15.
 */
public interface DiskCache<K, V> {

    Completable put(K key, V value);

    Observable<V> get(K key);
}
