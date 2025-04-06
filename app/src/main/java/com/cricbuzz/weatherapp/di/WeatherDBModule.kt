package com.cricbuzz.weatherapp.di

import android.content.Context
import com.cricbuzz.weatherapp.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherDBModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return WeatherDatabase.getDatabase(context)
    }

//    @Provides
//    @Singleton
//    fun provideInventoryDao(appDatabase: WeatherDatabase): InventoryDao {
//        return appDatabase.inventoryDao()
//    }

}
