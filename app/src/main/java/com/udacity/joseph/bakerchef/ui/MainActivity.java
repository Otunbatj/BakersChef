package com.udacity.joseph.bakerchef.ui;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.udacity.joseph.bakerchef.R;
import com.udacity.joseph.bakerchef.ui.adapter.CakeAdapter;
import com.udacity.joseph.bakerchef.ui.base.BaseActivity;
import com.udacity.joseph.bakerchef.ui.dependency.components.DaggerCakeComponent;
import com.udacity.joseph.bakerchef.ui.dependency.module.CakeModule;
import com.udacity.joseph.bakerchef.ui.m_utilities.NetworkUtils;
import com.udacity.joseph.bakerchef.ui.mvp.model.Cake;
import com.udacity.joseph.bakerchef.ui.mvp.presenter.CakePresenter;
import com.udacity.joseph.bakerchef.ui.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements MainView {

    @Bind(R.id.cake_list) protected RecyclerView mCakeList;
    @Inject
    protected CakePresenter mPresenter;
    private CakeAdapter mCakeAdapter;
    @Nullable
    private CheckIdleResource mIdlingResource;



    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        getIdlingResource();
        initializeList();
        loadCakes();

    }

    private void loadCakes() {
        if(NetworkUtils.isNetAvailable(this)) {
            mPresenter.getCakes(mIdlingResource);
        } else {
            mPresenter.getCakesFromDatabase();
        }
    }

    private void initializeList() {

        mCakeList.setHasFixedSize(true);

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            mCakeList.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));
            mCakeAdapter = new CakeAdapter(getLayoutInflater());
            mCakeAdapter.setCakeClickListener(mCakeClickListener);
            mCakeList.setAdapter(mCakeAdapter);
        } else {
            mCakeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mCakeAdapter = new CakeAdapter(getLayoutInflater());
            mCakeAdapter.setCakeClickListener(mCakeClickListener);
            mCakeList.setAdapter(mCakeAdapter);
        }

    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 800;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reload:
                loadCakes();
                return true;
            case R.id.action_about:
                showAbout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Baker's Chef, \nUdacity Android developer fast track program \n Project 3! ")
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Get Code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://github.com/otunbatj"));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerCakeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cakeModule(new CakeModule(this))
                .build().inject(this);
    }

    @Override
    public void onCakeLoaded(List<Cake> cakes) {
        if (cakes != null) {
            mCakeAdapter.addCakes(cakes);
        } else {
            mPresenter.getCakesFromDatabase();
        }
    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void onShowToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClearItems() {
        mCakeAdapter.clearCakes();
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new CheckIdleResource();
        }
        return mIdlingResource;
    }


    private CakeAdapter.OnCakeClickListener mCakeClickListener = new CakeAdapter.OnCakeClickListener() {
        @Override
        public void onClick(View v, Cake cake, int position) {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.CAKE, cake);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, v, "cakeImageAnimation");
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }

    };
}

