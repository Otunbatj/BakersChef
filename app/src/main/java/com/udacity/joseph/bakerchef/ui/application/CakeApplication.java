

package com.udacity.joseph.bakerchef.ui.application;


import android.app.Application;

import com.udacity.joseph.bakerchef.ui.dependency.components.ApplicationComponent;
import com.udacity.joseph.bakerchef.ui.dependency.components.DaggerApplicationComponent;
import com.udacity.joseph.bakerchef.ui.dependency.module.ApplicationModule;

public class CakeApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponent();
    }

    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this, "http://go.udacity.com"))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}

