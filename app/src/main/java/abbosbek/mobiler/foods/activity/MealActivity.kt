package abbosbek.mobiler.foods.activity

import abbosbek.mobiler.foods.R
import abbosbek.mobiler.foods.databinding.ActivityMealBinding
import abbosbek.mobiler.foods.db.MealDatabase
import abbosbek.mobiler.foods.fragments.HomeFragment
import abbosbek.mobiler.foods.pojo.Meal
import abbosbek.mobiler.foods.viewModel.MealViewModel
import abbosbek.mobiler.foods.viewModel.MealViewModelFactory
import android.content.Intent
import android.content.res.Resources.Theme
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

class MealActivity : AppCompatActivity() {

    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var youTubeLink : String

    private lateinit var binding : ActivityMealBinding

    private lateinit var mealViewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealViewModel = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

        loadingCase()
        getMealInformationIntent()
        setInformationInViews()

        mealViewModel.getMealDetail(mealId)
        observeMealDetailsLiveData()
        onYouTubeClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() = with(binding) {

        btnAddToFav.setOnClickListener {
            mealToSave?.let {
                mealViewModel.insertMeal(it)
                Toast.makeText(this@MealActivity, "Meal Saved", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun onYouTubeClick() = with(binding) {

        imgYouTube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubeLink))
            startActivity(intent)
        }

    }

    private var mealToSave : Meal ?= null
    private fun observeMealDetailsLiveData() = with(binding) {
        mealViewModel.observeMealDetailLiveData().observe(this@MealActivity){meal->

            responseCase()

            mealToSave = meal

            tvCategory.text = "Category : ${meal!!.strCategory}"
            tvArea.text = "Area : ${meal!!.strArea}"
            tvDesc.text = meal.strInstructions
            youTubeLink = meal.strYoutube!!
        }
    }

    private fun loadingCase() = with(binding){

        progressBar.isVisible = true
        btnAddToFav.isVisible = false
        tvIntroduction.isVisible = false
        tvCategory.isVisible = false
        tvArea.isVisible = false
        imgYouTube.isVisible = false
    }
    private fun responseCase() = with(binding){

        progressBar.isVisible = false
        btnAddToFav.isVisible = true
        tvIntroduction.isVisible = true
        tvCategory.isVisible = true
        tvArea.isVisible = true
        imgYouTube.isVisible = true
    }

    private fun setInformationInViews() = with(binding) {

        Glide.with(this@MealActivity)
            .load(mealThumb)
            .into(imgMealDetail)

        collapsingToolbar.title = mealName
        collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this@MealActivity,R.color.white))

        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this@MealActivity,R.color.white))
    }

    private fun getMealInformationIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }
}