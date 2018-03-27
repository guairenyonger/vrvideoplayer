package com.newler.data.rxbmob;


import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 刺雒
 * @what
 * @date 2018/1/28
 */

public class BmobQueryListObservable<T extends BmobObject> extends Observable<List<T>> {
    private BmobQuery<T> mBmobQuery;
    private Class<T> mTClass;

    public BmobQueryListObservable(BmobQuery<T> bmobQuery, Class<T> c) {
        mBmobQuery = bmobQuery;
        this.mTClass = c;
    }

    @Override
    protected void subscribeActual(final Observer<? super List<T>> observer) {
        Callback<T> callback = new Callback(observer);

        mBmobQuery.findObjectsObservable(mTClass).subscribe(new rx.Observer<List<T>>() {
            @Override
            public void onCompleted() {
                observer.onComplete();
            }

            @Override
            public void onError(Throwable throwable) {
                observer.onError(throwable);
            }

            @Override
            public void onNext(List<T> ts) {
                observer.onNext(ts);
            }
        });
        observer.onSubscribe(callback);
    }

    private  final class Callback<T>  implements Disposable {
        private Observer<? super List<T>> mObserver;

        public Callback(Observer<? super List<T>> observer) {
            mObserver = observer;
        }

        @Override
        public void dispose() {

        }

        @Override
        public boolean isDisposed() {
            return true;
        }
    }
}
