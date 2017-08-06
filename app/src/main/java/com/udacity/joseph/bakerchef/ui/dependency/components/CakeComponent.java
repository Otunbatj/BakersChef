

package com.udacity.joseph.bakerchef.ui.dependency.components;



import com.udacity.joseph.bakerchef.ui.MainActivity;
import com.udacity.joseph.bakerchef.ui.dependency.module.CakeModule;
import com.udacity.joseph.bakerchef.ui.dependency.scope.PerActivity;

import dagger.Component;

/*
Component class for dependency control
 */
@PerActivity
@Component(modules = CakeModule.class, dependencies = ApplicationComponent.class)
public interface CakeComponent {

    void inject(MainActivity activity);
}
