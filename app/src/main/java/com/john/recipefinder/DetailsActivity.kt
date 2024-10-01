package com.john.recipefinder

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.john.recipefinder.RetrofitInstance.RetrofitInstance
import com.john.recipefinder.RetrofitInterface.RetrofitInterface
import com.john.recipefinder.databinding.ActivityDetailsBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.saksham.customloadingdialog.showDialog
import com.saksham.customloadingdialog.hideDialog

class DetailsActivity : AppCompatActivity() {
    lateinit var detailsBinding: ActivityDetailsBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        detailsBinding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = detailsBinding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //create api interface
        val api = RetrofitInstance.getRetrofitInstance().create(RetrofitInterface::class.java)

        val mealId = intent.getStringExtra("mealId").toString()

        showDialog(this,false,R.raw.chef_animation)

        lifecycleScope.launch(Dispatchers.IO) {
            val response = api.getMealDetailById(mealId)
            if (response.isSuccessful){
                val meal = response.body()!!
                val mealImage = meal.meals[0].get("strMealThumb").toString().removeSurrounding("\"")
                val mealName = meal.meals[0].get("strMeal").toString().removeSurrounding("\"")

                // get meal instructions
                val mealInstructions = meal.meals[0].get("strInstructions").toString().removeSurrounding("\"").replace("\\n","\n").replace("\\r","\r").replace("\\t","\t")

                //get meal ingredients and measurements going to be a huge block

                //Ingredients block

                val ingredients1 = meal.meals[0].get("strIngredient1").toString().removeSurrounding("\"")
                val ingredients2 = meal.meals[0].get("strIngredient2").toString().removeSurrounding("\"")
                val ingredients3 = meal.meals[0].get("strIngredient3").toString().removeSurrounding("\"")
                val ingredients4 = meal.meals[0].get("strIngredient4").toString().removeSurrounding("\"")
                val ingredients5 = meal.meals[0].get("strIngredient5").toString().removeSurrounding("\"")
                val ingredients6 = meal.meals[0].get("strIngredient6").toString().removeSurrounding("\"")
                val ingredients7 = meal.meals[0].get("strIngredient7").toString().removeSurrounding("\"")
                val ingredients8 = meal.meals[0].get("strIngredient8").toString().removeSurrounding("\"")
                val ingredients9 = meal.meals[0].get("strIngredient9").toString().removeSurrounding("\"")
                val ingredients10 = meal.meals[0].get("strIngredient10").toString().removeSurrounding("\"")
                val ingredients11 = meal.meals[0].get("strIngredient11").toString().removeSurrounding("\"")
                val ingredients12 = meal.meals[0].get("strIngredient12").toString().removeSurrounding("\"")
                val ingredients13 = meal.meals[0].get("strIngredient13").toString().removeSurrounding("\"")
                val ingredients14 = meal.meals[0].get("strIngredient14").toString().removeSurrounding("\"")
                val ingredients15 = meal.meals[0].get("strIngredient15").toString().removeSurrounding("\"")
                val ingredients16 = meal.meals[0].get("strIngredient16").toString().removeSurrounding("\"")
                val ingredients17 = meal.meals[0].get("strIngredient17").toString().removeSurrounding("\"")
                val ingredients18 = meal.meals[0].get("strIngredient18").toString().removeSurrounding("\"")
                val ingredients19 = meal.meals[0].get("strIngredient19").toString().removeSurrounding("\"")
                val ingredients20 = meal.meals[0].get("strIngredient20").toString().removeSurrounding("\"")


                // block to get ingredient measurements
                val measurement1 = meal.meals[0].get("strMeasure1").toString().removeSurrounding("\"")
                val measurement2 = meal.meals[0].get("strMeasure2").toString().removeSurrounding("\"")
                val measurement3 = meal.meals[0].get("strMeasure3").toString().removeSurrounding("\"")
                val measurement4 = meal.meals[0].get("strMeasure4").toString().removeSurrounding("\"")
                val measurement5 = meal.meals[0].get("strMeasure5").toString().removeSurrounding("\"")
                val measurement6 = meal.meals[0].get("strMeasure6").toString().removeSurrounding("\"")
                val measurement7 = meal.meals[0].get("strMeasure7").toString().removeSurrounding("\"")
                val measurement8 = meal.meals[0].get("strMeasure8").toString().removeSurrounding("\"")
                val measurement9 = meal.meals[0].get("strMeasure9").toString().removeSurrounding("\"")
                val measurement10 = meal.meals[0].get("strMeasure10").toString().removeSurrounding("\"")
                val measurement11 = meal.meals[0].get("strMeasure11").toString().removeSurrounding("\"")
                val measurement12 = meal.meals[0].get("strMeasure12").toString().removeSurrounding("\"")
                val measurement13 = meal.meals[0].get("strMeasure13").toString().removeSurrounding("\"")
                val measurement14 = meal.meals[0].get("strMeasure14").toString().removeSurrounding("\"")
                val measurement15 = meal.meals[0].get("strMeasure15").toString().removeSurrounding("\"")
                val measurement16 = meal.meals[0].get("strMeasure16").toString().removeSurrounding("\"")
                val measurement17 = meal.meals[0].get("strMeasure17").toString().removeSurrounding("\"")
                val measurement18 = meal.meals[0].get("strMeasure18").toString().removeSurrounding("\"")
                val measurement19 = meal.meals[0].get("strMeasure19").toString().removeSurrounding("\"")
                val measurement20 = meal.meals[0].get("strMeasure20").toString().removeSurrounding("\"")

                val mealOrigin = meal.meals[0].get("strArea").toString().removeSurrounding("\"")
                val mealCategory = meal.meals[0].get("strCategory").toString().removeSurrounding("\"")

                withContext(Dispatchers.Main){
                    detailsBinding.textViewMealName.text = mealName
                    detailsBinding.textViewOrigin.text = "Origin: $mealOrigin"
                    detailsBinding.textViewCategory.text = "Category: $mealCategory"
                    Picasso.get().load(mealImage).into(detailsBinding.imageViewMeal)

                    // update the ui with ingredients and measurements
                    detailsBinding.textViewIngredient1.text = ingredients1
                    detailsBinding.textViewIngredient2.text = ingredients2
                    detailsBinding.textViewIngredient3.text = ingredients3
                    detailsBinding.textViewIngredient4.text = ingredients4
                    detailsBinding.textViewIngredient5.text = ingredients5
                    detailsBinding.textViewIngredient6.text = ingredients6
                    detailsBinding.textViewIngredient7.text = ingredients7
                    detailsBinding.textViewIngredient8.text = ingredients8
                    detailsBinding.textViewIngredient9.text = ingredients9
                    detailsBinding.textViewIngredient10.text = ingredients10
                    detailsBinding.textIngredient11.text = ingredients11
                    detailsBinding.textIngredient12.text = ingredients12
                    detailsBinding.textViewIngredient13.text = ingredients13
                    detailsBinding.textViewIngredient14.text = ingredients14
                    detailsBinding.textViewIngredient15.text = ingredients15
                    detailsBinding.textViewIngredient16.text = ingredients16
                    detailsBinding.textViewIngredient17.text = ingredients17
                    detailsBinding.textViewIngredient18.text = ingredients18
                    detailsBinding.textViewIngredient19.text = ingredients19
                    detailsBinding.textViewIngredient20.text = ingredients20

                    //update the ui with ingredient measurements

                    detailsBinding.textViewMeasurement1.text = measurement1
                    detailsBinding.textViewMeasurement2.text = measurement2
                    detailsBinding.textViewMeasurement3.text = measurement3
                    detailsBinding.textViewMeasurement4.text = measurement4
                    detailsBinding.textViewMeasurement5.text = measurement5
                    detailsBinding.textViewMeasurement6.text = measurement6
                    detailsBinding.textViewMeasurement7.text = measurement7
                    detailsBinding.textViewMeasurement8.text = measurement8
                    detailsBinding.textViewMeasurement9.text = measurement9
                    detailsBinding.textViewMeasurement10.text = measurement10
                    detailsBinding.textViewMeasurement11.text = measurement11
                    detailsBinding.textViewMeasurement12.text = measurement12
                    detailsBinding.textViewMeasurement13.text = measurement13
                    detailsBinding.textViewMeasurement14.text = measurement14
                    detailsBinding.textViewMeasurement15.text = measurement15
                    detailsBinding.textViewMeasurement16.text = measurement16
                    detailsBinding.textViewMeasurement17.text = measurement17
                    detailsBinding.textViewMeasurement18.text = measurement18
                    detailsBinding.textViewMeasurement19.text = measurement19
                    detailsBinding.textViewMeasurement20.text = measurement20

                    //update meal instructions ui
                    detailsBinding.textViewInstructions.text = mealInstructions

                    hideDialog()
                    detailsBinding.cardViewDescription.visibility = View.VISIBLE
                    detailsBinding.cardViewIngredients.visibility = View.VISIBLE
                    detailsBinding.cardViewInstructions.visibility = View.VISIBLE
                    detailsBinding.textViewInstruction.visibility = View.VISIBLE
                    detailsBinding.textViewIngredient.visibility = View.VISIBLE
                }

            }

        }

    }

}

