package abbosbek.mobiler.foods.viewModel

import abbosbek.mobiler.foods.pojo.MealsByCategory
import abbosbek.mobiler.foods.pojo.MealsByCategoryList
import abbosbek.mobiler.foods.retrofit.RetrofitInstance
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {

    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName : String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let {mealsList ->
                    mealsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("CategoryByMeals", "onFailure: ${ t.message.toString()}")
            }
        })
    }

    fun observeMealsLiveData() : LiveData<List<MealsByCategory>>{
        return mealsLiveData
    }
}