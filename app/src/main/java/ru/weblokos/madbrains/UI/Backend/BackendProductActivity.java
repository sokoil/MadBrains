package ru.weblokos.madbrains.UI.Backend;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ru.weblokos.madbrains.DB.Entity.ProductEntity;
import ru.weblokos.madbrains.R;
import ru.weblokos.madbrains.ViewModel.ProductViewModel;
import ru.weblokos.madbrains.databinding.BackendActivityProductBinding;

public class BackendProductActivity extends AppCompatActivity implements ProductNavigator {

    public static final String TAG = "CowDatamodel";
    BackendActivityProductBinding binding;
    private ProductViewModel model;
    int productId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.backend_activity_product);
        if(getIntent().getExtras() != null)
            productId = getIntent().getExtras().getInt("product");
        ProductViewModel.Factory factory = new ProductViewModel.Factory(
                getApplication(), productId, this);
        model = ViewModelProviders.of(this, factory)
                .get(ProductViewModel.class);
        binding.setModel(model);
        subScriptUi();
    }

    private void subScriptUi() {
        if(productId != 0) {
            model.getObservableProduct().observe(this, new Observer<ProductEntity>() {
                @Override
                public void onChanged(@Nullable ProductEntity productEntity) {
                    if (productEntity != null) {
                        model.setProduct(productEntity);
                    }
                }
            });
        }
    }

    @Override
    public void onSave() {
        finish();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}