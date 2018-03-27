package com.newler.data.rxbmob;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 刺雒
 * @what
 * @date 2018/1/29
 */

public class BmobCountObservable<T> extends Observable<Integer> {
    private BmobQuery<T> mBmobQuery;
    private Class<T> mClass;
    public BmobCountObservable(BmobQuery<T> bmobQuery, Class<T> c) {
        mBmobQuery = bmobQuery;
        this.mClass = c;
    }

    @Override
    protected void subscribeActual(Observer<? super Integer> observer) {
        Callback callback = new Callback(observer);
        observer.onSubscribe(callback);
        mBmobQuery.count(mClass, callback);

    }

    private final static class Callback extends CountListener implements Disposable {
        private Observer mObserver;

        public Callback(Observer observer) {
            mObserver = observer;
        }

        @Override
        public void done(Integer integer, BmobException e) {
            if (e == null) {
                mObserver.onNext(integer);
            } else {
                mObserver.onError(e);
            }
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
