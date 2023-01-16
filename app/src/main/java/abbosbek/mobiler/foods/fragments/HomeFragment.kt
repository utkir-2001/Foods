package abbosbek.mobiler.foods.fragments

import abbosbek.mobiler.foods.activity.CategoryMealsActivity
import abbosbek.mobiler.foods.activity.MainActivity
import abbosbek.mobiler.foods.activity.MealActivity
import abbosbek.mobiler.foods.adapter.CategoriesAdapter
import abbosbek.mobiler.foods.adapter.MostPopularAdapter
import abbosbek.mobiler.foods.adapter.OnClickListener
import abbosbek.mobiler.foods.adapter.OnclickListener
import abbosbek.mobiler.foods.databinding.FragmentHomeBinding
import abbosbek.mobiler.foods.fragments.bottomSheet.BottomSheetFragment
import abbosbek.mobiler.foods.pojo.Category
import abbosbek.mobiler.foods.pojo.MealsByCategory
import abbosbek.mobiler.foods.pojo.Meal
import abbosbek.mobiler.foods.pojo.MealList
import abbosbek.mobiler.foods.retrofit.RetrofitInstance
import abbosbek.mobiler.foods.viewModel.HomeViewModel
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding ?= null

    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    private lateinit var randomMeal : Meal

    companion object{
        const val MEAL_ID = "abbosbek.mobiler.foods.fragments.idMeal"
        const val MEAL_NAME = "abbosbek.mobiler.foods.fragments.nameMeal"
        const val MEAL_THUMB = "abbosbek.mobiler.foods.fragments.thumbMeal"
        const val CATEGORY_NAME = "abbosbek.mobiler.foods.fragments.categoryName"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularMeal()

        viewModel.getCategories()
        observeCategoriesLiveData()
    }

    private fun observeCategoriesLiveData() {

        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner){ categoryList->
            binding.recViewCategories.adapter = CategoriesAdapter(categoryList!!,object : OnclickListener{
                override fun onClick(meal: Category) {
                    val intent = Intent(requireActivity(),CategoryMealsActivity::class.java)
                    intent.putExtra(CATEGORY_NAME,meal.strCategory)
                    startActivity(intent)
                }
            })
        }

    }


    private fun observePopularMeal() {
        viewModel.observePopularMealLiveData().observe(viewLifecycleOwner){ mealList->
            binding.recViewMealsPopular.adapter = MostPopularAdapter(mealList!! as ArrayList<MealsByCategory>,object : OnClickListener{
                override fun onClick(meal: MealsByCategory) {
                    val intent = Intent(activity,MealActivity::class.java)
                    intent.putExtra(MEAL_ID,meal.idMeal)
                    intent.putExtra(MEAL_NAME,meal.strMeal)
                    intent.putExtra(MEAL_THUMB,meal.strMealThumb)
                    startActivity(intent)
                }

                override fun onLongClick(meal: MealsByCategory) {
                    val mealBottomSheetFragment = BottomSheetFragment.newInstance(meal.idMeal)
                    mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
                }
            })
        }
    }


    private fun observeRandomMeal() {

        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.randomMealImg)
            this.randomMeal = meal
        }

    }

    private fun onRandomMealClick() = with(binding){
        randomMealCard.setOnClickListener {
            val intent = Intent(requireActivity(), MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}