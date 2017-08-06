

package com.udacity.joseph.bakerchef.ui.mvp.presenter;

import android.content.Context;

import com.udacity.joseph.bakerchef.R;
import com.udacity.joseph.bakerchef.ui.CheckIdleResource;
import com.udacity.joseph.bakerchef.ui.base.BasePresenter;
import com.udacity.joseph.bakerchef.ui.list_mapper.CakeMapper;
import com.udacity.joseph.bakerchef.ui.mvp.model.Cake;
import com.udacity.joseph.bakerchef.ui.mvp.model.CakesResponse;
import com.udacity.joseph.bakerchef.ui.mvp.model.Storage;
import com.udacity.joseph.bakerchef.ui.mvp.view.MainView;
import com.udacity.joseph.bakerchef.ui.myapi.CakeApiService;


import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;


public class CakePresenter extends BasePresenter<MainView> implements Observer<List<CakesResponse>> {

    @Inject protected CakeApiService mApiService;
    @Inject protected CakeMapper mCakeMapper;
    @Inject protected Storage mStorage;
    CheckIdleResource resource;
    Context context;

    @Inject
    public CakePresenter(Context mContext) {
        this.context = mContext;
    }

    public void getCakes(CheckIdleResource resource) {
        this.resource = resource;
        getView().onShowDialog(context.getString(R.string.loading));
        if (resource != null) resource.setIdleState(false);
        Observable<List<CakesResponse>> cakesResponseObservable = mApiService.getCakes();
        subscribe(cakesResponseObservable, this);

    }

    @Override
    public void onCompleted() {
        getView().onHideDialog();
        if (resource != null) resource.setIdleState(true);
        getView().onShowToast(context.getString(R.string.load_completed));

    }

    @Override
    public void onError(Throwable e) {
        getView().onHideDialog();
        getView().onShowToast(context.getString(R.string.error) + e.getMessage());
    }

    @Override
    public void onNext(List<CakesResponse>  cakesResponse) {
        List<Cake> cakes = mCakeMapper.mapCakes(mStorage, cakesResponse);
        getView().onClearItems();
        getView().onCakeLoaded(cakes);
    }

    public void getCakesFromDatabase() {
        List<Cake> cakes = mStorage.getSavedCakes();
        getView().onClearItems();
        getView().onCakeLoaded(cakes);
    }
}
