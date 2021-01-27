package com.ortizzakarie.dealfinder.di

import com.ortizzakarie.dealfinder.model.repository.CheapSharkRepository
import com.ortizzakarie.dealfinder.model.repository.CheapSharkRepositoryInterface
import com.ortizzakarie.dealfinder.model.repository.remote.api.CheapSharkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(CheapSharkApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun provideCheapSharkApi(retrofit: Retrofit): CheapSharkApi =
        retrofit.create(CheapSharkApi::class.java)


    @Provides
    @Singleton
    fun provideCheapSharkRepository(
        api: CheapSharkApi
    ) = CheapSharkRepository(api) as CheapSharkRepositoryInterface

}