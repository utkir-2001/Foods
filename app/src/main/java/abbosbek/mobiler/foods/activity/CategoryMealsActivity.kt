package abbosbek.mobiler.foods.activity

import abbosbek.mobiler.foods.R
import abbosbek.mobiler.foods.adapter.CategoryMealsAdapter
import abbosbek.mobiler.foods.databinding.ActivityCategoryMealsBinding
import abbosbek.mobiler.foods.fragments.HomeFragment
import abbosbek.mobiler.foods.pojo.MealsByCategory
import abbosbek.mobiler.foods.viewModel.CategoryMealsViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCategoryMealsBinding
    private lateinit var viewModel: CategoryMealsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        viewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME).toString())
        viewModel.observeMealsLiveData().observe(this){mealsList->
            binding.tvCategoryCount.text = mealsList.size.toString()
            binding.rvMeals.adapter = CategoryMealsAdapter(mealsList as ArrayList<MealsByCategory>)
        }

    }
}