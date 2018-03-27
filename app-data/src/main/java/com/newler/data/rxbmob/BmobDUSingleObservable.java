package com.newler.data.rxbmob;

import com.newler.data.Constants;
import com.orhanobut.logger.Logger;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 刺雒
 * @what
 * @date 2018/1/28
 */

public class BmobDUSingleObservable extends Observable<String> {
    private BmobObject mBmobObject;
    private @Constants.OperationStatus int status;

    public BmobDUSingleObservable(BmobObject bmobObject, int status) {
        mBmobObject = bmobObject;
        this.status = status;
    }

    @Override
    protected void subscribeActual(Observer<? super String> observer) {
        CallBack callBack = new CallBack(observer);

        switch (status) {
            case Constants.SINGLE_DELETE:
                mBmobObject.delete(callBack);
                break;

            case Constants.SINGLE_UPDATE:
                mBmobObject.update(callBack);
                break;
        }

        observer.onSubscribe(callBack);
    }

    private static final class CallBack extends UpdateListener implements Disposable {
        private  Observer mObserver;

        public CallBack(Observer observer) {
            mObserver = observer;
        }

        @Override
        public void done(BmobException e) {
            if (e == null) {
                mObserver.onNext("sss");
            } else {
                mObserver.onError(e);
            }
            mObserver.onComplete();
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
