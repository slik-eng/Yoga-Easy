package com.slikeng.yogaeasytest.di

import com.slikeng.yogaeasytest.MainActivityInteractor
import com.slikeng.yogaeasytest.MainActivityViewModel
import com.slikeng.yogaeasytest.network.MainApi
import com.slikeng.yogaeasytest.network.ResponseList
import com.slikeng.yogaeasytest.util.Constants
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

internal val mainActivityKoinModule = module {

    factory {
        MainActivityInteractor(
             mainApi = get()
        )
    }

    factory {
        MainActivityViewModel(
            interactor = get()
        )
    }

    single {
        Retrofit.Builder()
            .baseUrl((Constants.BASE_URL).plus(Constants.VERSION_NUMBER))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(MainApi::class.java)
    }

}