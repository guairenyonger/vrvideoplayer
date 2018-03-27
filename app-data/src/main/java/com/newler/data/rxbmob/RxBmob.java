package com.newler.data.rxbmob;

import com.newler.data.Constants;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import io.reactivex.Observable;

/**
 * @author 刺雒
 * @what
 * @date 2018/1/27
 */

public class RxBmob<T extends BmobObject> {
    private static RxBmob sRxBmob;


    public static RxBmob getInstance() {
        if (sRxBmob == null) {
            synchronized (RxBmob.class) {
                if (sRxBmob == null) {
                    sRxBmob = new RxBmob();
                }
            }
        }
        return sRxBmob;
    }

    public Observable<BmobObject> saveSingle(BmobObject data) {
        return new BmobSaveSingleObservable(data);
    }

    public Observable<List<BmobObject>> updateList(List<BmobObject> bmobObjects) {
        return new BmobDUSListObservable(bmobObjects, Constants.LIST_UPDATE);
    }

    public Observable<List<BmobObject>> deleteList(List<BmobObject> bmobObjects) {
        return new BmobDUSListObservable(bmobObjects, Constants.LIST_DELETE);
    }

    public Observable<List<BmobObject>> saveList(List<BmobObject> bmobObjects) {
        return new BmobDUSListObservable(bmobObjects, Constants.LIST_SAVE);
    }

    public Observable<String> updateSingle(BmobObject bmobObject) {
        return new BmobDUSingleObservable(bmobObject, Constants.SINGLE_UPDATE);
    }

    public Observable<String> deleteSingle(BmobObject bmobObject) {
        return new BmobDUSingleObservable(bmobObject, Constants.SINGLE_DELETE);
    }

    public Observable<List<T>> queryList(BmobQuery<T> bmobQuery, Class<T> c) {
        return new BmobQueryListObservable<>(bmobQuery,c);
    }

    public Observable<Integer> queryCount(BmobQuery<T> bmobQuery, Class<T> c) {
        return new BmobCountObservable<>(bmobQuery, c);
    }

    public Observable<String[]> deleteFileList(String[] fileUrls) {
        return new BmobDeleteFileListObservable(fileUrls);
    }
}
