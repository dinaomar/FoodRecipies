package com.dina.elcg.myreciepes.ui.fragments.recipeis.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.dina.elcg.myreciepes.R
import com.dina.elcg.myreciepes.databinding.FragmentBottomSheetBinding
import com.dina.elcg.myreciepes.ui.viewmodels.RecipesViewModel
import com.dina.elcg.myreciepes.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.dina.elcg.myreciepes.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.lang.Exception
import java.util.*


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var recipesViewModel: RecipesViewModel
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!


    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mealTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            mealTypeChipId = checkedId
            val chip = group.findViewById<Chip>(checkedId)
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            mealTypeChip = selectedMealType
        }

        binding.dietTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            dietTypeChipId = checkedId
            val chip = group.findViewById<Chip>(checkedId)
            dietTypeChip = chip.text.toString().lowercase(Locale.ROOT)
        }

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner,
            {
                mealTypeChip = it.selectedMealType
                dietTypeChip = it.selectedDietType
                updateChip(it.selectedDietTypeId, binding.dietTypeChipGroup)
                updateChip(it.selectedMealTypeId, binding.mealTypeChipGroup)
            })

        binding.applyBtn.setOnClickListener {
            recipesViewModel.saveMealAndDietType(mealTypeChip,mealTypeChipId,dietTypeChip,dietTypeChipId)
            val action = BottomSheetFragmentDirections.actionBottomSheetFragment2ToRecipesFragment(true)
            findNavController().navigate(action)
        }
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}