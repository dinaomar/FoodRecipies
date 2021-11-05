package com.dina.elcg.myreciepes.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dina.elcg.myreciepes.model.FoodRecipe
import com.dina.elcg.myreciepes.util.Constants.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class RecipesEntity(var foodRecipe: FoodRecipe) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}