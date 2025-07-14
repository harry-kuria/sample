package com.example.gettingstartedwithjetpackcompose

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.gettingstartedwithjetpackcompose.UserAccountData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


///////NOT USED NOW BECAUSE OF DATASTORE (I think)
//object UserAccountDataStore {
//    private lateinit var dataStore: DataStore<UserAccountData> //to be initialized later
//    fun initializeDataStore(context: Context) {
//        dataStore = DataStoreFactory.create( //create() is used to create the datastore
//            serializer = UserAccountDataSerializer,
//            //serializer basically defines how to read and write the data using what was defined in UserAccountDataSerializer
//            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
//            //allows the datastore to work in the background and SupervisorJob() basically allows different read/write operations to fail without crashing the rest
//
//        ) {
//            context.dataStoreFile("user_account_data.pb")
//            //gives the name and therefore the location of the datastore where to store the data
//        }
//    }
//
//    fun getDataStore(): DataStore<UserAccountData> {
//        if (!::dataStore.isInitialized) {
//            //::allows for referencing of the variable of function itself and not the value stored in it
//            //so the line is basically checking if the variable has been initialized
//            throw IOException("UserAccountDataStore has not been initialized yet.Call initializeDataStore(context) first.")
//            //if not then throw an exception
//        }
//        return dataStore
//        //if its been initialized then return the datastore
//    }
//}

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
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
