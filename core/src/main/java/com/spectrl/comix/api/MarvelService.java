package com.spectrl.comix.api;

import com.spectrl.comix.api.data.model.MarvelApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Kavi @ SPECTRL Ltd. on 27/09/2016.
 */

public interface MarvelService {

    @GET("/v1/public/comics")
    Observable<MarvelApiResponse> getComics(@Query("limit") int limit);
}
