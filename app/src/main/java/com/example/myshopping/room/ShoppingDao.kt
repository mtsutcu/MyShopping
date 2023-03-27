package com.example.myshopping.room

import androidx.room.*
import com.example.myshopping.data.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {

    @Query("SELECT * FROM shopping WHERE item_compDate IS NULL")
    fun getItems(): Flow<List<ShoppingItem>>

    @Insert
    suspend fun addItem(shoppingItem: ShoppingItem)

    @Update
    suspend fun updateItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteItem(shoppingItem: ShoppingItem)

    @Query("SELECT item_compDate FROM shopping WHERE item_compDate IS NOT NULL ORDER BY item_compDate DESC")
    fun getCompDate(): Flow<List<String>?>

    @Query("DELETE FROM shopping WHERE item_compDate = :compDate")
    suspend fun deleteCompDate(compDate: String)

    @Query("SELECT * FROM shopping WHERE item_compDate = :compDate")
    fun getCompleteItems(compDate: String) : Flow<List<ShoppingItem>>
}