package com.gamerole.zutan.ui;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.SeekBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.library.base.RoutConfig;
import com.gamerole.zutan.R;
import com.gamerole.zutan.TestActivityCustom;

/**
 * @author吕志豪 .
 * @date 17-11-15  下午4:49.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_TEST)
public class TestActivity extends CommonFullTitleActivity {

    private TestActivityCustom dataBinding;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (TestActivityCustom) inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_test;
    }

    @Override
    public void initData() {

    }

    @Override
    public void setView() {
        ImageUtil.setImage("http://img.blog.csdn.net/20150104175650124?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbWF0cml4X3NwYWNl/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center",dataBinding.ivImage);
        dataBinding.seekBarX.setMax(1500);
        dataBinding.seekBarY.setMax(720);
        ClickUtil.click(dataBinding.btStart,() -> {
            dataBinding.llRoot.setDrawingCacheEnabled(true);
            dataBinding.llRoot.buildDrawingCache();
            dataBinding.blackHole.setVisibility(View.VISIBLE);
            dataBinding.blackHole.collapse(dataBinding.llRoot.getDrawingCache(),500,300);
        });

        dataBinding.seekBarX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                System.out.println((float) i / 100);
                dataBinding.blackHole.setSx( i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        dataBinding.seekBarY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                System.out.println((float) i / 100);
                dataBinding.blackHole.setDegree((double) -i / 720);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        dataBinding.tv.setOnClickListener(v -> {
//            ViewCompat.offsetTopAndBottom(v, 5);
//        });
    }
}
