

package com.udacity.joseph.bakerchef.ui.mvp.view;



import com.udacity.joseph.bakerchef.ui.mvp.model.Cake;

import java.util.List;


public interface MainView extends BaseView {

    void onCakeLoaded(List<Cake> cakes);

    void onShowDialog(String message);

    void onHideDialog();

    void onShowToast(String message);

    void onClearItems();
}
