package abbosbek.mobiler.foods.retrofit

import abbosbek.mobiler.foods.pojo.CategoryList
import abbosbek.mobiler.foods.pojo.MealsByCategoryList
import abbosbek.mobiler.foods.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal() : Call<MealList>

    @GET("lookup.php?")
    fun getMealDetail(@Query("i") id : String) : Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") category: String) : Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName : String) : Call<MealsByCategoryList>
}