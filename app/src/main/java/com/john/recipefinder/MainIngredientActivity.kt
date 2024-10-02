package com.john.recipefinder

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.john.recipefinder.RetrofitInstance.RetrofitInstance
import com.john.recipefinder.RetrofitInterface.RetrofitInterface
import com.john.recipefinder.databinding.ActivityMainIngredientBinding
import com.saksham.customloadingdialog.hideDialog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MainIngredientActivity : AppCompatActivity() {

    lateinit var mainIngredientBinding: ActivityMainIngredientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainIngredientBinding = ActivityMainIngredientBinding.inflate(layoutInflater)
        val view = mainIngredientBinding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val api = RetrofitInstance.getRetrofitInstance().create(RetrofitInterface::class.java)

        val mainIngredient = intent.getStringExtra("main_ingredient").toString()

        com.saksham.customloadingdialog.showDialog(this, false, R.raw.chef_animation)

        mainIngredientBinding.textViewMainIngredientCategory.text = mainIngredient
        mainIngredientBinding.recyclerViewMainIngredient.layoutManager = LinearLayoutManager(this)



        val handler = CoroutineExceptionHandler{CoroutineContext,throwable ->
            println("caughtzzz exception $throwable")
        }

        lifecycleScope.launch(Dispatchers.IO + handler) {
            val response = api.getMealByMainIngredient(mainIngredient)
            if (response.isSuccessful){
                val categoryMeals = response.body()!!
                withContext(Dispatchers.Main){
                    val adapter = CategoryAdapter(categoryMeals,this@MainIngredientActivity)
                    mainIngredientBinding.recyclerViewMainIngredient.adapter = adapter
                    hideDialog()
                    mainIngredientBinding.recyclerViewMainIngredient.visibility = View.VISIBLE
                }
            }

        }

    }
}