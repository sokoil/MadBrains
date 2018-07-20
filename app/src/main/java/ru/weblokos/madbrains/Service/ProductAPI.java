package ru.weblokos.madbrains.Service;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gravitas on 11.07.2018.
 */

public interface ProductAPI {
    /*@GET("products.json")
    Call<List<Product>> getResults();*/
    @GET("products.json")
    Call<ProductsList> getResults();
}
