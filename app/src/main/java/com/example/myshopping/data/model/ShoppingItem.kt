package com.example.myshopping.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "shopping")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id") @NotNull val item_id: Int,
    @ColumnInfo(name = "item_name") @NotNull var item_name: String,
    @ColumnInfo(name = "item_count")var item_count: Int?,
    @ColumnInfo(name = "item_compDate") var item_compDate: String?
)
