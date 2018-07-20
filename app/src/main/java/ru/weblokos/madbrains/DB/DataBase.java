package ru.weblokos.madbrains.DB;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import retrofit2.Call;
import retrofit2.Response;
import ru.weblokos.madbrains.App;
import ru.weblokos.madbrains.AppExecutors;
import ru.weblokos.madbrains.DB.DAO.ProductDao;
import ru.weblokos.madbrains.DB.Entity.ProductEntity;
import ru.weblokos.madbrains.Service.ProductService;
import ru.weblokos.madbrains.Service.ProductsList;

@Database(entities = {ProductEntity.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

    private static DataBase sInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    @VisibleForTesting
    public static final String DATABASE_NAME = "madbrains-shop-db";

    public abstract ProductDao productDao();

    public static DataBase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (DataBase.class) {
                if (sInstance == null) {
                    sInstance = create(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context);
                }
            }
        }
        return sInstance;
    }

    private static DataBase create(final Context context, final AppExecutors executors) {
        return Room.databaseBuilder(context.getApplicationContext(), DataBase.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                getInstance(context, executors).updateDatabaseCreated(context);
                (new ProductService())
                        .getAPI()
                        .getResults()
                        .enqueue(new retrofit2.Callback<ProductsList>() {
                            @Override
                            public void onResponse(Call<ProductsList> call, Response<ProductsList> response) {
                                if(response.body() != null) {
                                    executors.diskIO().execute(() -> { // заполнение базы данными
                                        ((App) context).getRepository().addProducts(response.body().products);
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<ProductsList> call, Throwable t) {
                            }
                        });
            }
        }).build();
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(Boolean.valueOf(true));
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
