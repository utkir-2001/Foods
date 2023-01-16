package abbosbek.mobiler.foods.adapter

import abbosbek.mobiler.foods.databinding.CategoryItemBinding
import abbosbek.mobiler.foods.pojo.Category
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

interface OnclickListener{
    fun onClick(meal : Category)
}

class CategoriesAdapter(
    private val items : List<Category>,
    private val listener: OnclickListener
) : RecyclerView.Adapter<CategoriesAdapter.ItemHolder>() {
    inner class ItemHolder(val binding : CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(items[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryName.text = items[position].strCategory
        holder.itemView.setOnClickListener {
            listener.onClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}