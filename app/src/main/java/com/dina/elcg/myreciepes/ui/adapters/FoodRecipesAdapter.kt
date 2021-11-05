package com.dina.elcg.myreciepes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dina.elcg.myreciepes.databinding.RecipiesRecyclerItemBinding
import com.dina.elcg.myreciepes.model.FoodRecipe
import com.dina.elcg.myreciepes.model.Result

class FoodRecipesAdapter : RecyclerView.Adapter<FoodRecipesAdapter.MyViewHolder>() {

    private var recipes = emptyList<Result>()

    class MyViewHolder(private val binding: RecipiesRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Result) {
            binding.result = recipe
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipiesRecyclerItemBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(foodRecipe: FoodRecipe) {
        val diffUtil = RecipesDiffUtil(recipes, foodRecipe.results)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        recipes = foodRecipe.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class RecipesDiffUtil(
        private val oldResult: List<Result>,
        private val newResult: List<Result>
    ) : DiffUtil.Callback() {


        override fun getOldListSize(): Int {
            return oldResult.size
        }

        override fun getNewListSize(): Int {
            return newResult.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldResult[oldItemPosition] === newResult[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldResult[oldItemPosition] == newResult[newItemPosition]
        }
    }
}