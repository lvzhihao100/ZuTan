package com.eqdd.common.mvchelper;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by lvzhihao on 17-3-30.
 */

public class ModelRx2DataSource<T> extends MRx2DataSource<List<T>> {
    private int pageSize=20;
    private boolean isMore = false;
    public int mPage = 1;

    OnLoadSource onLoadSource;
    public ModelRx2DataSource(OnLoadSource onLoadSource,int pageSize) {
        this.pageSize=pageSize;
        this.onLoadSource=onLoadSource;
    }
    public ModelRx2DataSource(OnLoadSource onLoadSource) {
        this.onLoadSource=onLoadSource;
    }

    public interface OnLoadSource<T>{
        Flowable<List<T>> loadSourcce(final int page, DoneActionRegister<List<T>> register);
    }
    private Flowable load(final int page, DoneActionRegister<List<T>> register) throws Exception {

        return onLoadSource.loadSourcce(page,register)
                .flatMap(new Function<List<T>, Flowable<List<T>>>() {
                    @Override
                    public Flowable<List<T>> apply(@NonNull List<T> s) throws Exception {
                        if (s!=null&&s.size()==pageSize){
                           isMore=true;
                       }else {
                           isMore=false;
                       }
                       return Flowable.just(s);
                    }
                });
    }

    @Override
    public boolean hasMore() {
        return isMore;
    }

    @Override
    public Flowable<List<T>> refreshRXM(DoneActionRegister<List<T>> register) throws Exception {
        mPage = 1;
        return load(mPage, register);
    }

    @Override
    public Flowable<List<T>> loadMoreRXM(DoneActionRegister<List<T>> register) throws Exception {
        return load(++mPage, register);
    }

}