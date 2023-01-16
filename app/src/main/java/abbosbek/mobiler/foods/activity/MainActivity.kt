package abbosbek.mobiler.foods.activity

import abbosbek.mobiler.foods.R
import abbosbek.mobiler.foods.databinding.ActivityMainBinding
import abbosbek.mobiler.foods.db.MealDatabase
import abbosbek.mobiler.foods.viewModel.HomeViewModel
import abbosbek.mobiler.foods.viewModel.HomeViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    val viewModel : HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = Navigation.findNavController(this, R.id.host_fragment)

        NavigationUI.setupWithNavController(binding.btmNav,navController)

    }
}