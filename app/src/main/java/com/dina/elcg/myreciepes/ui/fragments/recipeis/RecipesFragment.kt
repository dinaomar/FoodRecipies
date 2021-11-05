package com.dina.elcg.myreciepes.ui.fragments.recipeis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dina.elcg.myreciepes.R
import com.dina.elcg.myreciepes.ui.adapters.FoodRecipesAdapter
import com.dina.elcg.myreciepes.ui.viewmodels.MainViewModel
import com.dina.elcg.myreciepes.util.Constants.Companion.API_TOKEN
import com.dina.elcg.myreciepes.util.NetworkResult
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var recyclerView: ShimmerRecyclerView
    private val mAdapter: FoodRecipesAdapter by lazy { FoodRecipesAdapter() }
    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.let {
            it.adapter = mAdapter
            it.layoutManager = LinearLayoutManager(requireContext())
            it.showShimmer()
        }
        getDataFromApi()
    }

    fun getDataFromApi() {
        mainViewModel.getRecipes(makeQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is NetworkResult.Success -> {
                    recyclerView.hideShimmer()
                    result.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    recyclerView.hideShimmer()
                    Toast.makeText(requireContext(), result.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    recyclerView.showShimmer()
                }
            }
        })
    }

    fun makeQueries(): HashMap<String, String> {
        val map: HashMap<String, String> = HashMap()
        map["number"] = "50"
        map["apiKey"] = API_TOKEN
        map["type"] = "snack"
        map["diet"] = "vegan"
        map["addRecipeInformation"] = "true"
        map["fillIngredients"] = "true"

        return map
    }

}