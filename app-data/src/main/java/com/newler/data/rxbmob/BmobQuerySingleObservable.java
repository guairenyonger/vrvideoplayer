package com.newler.data.rxbmob;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 刺雒
 * @what
 * @date 2018/1/28
 */

public class BmobQuerySingleObservable<T extends BmobObject> extends Observable<T> {
    private BmobQuery<T> mBmobQuery;

    public BmobQuerySingleObservable(BmobQuery<T> bmobQuery) {
        mBmobQuery = bmobQuery;
    }


    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        Callback<T> callback = new Callback<T>(observer);
        observer.onSubscribe(callback);
    //    mBmobQuery.getObjectByTable().
    }

    private static final class Callback<R> extends QueryListener<R> implements Disposable {
        private Observer<? super R> mObserver;

        public Callback(Observer<? super R> observer) {
            mObserver = observer;
        }

        @Override
        public void done(R r, BmobException e) {
            if (e == null) {

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
