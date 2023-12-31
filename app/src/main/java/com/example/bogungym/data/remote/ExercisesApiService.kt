package com.example.bogungym.data.remote

import com.example.bogungym.BuildConfig
import com.example.bogungym.data.model.Exercises
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
const val BASE_URL ="https://exercisedb.p.rapidapi.com/"

const val apiKey = BuildConfig.API_KEY

val client: OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
    val newRequest: Request = chain.request().newBuilder()
        .addHeader("X-RapidAPI-Key", apiKey)
        .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
        .build()
    chain.proceed(newRequest)

}.build()


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ExercisesApiService {
    @GET("exercises?limit=999")
    suspend fun getExercises(): List<Exercises>

}

object ExercisesApi {
    val retrofitService: ExercisesApiService by lazy { retrofit.create(ExercisesApiService::class.java) }
}