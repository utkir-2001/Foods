package abbosbek.mobiler.foods.adapter

import abbosbek.mobiler.foods.databinding.MealItemBinding
import abbosbek.mobiler.foods.pojo.MealsByCategory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CategoryMealsAdapter(
    private val mealsList : ArrayList<MealsByCategory>
) : RecyclerView.Adapter<CategoryMealsAdapter.ItemHolder>() {
    inner class ItemHolder(val binding : MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = mealsList[position].strMeal

    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}