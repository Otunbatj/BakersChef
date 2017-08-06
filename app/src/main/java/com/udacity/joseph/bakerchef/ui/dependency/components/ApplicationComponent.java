
package com.udacity.joseph.bakerchef.ui.dependency.components;

import android.content.Context;


import com.udacity.joseph.bakerchef.ui.dependency.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/*
handles dependency control
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Retrofit exposeRetrofit();

    Context exposeContext();
}
