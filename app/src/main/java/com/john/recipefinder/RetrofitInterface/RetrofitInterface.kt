package com.john.recipefinder.RetrofitInterface

import com.john.recipefinder.Model.CategoryMeals
import com.john.recipefinder.Model.Meal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("random.php")
    suspend fun getRandomMeal() : Response<Meal>

    @GET("lookup.php")
    suspend fun getMealDetailById(@Query("i") imageId : String) : Response<Meal>

    @GET("filter.php")
    suspend fun getMealsInCategory(@Query("c") category : String) : Response<CategoryMeals>

}