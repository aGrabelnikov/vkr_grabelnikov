package com.example.newanimals.fragment;

import android.app.ProgressDialog;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newanimals.R;
import com.example.newanimals.activity.StartAppActivity;
import com.example.newanimals.adapter.AdsAdapter;
import com.example.newanimals.db.AdsData;
import com.example.newanimals.dialog.InfoAdsBottomDialog;
import com.example.newanimals.presenter.LoadDataPresenter;
import com.example.newanimals.view.LoadDataView;

import java.util.List;

import butterknife.BindView;

public class SearchAdsFragment extends BaseFragment implements LoadDataView, AdsAdapter.OnClickEventListner {
    public static SearchAdsFragment newInstance() {
        return new SearchAdsFragment();
    }

    @BindView(R.id.all) LinearLayout all;
    @BindView(R.id.biznes) LinearLayout biznes;
    @BindView(R.id.auto) LinearLayout auto;
    @BindView(R.id.work) LinearLayout work;
    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.service) LinearLayout service;

    private AdsAdapter adsAdapter;
    private LoadDataPresenter presenter;
    public ProgressDialog dialogFragment;
    @Override
    protected void initViews() {
        super.initViews();
        presenter = new LoadDataPresenter(this);
        presenter.getDataFromDB();
        dialogFragment = ProgressDialog.show(getContext(), "","Пожалуйста подождите...", true);
        all.setOnClickListener(l->{
            presenter.getDataFromDB();
        });
        biznes.setOnClickListener(l->{
            presenter.getDataFromDBType("biznes");
        });
        auto.setOnClickListener(l->{
            presenter.getDataFromDBType("car");
        });
        work.setOnClickListener(l->{
            presenter.getDataFromDBType("work");
        });
        service.setOnClickListener(l->{
            presenter.getDataFromDBType("service");
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.search_ads_fragment_layout;
    }

    @Override
    public void getMessage(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getData(List<AdsData> data) {
        adsAdapter =  new AdsAdapter(data, getContext(), this);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setAdapter(adsAdapter);
        dialogFragment.dismiss();
    }


    @Override
    public void getClick(AdsData data) {
        InfoAdsBottomDialog adsBottomDialog = new InfoAdsBottomDialog(data);
            adsBottomDialog.show(getChildFragmentManager(), "lll");
    }
}
