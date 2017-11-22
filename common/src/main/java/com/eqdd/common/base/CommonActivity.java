package com.eqdd.common.base;

import android.os.Bundle;

/**
 * Created by lvzhihao on 17-5-30.
 */

public abstract class CommonActivity extends BaseActivity {

    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
    }

    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

}
