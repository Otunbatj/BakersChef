
package com.udacity.joseph.bakerchef.ui.dependency.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/*
handles dependency control
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
