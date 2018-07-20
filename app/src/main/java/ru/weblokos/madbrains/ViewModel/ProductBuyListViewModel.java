package ru.weblokos.madbrains.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import ru.weblokos.madbrains.App;
import ru.weblokos.madbrains.DB.Entity.ProductEntity;
import ru.weblokos.madbrains.UI.Backend.ProductListNavigator;

public class ProductBuyListViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<ProductEntity>> mObservableProducts;
    private ProductListNavigator mNavigator;

    public ProductBuyListViewModel(Application application,
                                   ProductListNavigator navigator) {
        super(application);
        mNavigator = navigator;
        mObservableProducts = new MediatorLiveData<>();
        mObservableProducts.setValue(null);
        LiveData<List<ProductEntity>> products = ((App) application).getRepository().getAllProducts();
        mObservableProducts.addSource(products, mObservableProducts::setValue);
    }

    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;
        @Nullable
        private final ProductListNavigator mNavigator;

        public Factory(@NonNull Application application, ProductListNavigator navigator) {
            mApplication = application;
            mNavigator = navigator;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ProductBuyListViewModel(mApplication, mNavigator);
        }
    }
}
