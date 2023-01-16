package abbosbek.mobiler.foods.fragments

import abbosbek.mobiler.foods.R
import abbosbek.mobiler.foods.activity.CategoryMealsActivity
import abbosbek.mobiler.foods.activity.MainActivity
import abbosbek.mobiler.foods.adapter.CategoriesAdapter
import abbosbek.mobiler.foods.adapter.OnClickListener
import abbosbek.mobiler.foods.adapter.OnclickListener
import abbosbek.mobiler.foods.databinding.FragmentCategoryBinding
import abbosbek.mobiler.foods.pojo.Category
import abbosbek.mobiler.foods.viewModel.HomeViewModel
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class CategoryFragment : Fragment() {

    private var _binding : FragmentCategoryBinding ?= null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeCategories()
    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner){meals->
            binding.rvCategories.adapter = CategoriesAdapter(meals,object : OnclickListener{
                override fun onClick(meal: Category) {
                    val intent = Intent(requireActivity(), CategoryMealsActivity::class.java)
                    intent.putExtra(HomeFragment.CATEGORY_NAME,meal.strCategory)
                    startActivity(intent)
               }
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}