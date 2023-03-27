package com.example.myshopping.di

import androidx.room.Room
import com.example.myshopping.data.datasource.DataSource
import com.example.myshopping.data.repository.ShoppingRepository
import com.example.myshopping.room.ShoppingDao
import com.example.myshopping.room.ShoppingDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataSource(shoppingDao: ShoppingDao): DataSource{
        return DataSource(shoppingDao)
    }

    @Provides
    @Singleton
    fun provideShoppingRepository(dataSource: DataSource) : ShoppingRepository{
        return ShoppingRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideShoppingDao(@ApplicationContext context: Context): ShoppingDao {
        val db = Room.databaseBuilder(context, ShoppingDataBase::class.java, "shopping_db.sqlite")
            .createFromAsset("shopping_db.sqlite").allowMainThreadQueries().build()
        return db.getShoppingDao()
    }



}