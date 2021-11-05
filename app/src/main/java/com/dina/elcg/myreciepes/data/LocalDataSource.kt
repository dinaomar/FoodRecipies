package com.dina.elcg.myreciepes.data

import com.dina.elcg.myreciepes.data.database.FoodRecipeDao
import com.dina.elcg.myreciepes.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: FoodRecipeDao) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity) =
        recipesDao.insertRecipes(recipesEntity)


    fun readDatabase(): Flow<List<RecipesEntity>> = recipesDao.readRecipes()
}