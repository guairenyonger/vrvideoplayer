package com.newler.data.rxbmob;

import com.orhanobut.logger.Logger;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobReturn;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 刺雒
 * @what
 * @date 2018/2/4
 */

public class BmobDeleteFileListObservable extends Observable<String[]> {
    private String[] mFiles;

    public BmobDeleteFileListObservable(String[] files) {
        mFiles = files;
    }

    @Override
    protected void subscribeActual(final Observer<? super String[]> observer) {
        Callback callback = new Callback();
        BmobFile.deleteBatchObservable(mFiles)
                .subscribe(new rx.Observer<BmobReturn<String[]>>() {
                    @Override
                    public void onCompleted() {
                        observer.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onNext(BmobReturn<String[]> bmobReturn) {
                        observer.onNext(new String[1]);

                    }
                });
        observer.onSubscribe(callback);
    }

    private static final class Callback  implements Disposable {

        @Override
        public void dispose() {

        }

        @Override
        public boolean isDisposed() {
            return true;
        }
    }
}
