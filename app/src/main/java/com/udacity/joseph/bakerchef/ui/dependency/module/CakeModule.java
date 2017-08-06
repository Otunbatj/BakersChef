

package com.udacity.joseph.bakerchef.ui.dependency.module;

import com.udacity.joseph.bakerchef.ui.dependency.scope.PerActivity;
import com.udacity.joseph.bakerchef.ui.mvp.view.MainView;
import com.udacity.joseph.bakerchef.ui.myapi.CakeApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class CakeModule {

    private MainView mView;

    public CakeModule(MainView view) {
        mView = view;
    }

    @PerActivity
    @Provides
    CakeApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(CakeApiService.class);
    }

    @PerActivity
    @Provides
    MainView provideView() {
        return mView;
    }
}
