package ru.weblokos.madbrains.UI.Frontend;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.weblokos.madbrains.DB.Entity.ProductEntity;
import ru.weblokos.madbrains.Model.Product;
import ru.weblokos.madbrains.R;
import ru.weblokos.madbrains.UI.Backend.BackendProductActivity;
import ru.weblokos.madbrains.UI.Backend.ProductClickCallback;
import ru.weblokos.madbrains.ViewModel.ProductBuyViewModel;
import ru.weblokos.madbrains.databinding.FrontendFragmentProductBinding;

public class FrontendProductFragment extends Fragment implements ProductBuyNavigator {

    private FrontendFragmentProductBinding binding;

    public static FrontendProductFragment newInstance(int productId) {
        FrontendProductFragment myFragment = new FrontendProductFragment();

        Bundle args = new Bundle();
        args.putInt("product", productId);
        myFragment.setArguments(args);

        return myFragment;
    }

    public FrontendProductFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frontend_fragment_product, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductBuyViewModel.Factory factory = new ProductBuyViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt("product"), this);
        final ProductBuyViewModel model = ViewModelProviders.of(this, factory)
                .get(ProductBuyViewModel.class);
        binding.setModel(model);
        subscribeToModel(model);
    }

    private void subscribeToModel(final ProductBuyViewModel model) {
        model.getObservableProduct().observe(this, new Observer<ProductEntity>() {
            @Override
            public void onChanged(@Nullable ProductEntity productEntity) {
                model.setProduct(productEntity);
            }
        });
    }

    private final ProductClickCallback mProductClickCallback = new ProductClickCallback() {
        @Override
        public void onClick(Product product) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(new Intent(getActivity(), BackendProductActivity.class).putExtra("product", product.getId()));
            }
        }
    };

    @Override
    public void onBuy() {

    }
}
