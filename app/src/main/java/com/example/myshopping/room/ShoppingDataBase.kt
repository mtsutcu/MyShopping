package com.example.myshopping.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myshopping.data.model.ShoppingItem

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingDataBase : RoomDatabase(){
    abstract fun getShoppingDao() : ShoppingDao
}