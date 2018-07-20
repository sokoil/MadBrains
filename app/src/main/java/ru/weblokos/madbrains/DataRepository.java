package ru.weblokos.madbrains;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import ru.weblokos.madbrains.DB.DataBase;
import ru.weblokos.madbrains.DB.Entity.ProductEntity;


public class DataRepository {
    private static DataRepository sInstance;

    private final DataBase mDatabase;
    private MediatorLiveData<List<ProductEntity>> mObservableProducts;

    private DataRepository(final DataBase database) {
        mDatabase = database;
        mObservableProducts = new MediatorLiveData<>();
        mObservableProducts.addSource(mDatabase.productDao().loadAllProducts(),
                productEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableProducts.postValue(productEntities);
                    }
                });
    }

    public static DataRepository getInstance(final DataBase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<ProductEntity> getProduct(int productId) {
        return mDatabase.productDao().loadProduct(productId);
    }

    public LiveData<List<ProductEntity>> getAllProducts() {
        return mObservableProducts;
    }

    public void updateProduct(final ProductEntity productEntity) {
        mDatabase.productDao().update(productEntity);
    }

    public void addProduct(ProductEntity productEntity) {
        mDatabase.productDao().insert(productEntity);
    }

    public void addProducts(List<ProductEntity> productEntities) {
        mDatabase.productDao().insertAll(productEntities);
    }
}
