package com.divyank.swipeandroidassignment.di

import com.divyank.swipeandroidassignment.data.repository.ProductRepository
import com.divyank.swipeandroidassignment.ui.viewmodels.ProductViewModel
import com.divyank.swipeandroidassignment.utils.RetrofitBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // RetrofitBuilder
    single { RetrofitBuilder }

    // ProductApiService
    single { get<RetrofitBuilder>().createService() }

    // Repository
    single { ProductRepository(get()) }

    // ViewModel
    viewModel { ProductViewModel(get()) }
}