package abbosbek.mobiler.foods.fragments.bottomSheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import abbosbek.mobiler.foods.R
import abbosbek.mobiler.foods.activity.MainActivity
import abbosbek.mobiler.foods.activity.MealActivity
import abbosbek.mobiler.foods.databinding.FragmentBottomSheetBinding
import abbosbek.mobiler.foods.fragments.HomeFragment
import abbosbek.mobiler.foods.viewModel.HomeViewModel
import android.content.Intent
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "param1"

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: HomeViewModel

    private var mealId : String ?= null

    private var _binding : FragmentBottomSheetBinding ?= null
    private val  binding  get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetBinding.inflate(inflater,container,false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getMealById(it)
        }

        observeBottomSheetMeal()
        onBottomSheetClick()
    }

    private fun onBottomSheetClick() {

        binding.bottomSheet.setOnClickListener {
            val intent = Intent(requireActivity(), MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,mealIdName)
            intent.putExtra(HomeFragment.MEAL_NAME,mealName)
            intent.putExtra(HomeFragment.MEAL_THUMB,mealThumb)
            startActivity(intent)
        }
    }

    private var mealIdName : String ?= null
    private var mealName : String ?= null
    private var mealThumb : String ?= null

    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetLiveData().observe(viewLifecycleOwner){meal->
            Glide.with(this)
                .load(meal.strMealThumb)
                .into(binding.imgBottomSheet)
            binding.tvBottomSheetCategory.text  = meal.strCategory
            binding.tvBottomSheetArea.text = meal.strArea
            binding.tvBottomSheetMealName.text = meal.strMeal

            mealIdName = meal.idMeal
            mealName = meal.strMeal
            mealThumb = meal.strMealThumb
        }
    }

    companion object{
        @JvmStatic
        fun newInstance(param1:String)=
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID,param1)
                }
            }
    }

}