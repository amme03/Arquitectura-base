package com.cosmo.arquitecturamvpbase.services;

import com.cosmo.arquitecturamvpbase.model.Product;

import java.util.ArrayList;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by leidyzulu on 16/09/17.
 */

public interface IServices {

    @GET("/products")
    ArrayList<Product> getProductList();

    @POST("/products")
    Product createProduct(@Body Product product);
}
