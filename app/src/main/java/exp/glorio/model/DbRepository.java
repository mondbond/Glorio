package exp.glorio.model;

import android.content.Context;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.DeleteQuery;

import java.util.ArrayList;

import exp.glorio.model.data.Category;
import exp.glorio.model.data.CategoryDao;
import exp.glorio.model.data.DaoMaster;
import exp.glorio.model.data.DaoSession;
import exp.glorio.model.data.Public;
import exp.glorio.model.data.PublicDao;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DbRepository {

    private Database mDb;
    private DaoMaster mDaoMaster;

    public DbRepository(Context context) {
        this.mDb = new DaoMaster.DevOpenHelper(context, "glorio-mDb", null).getWritableDb();
        mDaoMaster = new DaoMaster(mDb);
    }

    public Observable<ArrayList<Category>> loadAllCategories() {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Category>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Category>> subscriber) {

                DaoSession daoSession = mDaoMaster.newSession();
                CategoryDao categoryDao = daoSession.getCategoryDao();
                ArrayList<Category> res = (ArrayList<Category>) categoryDao.queryBuilder()
                        .list();

                subscriber.onNext(res);

        }}).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> deleteSelectedCategory(ArrayList<Long> deleteList) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                DaoSession daoSession = mDaoMaster.newSession();
                CategoryDao categoryDao = daoSession.getCategoryDao();
                for(Long id: deleteList) {
                    deleteAllPublicsOfCategory(id);
                    categoryDao.deleteByKey(id);
                }
                subscriber.onNext(true);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Boolean> deleteAllPublicsOfCategory(Long categoryId) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                DaoSession daoSession = mDaoMaster.newSession();
                PublicDao categoryDao = daoSession.getPublicDao();
                    DeleteQuery<Public> query = categoryDao.queryBuilder()
                            .where(PublicDao.Properties.CategoryId.eq(categoryId)).buildDelete();
                    query.executeDeleteWithoutDetachingEntities();
                subscriber.onNext(true);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> createNewCategory(String categoryName) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                DaoSession daoSession = mDaoMaster.newSession();
                CategoryDao categoryDao = daoSession.getCategoryDao();
                categoryDao.insert(new Category(null, categoryName, false));
                subscriber.onNext(true);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> savePublic(int pubId, long categoryId) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                DaoSession daoSession = mDaoMaster.newSession();
                daoSession.getPublicDao().insert(new Public(null, pubId, categoryId));
                subscriber.onNext(true);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ArrayList<Public>> getPublicsByCategoryId(Long categoryId) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Public>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Public>> subscriber) {
                DaoSession daoSession = mDaoMaster.newSession();
                ArrayList<Public> result = (ArrayList<Public>) daoSession.getPublicDao()
                        .queryBuilder().where(PublicDao.Properties.CategoryId.eq(categoryId)).list();
                subscriber.onNext(result);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> deleteSelectedPublics(ArrayList<Long> deleteList) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                DaoSession daoSession = mDaoMaster.newSession();
                PublicDao categoryDao = daoSession.getPublicDao();
                for(Long id: deleteList) {
                    DeleteQuery<Public> query = categoryDao.queryBuilder()
                            .where(PublicDao.Properties.PublicId.eq(id)).buildDelete();
                    query.executeDeleteWithoutDetachingEntities();
                }
                subscriber.onNext(true);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
