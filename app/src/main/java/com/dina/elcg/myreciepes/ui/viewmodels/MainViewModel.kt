package com.dina.elcg.myreciepes.ui.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.dina.elcg.myreciepes.data.Repository
import com.dina.elcg.myreciepes.data.database.RecipesEntity
import com.dina.elcg.myreciepes.model.FoodRecipe
import com.dina.elcg.myreciepes.util.InternetUtil
import com.dina.elcg.myreciepes.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readDatabase().asLiveData()

    fun getRecipes(queries: Map<String, String>) {
        viewModelScope.launch {
            getRecipesSafeCall(queries)
        }
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        if (InternetUtil.hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleRecipesResponse(response)

                val foodRecipes = recipesResponse.value!!.data
                if (foodRecipes != null)
                    offlineCacheRecipes(foodRecipes)
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("No recipes found!!")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection!!")
        }
    }

    private fun handleRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        when {
            response.message().toString()
                .contains("Timeout") -> return NetworkResult.Error("Timeout")
            response.code() == 402 -> return NetworkResult.Error("Api ket limited")
            response.code() == 401 -> return NetworkResult.Error("Not Authorized")
            response.body()!!.results.toString()
                .isEmpty() -> return NetworkResult.Error("No Recipes found")
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> return NetworkResult.Error(response.message())
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun insertRecipes(recipesEntity: RecipesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }
    }

}