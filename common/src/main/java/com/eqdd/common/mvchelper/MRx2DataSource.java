package com.eqdd.common.mvchelper;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by LuckyJayce on 2016/7/22.
 */
public abstract class MRx2DataSource<DATA> extends Rx2DataSource<DATA> {

    @Override
    public Flowable<DATA> refreshRX(DoneActionRegister<DATA> register) throws Exception {
        return load(refreshRXM(register));
    }

    @Override
    public Flowable<DATA> loadMoreRX(DoneActionRegister<DATA> register) throws Exception {
        return load(loadMoreRXM(register));
    }

    private Flowable<DATA> load(Flowable<DATA> observableAction) {
        return observableAction.flatMap(Flowable::just);
    }

    public abstract Flowable<DATA> refreshRXM(DoneActionRegister<DATA> register) throws Exception;

    public abstract Flowable<DATA> loadMoreRXM(DoneActionRegister<DATA> register) throws Exception;
}
