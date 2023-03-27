package com.example.myshopping.data.repository

import com.example.myshopping.data.datasource.DataSource
import com.example.myshopping.data.model.ShoppingItem
import kotlinx.coroutines.flow.Flow


class ShoppingRepository(var dataSource : DataSource) {

    suspend fun getItems() : Flow<List<ShoppingItem>> = dataSource.getItems()
    suspend fun addItem(shoppingItem: ShoppingItem) = dataSource.addItem(shoppingItem)
    suspend fun updateItem(shoppingItem: ShoppingItem)=dataSource.updateItem(shoppingItem)
    suspend fun deleteItem(shoppingItem: ShoppingItem) = dataSource.deleteItem(shoppingItem)
    suspend fun getCompDate() : Flow<List<String>?> = dataSource.getCompDate()
    suspend fun deleteCompDate(compDate : String) = dataSource.deleteCompDate(compDate)
    suspend fun getCompleteItems(compDate: String) : Flow<List<ShoppingItem>> = dataSource.getCompleteItems(compDate)

}