package abbosbek.mobiler.foods.fragments

import abbosbek.mobiler.foods.R
import abbosbek.mobiler.foods.activity.MainActivity
import abbosbek.mobiler.foods.adapter.FavoriteMealAdapter
import abbosbek.mobiler.foods.databinding.FragmentFavoriteBinding
import abbosbek.mobiler.foods.viewModel.HomeViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding ?= null
    private val binding get() = _binding!!

    private lateinit var viewModel : HomeViewModel

    private lateinit var favoriteAdapter : FavoriteMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoriteAdapter.differ.currentList[position])

                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo"
                    ) {
                        viewModel.insertMeal(favoriteAdapter.differ.currentList[position])
                    }
                    .show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorite)
    }

    private fun prepareRecyclerView() = with(binding){

        favoriteAdapter = FavoriteMealAdapter()

        rvFavorite.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = favoriteAdapter
        }
    }

    private fun observeFavorites() {

        viewModel.observeFavoritesMealsLiveData().observe(requireActivity()){meals->
            favoriteAdapter.differ.submitList(meals)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}