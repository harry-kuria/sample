package com.example.gettingstartedwithjetpackcompose.ui.theme.accountsDashboard

import com.example.gettingstartedwithjetpackcompose.data.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AccountsModule {

    @Provides
    @Singleton
    fun provideAccountsAPI(): AccountsAPI {
        return ApiClient.accountsApi
    }
}