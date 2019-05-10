package com.example.user.fruitsapi.Presenter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.user.fruitsapi.R

class FruitInformation : AppCompatActivity() {

    private lateinit var infoFruit: TextView
    private lateinit var priceFruit: TextView
    private lateinit var weightFruit: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit_information)

        val intent = intent
        val info = intent.getStringExtra("type")
        val price = intent.getIntExtra("price", 0)
        val weight = intent.getIntExtra("weight", 0)


        infoFruit = findViewById(R.id.tv_info)
        infoFruit.text = info

        priceFruit = findViewById(R.id.tv_price)
        priceFruit.text = Integer.toString(price)

        weightFruit = findViewById(R.id.tv_weight)
        weightFruit.text = Integer.toString(weight)
    }
}
