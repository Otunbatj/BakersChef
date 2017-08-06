

package com.udacity.joseph.bakerchef.ui.myapi;



import com.udacity.joseph.bakerchef.ui.mvp.model.CakesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

public interface CakeApiService {

    @GET("/android-baking-app-json")
    Observable<List<CakesResponse>>getCakes();

    @GET("/android-baking-app-json")
    Call<List<CakesResponse>>getTheCakes();
}
