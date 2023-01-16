package abbosbek.mobiler.foods.db

import abbosbek.mobiler.foods.pojo.Meal
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals() : LiveData<List<Meal>>
}