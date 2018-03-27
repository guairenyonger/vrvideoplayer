package com.newler.data.rxbmob;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 刺雒
 * @what
 * @date 2018/1/27
 */

public class BmobSaveSingleObservable extends Observable<BmobObject> {
    private BmobObject mBmobObject;
    public BmobSaveSingleObservable(BmobObject bmobObject) {
        this.mBmobObject = bmobObject;
    }

    @Override
    protected void subscribeActual(Observer<? super BmobObject> observer) {
        CallCallback callCallback = new CallCallback(observer, mBmobObject);
        observer. onSubscribe(callCallback);
        mBmobObject.save(callCallback);
    }


    private static final class CallCallback extends SaveListener<String> implements Disposable {
            private Observer<? super BmobObject> mObserver;
            private BmobObject mBmobObject;
            public CallCallback(Observer<? super BmobObject> observer, BmobObject bmobObject) {
                this.mObserver = observer;
                this.mBmobObject = bmobObject;
            }


            @Override
            public void dispose() {

            }

            @Override
            public boolean isDisposed() {
                return true;
            }

            @Override
            public void done(String  t, BmobException e) {
                if (e == null) {
                    mBmobObject.setObjectId(t);
                    mObserver.onNext(mBmobObject);
                } else {
                    mObserver.onError(e);
                }

                mObserver.onComplete();
            }
        }
}
