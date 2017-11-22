package com.eqdd.common.utils;

import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by vzhihao on 2017/3/2.
 */
public class RxBus {

    private static RxBus rxBus;
    private final Subject<Events<?>, Events<?>> _bus = new SerializedSubject<>(PublishSubject.<Events<?>>create());

    private RxBus() {
    }

    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    public void send(Events<?> o) {
        _bus.onNext(o);
    }

    public void send(@Events.EventCode int code, Object content) {
        Events<Object> event = new Events<>();
        event.code = code;
        event.content = content;
        send(event);
    }

    public Observable<Events<?>> toObservable() {
        return _bus;
    }

    public static SubscriberBuilder with(RxFragment provider) {
        return new SubscriberBuilder(provider);
    }

    public static SubscriberBuilder with(RxAppCompatActivity provider) {
        return new SubscriberBuilder(provider);
    }


    public static class SubscriberBuilder {

        private RxFragment mFragLifecycleProvider;
        private RxAppCompatActivity mActLifecycleProvider;
        private FragmentEvent mFragmentEndEvent;
        private ActivityEvent mActivityEndEvent;
        private int event;
        private Action1<? super Events<?>> onNext;
        private Action1<Throwable> onError;

        public SubscriberBuilder(RxFragment provider) {
            this.mFragLifecycleProvider = provider;
        }

        public SubscriberBuilder(RxAppCompatActivity provider) {
            this.mActLifecycleProvider = provider;
        }

        public SubscriberBuilder setEvent(@Events.EventCode int event) {
            this.event = event;
            return this;
        }

        public SubscriberBuilder setEndEvent(FragmentEvent event) {
            this.mFragmentEndEvent = event;
            return this;
        }

        public SubscriberBuilder setEndEvent(ActivityEvent event) {
            this.mActivityEndEvent = event;
            return this;
        }

        public SubscriberBuilder onNext(Action1<? super Events<?>> action) {
            this.onNext = action;
            return this;
        }

        public SubscriberBuilder onError(Action1<Throwable> action) {
            this.onError = action;
            return this;
        }


        public void create() {
            _create();
        }

        public Subscription _create() {
            if (mFragLifecycleProvider != null) {
                return RxBus.getInstance().toObservable()
                        .compose(mFragmentEndEvent == null ? mFragLifecycleProvider.bindToLifecycle() : mFragLifecycleProvider.<Events<?>>bindUntilEvent(mFragmentEndEvent)) // 绑定生命周期
                        .filter(new Func1<Events<?>, Boolean>() {
                            @Override
                            public Boolean call(Events<?> events) {
                                return events.code == event;
                            }
                        })   //过滤 根据code判断返回事件
                        .subscribe(onNext, onError == null ? new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        } : onError);
            }
            if (mActLifecycleProvider != null) {
                return RxBus.getInstance().toObservable()
                        .compose(mActivityEndEvent == null ? mActLifecycleProvider.bindToLifecycle() : mActLifecycleProvider.<Events<?>>bindUntilEvent(mActivityEndEvent))
                        .filter(new Func1<Events<?>, Boolean>() {
                            @Override
                            public Boolean call(Events<?> events) {
                                return events.code == event;
                            }
                        })
                        .subscribe(onNext, onError == null ? (Action1<Throwable>) new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        } : onError);
            }
            return null;
        }
    }
}
