package com.gamerole.zutan.ui;

import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.library.base.RoutConfig;
import com.gamerole.zutan.R;
import com.gamerole.zutan.SearchActivityCustom;
import com.gamerole.zutan.ui.home.fragment.ZuListFragment;

/**
 * @author吕志豪 .
 * @date 18-2-7  上午10:50.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_SEARCH_ZU)
public class SearchZuActivity extends CommonFullTitleActivity {

    private SearchActivityCustom dataBinding;
    private Fragment fragment;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (SearchActivityCustom) inflate;
        initTopTitleBar("族群搜索");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_search;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        fragment = (Fragment) ARouter.getInstance().build(RoutConfig.APP_FRAGMENT_ZU_LIST).navigation();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void setView() {
        dataBinding.floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {

                if (!TextUtils.isEmpty(currentQuery)) {
                    ((ZuListFragment) fragment).refresh(currentQuery);
                }
            }
        });
    }
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        ((UserListFragment)fragment).refresh(label);
//    }
}
