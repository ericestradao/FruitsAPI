package com.example.user.fruitsapi.Presenter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.example.user.fruitsapi.Model.Fruit
import com.example.user.fruitsapi.Model.FruitData
import com.example.user.fruitsapi.Model.FruitInterface
import com.example.user.fruitsapi.R
import com.example.user.fruitsapi.View.FruitAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import android.support.v4.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.item_fruit.view.*
import java.util.ArrayList


class MainActivity : AppCompatActivity(), FruitAdapter.Listener {
    private val TAG = MainActivity::class.java.simpleName

    private val BASE_URL = "https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/"

    private var mCompositeDisposable: CompositeDisposable? = null

    private var mFruitArrayList: Fruit? = null

    private var mAdapter: FruitAdapter? = null


    override fun onItemClick(fruit: Fruit, position: Int) {
        Toast.makeText(this, "${fruit.fruit.get(position).type} Clicked !", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, FruitInformation::class.java)
            intent.putExtra("type", fruit.fruit.get(position).type)
            intent.putExtra("price", fruit.fruit.get(position).price)
            intent.putExtra("weight", fruit.fruit.get(position).weight)

        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCompositeDisposable = CompositeDisposable()
        initRecyclerView()
        loadJSON()

        val pullToRefresh = findViewById<SwipeRefreshLayout>(R.id.pullToRefresh)
        pullToRefresh.setOnRefreshListener {
            loadJSON()
            pullToRefresh.isRefreshing = false
        }
    }

    private fun loadJSON() {
        val requestInterface = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(FruitInterface::class.java)

        mCompositeDisposable?.add(requestInterface.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError))
    }

    private fun initRecyclerView() {
        rv_fruit_list.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv_fruit_list.layoutManager = layoutManager    }

    private fun handleResponse(fruitList: Fruit){
        mFruitArrayList = fruitList
        mAdapter = FruitAdapter(mFruitArrayList!!, this)

        rv_fruit_list.adapter = mAdapter
    }

    private fun handleError(error: Throwable){
        Log.d(TAG, error.localizedMessage)
        Toast.makeText(this, "Error ${error.localizedMessage}",
                Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable?.clear()
    }
}
