package com.cosmo.arquitecturamvpbase;

import com.cosmo.arquitecturamvpbase.helper.IValidateInternet;
import com.cosmo.arquitecturamvpbase.model.DeleteResponse;
import com.cosmo.arquitecturamvpbase.presenter.DetailProductPresenter;
import com.cosmo.arquitecturamvpbase.repository.IProductRepository;
import com.cosmo.arquitecturamvpbase.views.activities.IDetailProductView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import retrofit.RetrofitError;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by leidyzulu on 23/09/17.
 */


@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {



    @Mock
    IValidateInternet validateInternet;

    @Mock
    IProductRepository productRepository;

    @Mock
    IDetailProductView detailProductView;

    DetailProductPresenter detailProductPresenter;


    @Before
    public void setUp() throws Exception{
        detailProductPresenter = Mockito.spy(new DetailProductPresenter(productRepository));
        detailProductPresenter.inject(detailProductView, validateInternet);
    }


    @Test
    public void methodDeleteProductWithConnectionShouldCallMethodCreateThreadDeleteProduct(){
        String id = "13g1jhhd232";
        when(validateInternet.isConnected()).thenReturn(true);
        detailProductPresenter.deleteProduct(id);
        verify(detailProductPresenter).createThreadDeleteProduct(id);
        verify(detailProductView, never()).showAlertDialog(R.string.validate_internet);
    }


    @Test
    public void methodDeleteProductWithoutConnectionShouldShowAlertDialog(){
        String id = "13g1jhhd232";
        when(validateInternet.isConnected()).thenReturn(false);
        detailProductPresenter.deleteProduct(id);
        verify(detailProductView).showAlertDialog(R.string.validate_internet);
        verify(detailProductPresenter, never()).createThreadDeleteProduct(id);

    }

    @Test
    public void methodDeleteProductShouldCallMethodDeleteProductInRepositoryTrue(){
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setStatus(true);
        String id = "13g1jhhd232";
        when(productRepository.deleteProduct(id)).thenReturn(deleteResponse);
        detailProductPresenter.deleteProductRepository(id);
        Assert.assertTrue(deleteResponse.isStatus());
        verify(detailProductView).showToast(R.string.correct);
        verify(detailProductView, never()).showAlertDialogError(R.string.error);

    }

    @Test
    public void methodDeleteProductShouldCallMethodDeleteProductInRepositoryFalse(){
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setStatus(false);
        String id = "13g1jhhd232";
        when(productRepository.deleteProduct(id)).thenReturn(deleteResponse);
        detailProductPresenter.deleteProductRepository(id);
        Assert.assertFalse(deleteResponse.isStatus());
        verify(detailProductView).showAlertDialogError(R.string.error);
        verify(detailProductView, never()).showToast(R.string.correct);

    }

    @Test
    public void methodCreateThreadShouldShowProgressDialog(){
        String id = "13g1jhhd232";
        detailProductPresenter.createThreadDeleteProduct(id);
        verify(detailProductView).showProgress(R.string.loading_message);
    }


}
