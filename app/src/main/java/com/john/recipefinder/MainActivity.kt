package com.john.recipefinder

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.john.recipefinder.Model.Meal
import com.john.recipefinder.RetrofitInstance.RetrofitInstance
import com.john.recipefinder.RetrofitInterface.RetrofitInterface
import com.john.recipefinder.databinding.ActivityMainBinding
import com.saksham.customloadingdialog.hideDialog
import com.saksham.customloadingdialog.showDialog
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding : ActivityMainBinding
    lateinit var mealId : String
    lateinit var connectivityViewModel: ConnectivityViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val categories = resources.getStringArray(R.array.Categories)

        val spinnerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,categories)
        mainBinding.spinner.adapter = spinnerAdapter

        mainBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedItem = categories[position]
                when(selectedItem){

                    "Beef" ->{
                        val intent = Intent(this@MainActivity,CategoriesActivity::class.java)
                        intent.putExtra("category_name",selectedItem)
                        startActivity(intent)
                        mainBinding.spinner.setSelection(0)
                    }
                    "Breakfast" ->{
                        val intent = Intent(this@MainActivity,CategoriesActivity::class.java)
                        intent.putExtra("category_name",selectedItem)
                        startActivity(intent)
                        mainBinding.spinner.setSelection(0)
                    }
                    "Chicken" -> {
                        val intent = Intent(this@MainActivity,CategoriesActivity::class.java)
                        intent.putExtra("category_name",selectedItem)
                        startActivity(intent)
                        mainBinding.spinner.setSelection(0)
                    }
                    "Dessert" -> {
                        val intent = Intent(this@MainActivity,CategoriesActivity::class.java)
                        intent.putExtra("category_name",selectedItem)
                        startActivity(intent)
                        mainBinding.spinner.setSelection(0)
                    }
                    "Vegan" -> {
                        val intent = Intent(this@MainActivity,CategoriesActivity::class.java)
                        intent.putExtra("category_name",selectedItem)
                        startActivity(intent)
                        mainBinding.spinner.setSelection(0)
                    }

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        showDialog(this,false,R.raw.chef_animation)

        //creating the retrofit api interface
        val apiInterface = RetrofitInstance.getRetrofitInstance().create(RetrofitInterface::class.java)

        // for checking internet connectivity
        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager

        // view model factory for connectivity view model
        val connectivityFactory = ConnectivityViewModelFactory(this)

        connectivityViewModel = ViewModelProvider(this,connectivityFactory).get(ConnectivityViewModel::class.java)

        connectivityViewModel.isConnected.observe(this, Observer { it->
            if(it){
                lifecycleScope.launch(Dispatchers.IO) {
                    val response = apiInterface.getRandomMeal()
                    if (response.isSuccessful) {
                        val meal = response.body()!!

                        // to get id of meal
                        mealId = meal.meals[0].get("idMeal").toString().removeSurrounding("\"")

                        val mealVideo = meal.meals[0].get("strYoutube").toString().removeSurrounding("\"")
                        val mealImage = meal.meals[0].get("strMealThumb").toString().removeSurrounding("\"")
                        val mealName = meal.meals[0].get("strMeal").toString().removeSurrounding("\"")
                        withContext(Dispatchers.Main) {
                            mainBinding.mealName.text = mealName

                            //make youtube link of meal clickable
                            mainBinding.youtubeLinkTextView.isClickable = true
                            mainBinding.youtubeLinkTextView.movementMethod =
                                LinkMovementMethod.getInstance()
                            val youtubeLink = "<a href='$mealVideo'> $mealName </a>"
                            mainBinding.youtubeLinkTextView.text =
                                Html.fromHtml(youtubeLink, Html.FROM_HTML_MODE_COMPACT)

                            Picasso.get().load(mealImage).into(mainBinding.randomMealImage)
                            hideDialog()
                            mainBinding.randomMealContentLayout.visibility = View.VISIBLE
                        }
                    }
                }
            }else{
                Toast.makeText(applicationContext,"Internet needed",Toast.LENGTH_SHORT).show()
            }
        })


        mainBinding.randomMealImage.setOnClickListener {

            val intent = Intent(this@MainActivity,DetailsActivity::class.java)
            intent.putExtra("mealId",mealId)
            startActivity(intent)

        }

        mainBinding.inputBody.setStartIconOnClickListener {
            val mainIngredient = mainBinding.SearchBar.text.toString()
            if(mainIngredient.isNotEmpty()){
                val intent = Intent(this@MainActivity,MainIngredientActivity::class.java)
                intent.putExtra("main_ingredient",mainIngredient)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext,"Field cannot be empty!!",Toast.LENGTH_LONG).show()
            }

        }

    }

}