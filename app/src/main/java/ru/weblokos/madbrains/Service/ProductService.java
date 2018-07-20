package ru.weblokos.madbrains.Service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gravitas on 11.07.2018.
 */

public class ProductService {
    private Retrofit retrofit = null;

    public ProductAPI getAPI() {
        String BASE_URL = "https://gist.githubusercontent.com/alegch/c1241c81e042d83c78caf38fc525ca04/raw/493b1b3dee02f4b896bd81096bdecaf5931c0332/";

        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ProductAPI.class);
    }
}
