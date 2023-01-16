package abbosbek.mobiler.foods.viewModel

import abbosbek.mobiler.foods.db.MealDatabase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MealViewModelFactory(
    private val mealDatabase: MealDatabase
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
    }

}