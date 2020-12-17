package com.example.ellcom.api

import com.example.ellcom.pojo.auth.AuthBody
import com.example.ellcom.pojo.auth.AuthResult
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    //Billing
    @Headers("Content-Type: application/json")
    @POST("MobileAuthService/")
    suspend fun auth(@Body body: AuthBody): AuthResult
}

object ApiUtils {
    private const val BASE_URL = "https://bill.ellco.ru/bgbilling/ellcomobile/ru.ellco.mobile/"

    val apiService: ApiService get() = RetrofitClient.getClient(BASE_URL)
}