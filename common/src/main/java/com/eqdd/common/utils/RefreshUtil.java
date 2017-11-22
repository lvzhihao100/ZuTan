package com.eqdd.common.utils;

import com.shizhefei.view.coolrefreshview.CoolRefreshView;
import com.shizhefei.view.coolrefreshview.OnPullListener;

/**
 * Created by lv on 17-9-30.
 */

public class RefreshUtil {
    public static void openRefresh(CoolRefreshView coolRefreshView, OnRefresh onRefresh) {
        coolRefreshView.addOnPullListener(new OnPullListener() {
            @Override
            public void onPullBegin(CoolRefreshView refreshView) {

            }

            @Override
            public void onPositionChange(CoolRefreshView refreshView, int status, int dy, int currentDistance) {

            }

            @Override
            public void onRefreshing(CoolRefreshView refreshView) {
                onRefresh.refresh(refreshView);
            }

            @Override
            public void onReset(CoolRefreshView refreshView, boolean pullRelease) {

            }

            @Override
            public void onPullRefreshComplete(CoolRefreshView refreshView) {

            }
        });
    }

    public interface OnRefresh {
        void refresh(CoolRefreshView coolRefreshView);
    }
}
