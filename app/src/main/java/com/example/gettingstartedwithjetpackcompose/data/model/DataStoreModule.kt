package com.example.gettingstartedwithjetpackcompose.data.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.gettingstartedwithjetpackcompose.UserAccountData
import com.example.gettingstartedwithjetpackcompose.data.model.UserAccountDataSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides //exposes the DataStore so you don't need to explicitly call provideUserAccountDataStore
    @Singleton
    fun provideUserAccountDataStore(
        @ApplicationContext context: Context
    ): DataStore<UserAccountData> {
        return DataStoreFactory.create(
            serializer = UserAccountDataSerializer,
            produceFile = { context.dataStoreFile("user_account.pb") }
        )
    }
}