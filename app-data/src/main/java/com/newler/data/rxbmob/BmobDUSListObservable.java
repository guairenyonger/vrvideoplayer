package com.newler.data.rxbmob;


import com.newler.data.Constants;

import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 刺雒
 * @what
 * @date 2018/1/27
 */

public class BmobDUSListObservable extends Observable<List<BmobObject>>{
    private List mBmobObjects;
    private @Constants.OperationStatus int mStatus;

    public BmobDUSListObservable(List bmobObjects, int status) {
        mBmobObjects = bmobObjects;
        mStatus = status;
    }

    @Override
        protected void subscribeActual(Observer<? super List<BmobObject>> observer) {
        CallCallback callback = new CallCallback(observer, mBmobObjects);
        BmobBatch bmobBatch = new BmobBatch();

        observer.onSubscribe(callback);
        switch (mStatus) {
            case Constants.LIST_SAVE:
                bmobBatch.insertBatch(mBmobObjects).doBatch(callback);
                break;

            case Constants.LIST_DELETE:
                bmobBatch.deleteBatch(mBmobObjects).doBatch(callback);
                break;

            case Constants.LIST_UPDATE:
                bmobBatch.updateBatch(mBmobObjects).doBatch(callback);
                break;
        }


    }

    private static final class CallCallback extends QueryListListener<BatchResult> implements Disposable {
        private Observer<? super List<BmobObject>> mObserver;
        private List<BmobObject> mBmobObjects;
        public CallCallback(Observer<? super List<BmobObject>> observer, List<BmobObject> bmobObjects) {
            this.mObserver = observer;
            this.mBmobObjects = bmobObjects;
        }


        @Override
        public void dispose() {

        }

        @Override
        public boolean isDisposed() {
            return true;
        }


        @Override
        public void done(List<BatchResult> list, BmobException e) {
            if (e == null) {
                int i = 0;
                for (BatchResult batchResult : list) {
                    if (batchResult.getError() != null) {
                        mObserver.onError(batchResult.getError());
                        mObserver.onComplete();
                        return;
                    } else {
                        mBmobObjects.get(i).setObjectId(batchResult.getObjectId());
                    }
                    i++;
                }
            } else {
               mObserver.onError(e);
               mObserver.onComplete();
               return;
            }

            mObserver.onNext(mBmobObjects);
            mObserver.onComplete();
        }
    }


}
