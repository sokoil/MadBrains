package ru.weblokos.madbrains.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import ru.weblokos.madbrains.App;
import ru.weblokos.madbrains.DB.Entity.ProductEntity;
import ru.weblokos.madbrains.UI.Frontend.ProductBuyNavigator;

public class ProductBuyViewModel extends AndroidViewModel {

    public LiveData<ProductEntity> mObservableProduct;
    public ObservableField<ProductEntity> product = new ObservableField<>();

    private final int mProductId;
    private final ProductBuyNavigator mListener;
    private final Application application;


    public ProductBuyViewModel(Application application,
                               int productId,
                               ProductBuyNavigator listener) {
        super(application);
        this.application = application;
        this.mListener = listener;
        this.mProductId = productId;
        if(mProductId == 0) {
            product.set(new ProductEntity());
        } else {
            mObservableProduct = (((App)application).getRepository().getProduct(mProductId));
        }
    }

    public void setProduct(ProductEntity product) {
        this.product.set(product);
    }

    public void buyProduct() {
        product.get().buyProduct(1);
        ((App) application).getExecutors().diskIO().execute(() -> {
            ((App)application).getRepository().addProduct(product.get());
        });
        mListener.onBuy();
    }

    public LiveData<ProductEntity> getObservableProduct() {
        return mObservableProduct;
    }

    @BindingAdapter("app:onClick")
    public static void bindOnClick(View view, final Runnable runnable) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runnable.run();
            }
        });
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final ProductBuyNavigator mNavigator;

        private final int mProductId;

        public Factory(@NonNull Application application, int productId, ProductBuyNavigator navigator) {
            mApplication = application;
            mProductId = productId;
            mNavigator = navigator;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ProductBuyViewModel(mApplication, mProductId, mNavigator);
        }
    }
}
