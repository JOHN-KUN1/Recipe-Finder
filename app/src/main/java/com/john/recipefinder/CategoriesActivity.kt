package com.john.recipefinder

import android.graphics.DiscretePathEffect
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.john.recipefinder.RetrofitInstance.RetrofitInstance
import com.john.recipefinder.RetrofitInterface.RetrofitInterface
import com.john.recipefinder.databinding.ActivityCategoriesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.saksham.customloadingdialog.showDialog
import com.saksham.customloadingdialog.hideDialog

class CategoriesActivity : AppCompatActivity() {

    lateinit var categoryBinding: ActivityCategoriesBinding
    lateinit var connectivityViewModel: ConnectivityViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        categoryBinding = ActivityCategoriesBinding.inflate(layoutInflater)
        val view = categoryBinding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //create api interface
        val api = RetrofitInstance.getRetrofitInstance().create(RetrofitInterface::class.java)

        val mealCategory = intent.getStringExtra("category_name").toString()

        showDialog(this,false,R.raw.chef_animation)

        categoryBinding.textViewSelectedCategory.text = mealCategory
        categoryBinding.recyclerView.layoutManager = LinearLayoutManager(this)

        //view model for internet connectivity
        val connectivityViewModelFactory = ConnectivityViewModelFactory(this)
        connectivityViewModel = ViewModelProvider(this,connectivityViewModelFactory).get(ConnectivityViewModel::class.java)
        connectivityViewModel.isConnected.observe(this, Observer {
            if (it){
                lifecycleScope.launch(Dispatchers.IO) {
                    val response = api.getMealsInCategory(mealCategory)
                    if (response.isSuccessful){
                        val categoryMeals = response.body()!!

                        withContext(Dispatchers.Main){
                            val adapter = CategoryAdapter(categoryMeals,this@CategoriesActivity)
                            categoryBinding.recyclerView.adapter = adapter
                            hideDialog()
                            categoryBinding.recyclerView.visibility = View.VISIBLE
                        }

                    }

                }
            }else{
                Toast.makeText(applicationContext,"Internet Access Required",Toast.LENGTH_LONG).show()
            }
        })


    }
}