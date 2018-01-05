package com.eqdd.library.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by lvzhihao on 17-5-31.
 */
@Route(path = "/libraryservice/application")
public class LibraryService implements IProvider {
    @Override
    public void init(Context context) {

    }

}
