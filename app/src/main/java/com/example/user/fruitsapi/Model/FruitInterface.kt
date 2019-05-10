package com.example.user.fruitsapi.Model

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface FruitInterface {
    @GET("data.json")
    fun getData() : Observable<Fruit>
}