package com.dina.elcg.myreciepes.data

import com.dina.elcg.myreciepes.data.network.FoodRecipeApi
import com.dina.elcg.myreciepes.model.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val foodRecipeApi: FoodRecipeApi) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipeApi.getRecipes(queries)
    }

}