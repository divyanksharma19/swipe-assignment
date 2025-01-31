package com.divyank.swipeandroidassignment.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divyank.swipeandroidassignment.data.models.Product
import com.divyank.swipeandroidassignment.data.repository.ProductRepository
import com.divyank.swipeandroidassignment.utils.UiState
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _productListState = MutableLiveData<UiState<List<Product>>>()
    val productListState: LiveData<UiState<List<Product>>> get() = _productListState

    private val _addProductResult = MutableLiveData<UiState<Boolean>>()
    val addProductResult: LiveData<UiState<Boolean>> get() = _addProductResult

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _productListState.value = UiState.Loading

            try {
                val products = repository.fetchProducts()
                _productListState.value = UiState.Success(products)
            } catch (e: Exception) {
                _productListState.value = UiState.Failure(e.message)
            }
        }
    }




    fun addProduct(product: Product, image: MultipartBody.Part?) {
        viewModelScope.launch {
            _addProductResult.value = UiState.Loading

            try {
                val success = repository.addProduct(product.product_name,product.product_type,product.tax.toString(),product.price.toString(), image)
                _addProductResult.value = UiState.Success(success != null)
            } catch (e: Exception) {
                _addProductResult.value = UiState.Failure(e.message)
            }
        }
    }
}
