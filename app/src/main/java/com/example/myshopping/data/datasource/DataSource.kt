package com.example.myshopping.data.datasource

import com.example.myshopping.data.model.ShoppingItem
import com.example.myshopping.room.ShoppingDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class DataSource(var shoppingDao: ShoppingDao)  {


     suspend fun getItems(): Flow<List<ShoppingItem>> = withContext(Dispatchers.IO) {

        shoppingDao.getItems()
    }

    suspend fun addItem(shoppingItem: ShoppingItem) = withContext(Dispatchers.IO){
        shoppingDao.addItem(shoppingItem)
    }

    suspend fun updateItem(shoppingItem: ShoppingItem)= withContext(Dispatchers.IO){
        shoppingDao.updateItem(shoppingItem)
    }

    suspend fun deleteItem(shoppingItem: ShoppingItem)= withContext(Dispatchers.IO){
        shoppingDao.deleteItem(shoppingItem)
    }

    suspend fun getCompDate() : Flow<List<String>?> = withContext(Dispatchers.IO){
        shoppingDao.getCompDate()
    }

    suspend fun deleteCompDate(compDate : String) = withContext(Dispatchers.IO){
        shoppingDao.deleteCompDate(compDate)
    }

    suspend fun getCompleteItems(compDate: String) : Flow<List<ShoppingItem>> = withContext(Dispatchers.IO){
        shoppingDao.getCompleteItems(compDate)
    }


}