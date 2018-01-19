package com.eqdd.common.mvchelper;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by LuckyJayce on 2016/7/21.
 */
public abstract class Rx2DataSource<DATA> implements IAsyncDataSource<DATA> {

    @Override
    public final RequestHandle refresh(final ResponseSender<DATA> sender) throws Exception {
        DoneActionRegister<DATA> register = new DoneActionRegister<>();
        return load(sender, refreshRX(register), register);
    }

    @Override
    public final RequestHandle loadMore(ResponseSender<DATA> sender) throws Exception {
        DoneActionRegister<DATA> register = new DoneActionRegister<>();
        return load(sender, loadMoreRX(register), register);
    }

    private RequestHandle load(final ResponseSender<DATA> sender, final Flowable<DATA> flowable, final DoneActionRegister<DATA> register) {
        DisposableSubscriber<DATA> subscriber = new DisposableSubscriber<DATA>() {
            @Override
            public void onNext(DATA data) {
                for (Consumer<DATA> subscriber : register.subscribers) {
                    try {
                        subscriber.accept(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                sender.sendData(data);
            }

            @Override
            public void onError(Throwable e) {
               System.out.println("Rx2DataSource->onError");
                sender.sendError(new Exception(e));
            }

            @Override
            public void onComplete() {

            }
        };
        flowable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        return new RequestHandle() {
            @Override
            public void cancle() {
                    if (!subscriber.isDisposed()){
                        subscriber.dispose();
                    }
            }

            @Override
            public boolean isRunning() {
                return false;
            }
        };
    }

    public abstract Flowable<DATA> refreshRX(DoneActionRegister<DATA> register) throws Exception;

    public abstract Flowable<DATA> loadMoreRX(DoneActionRegister<DATA> register) throws Exception;

    public static class DoneActionRegister<DATA> {
        private List<Consumer<DATA>> subscribers = new ArrayList<>();

        public void addAction(final Consumer<DATA> doneAction) {
            subscribers.add(doneAction);
        }
    }

}
