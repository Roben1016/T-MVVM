package com.code.mvvm.core.view.followdraw;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.basiclibrary.base.BaseFragment;
import com.code.mvvm.base.BaseViewPagerFragment;
import com.code.mvvm.core.data.pojo.followdraw.FollowDrawTypeVo;
import com.code.mvvm.core.viewmodel.FollowDrawViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：zhangtianqiu on 18/7/2 14:24
 */
public class FollowDrawFragment extends BaseViewPagerFragment<FollowDrawViewModel> {

    private List<FollowDrawTypeVo.DataBean> titleName;

    public static FollowDrawFragment newInstance() {
        return new FollowDrawFragment();
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        titleName = new ArrayList<>();
        getTabData();
    }

    @Override
    protected void dataObserver() {
        mViewModel.getFollowDrawType().observe(this, new Observer<FollowDrawTypeVo>() {
            @Override
            public void onChanged(@Nullable FollowDrawTypeVo followDrawTypeVo) {
                if (followDrawTypeVo != null) {
                    setData(followDrawTypeVo);
                }
            }
        });
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getTabData();
    }

    @Override
    protected FollowDrawViewModel createViewModelProviders() {
        return VMProviders(this, FollowDrawViewModel.class);
    }

    @Override
    protected String[] createPageTitle() {
        return mArrTitles;
    }

    @Override
    protected List<BaseFragment> createFragments() {
        return mFragments;
    }


    private void getTabData() {
        mViewModel.getFollowDrawTypeData();
    }

    public void setData(FollowDrawTypeVo followDrawTypeVo) {
        mArrTitles = new String[followDrawTypeVo.data.size() + 1];
        titleName.clear();
        FollowDrawTypeVo.DataBean dataBean = new FollowDrawTypeVo.DataBean();
        dataBean.maintypename = ("推荐");
        mArrTitles[0] = "推荐";
        titleName.add(dataBean);
        for (int j = 0; j < followDrawTypeVo.data.size(); j++) {
            titleName.add(followDrawTypeVo.data.get(j));
            mArrTitles[j + 1] = (followDrawTypeVo.data.get(j).maintypename);
        }
        initFragment();
        setAdapter();
    }

    private void initFragment() {
        for (int i = 0; i < titleName.size(); i++) {
            if (i == 0) {
                FollowDrawRecommendFragment followDrawRecommendFragment = FollowDrawRecommendFragment.newInstance();
                mFragments.add(followDrawRecommendFragment);
            } else {
                FollowDrawListFragment followDrawListFragment = FollowDrawListFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("type_id", String.valueOf(titleName.get(i).maintypeid));
                followDrawListFragment.setArguments(bundle);
                mFragments.add(followDrawListFragment);
            }

        }
    }
}