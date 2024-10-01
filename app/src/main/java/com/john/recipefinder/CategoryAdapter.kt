package com.john.recipefinder

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.john.recipefinder.Model.CategoryMeals
import com.john.recipefinder.databinding.ItemDesignBinding
import com.squareup.picasso.Picasso

class CategoryAdapter(val categoryMeals: CategoryMeals,val context : Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(val itemDesignBinding: ItemDesignBinding) : RecyclerView.ViewHolder(itemDesignBinding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryMeals.meals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemDesignBinding.categoryMealName.text = categoryMeals.meals[position].strMeal
        Picasso.get().load(categoryMeals.meals[position].strMealThumb.removeSurrounding("\"")).into(holder.itemDesignBinding.categoryMealImage)

        holder.itemDesignBinding.itemDesignLayout.setOnClickListener {

            val mealId = categoryMeals.meals[position].idMeal.toString().removeSurrounding("\"")
            val intent = Intent(context,DetailsActivity::class.java)
            intent.putExtra("mealId",mealId)
            context.startActivity(intent)
        }

    }

}