package abbosbek.mobiler.foods.adapter

import abbosbek.mobiler.foods.databinding.PopularItemsBinding
import abbosbek.mobiler.foods.pojo.MealsByCategory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

interface OnClickListener{
    fun onClick(meal:MealsByCategory)
    fun onLongClick(meal: MealsByCategory)
}
class MostPopularAdapter(
    private val mealsList: ArrayList<MealsByCategory>,
    private val listener: OnClickListener
) : RecyclerView.Adapter<MostPopularAdapter.PopularMealHolder>() {


    inner class PopularMealHolder(val binding: PopularItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealHolder {
        return PopularMealHolder(
            PopularItemsBinding
                .inflate(LayoutInflater
                    .from(parent.context),
                    parent,false
                )
        )
    }

    override fun onBindViewHolder(holder: PopularMealHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            listener.onClick(mealsList[position])
        }

        holder.itemView.setOnLongClickListener {
            listener.onLongClick(mealsList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

}