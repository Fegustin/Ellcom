package com.example.ellcom.api

import com.example.ellcom.pojo.auth.AuthBody
import com.example.ellcom.pojo.auth.AuthResult
import com.example.ellcom.pojo.auth.OneTimePasswordBody
import com.example.ellcom.pojo.forgotpassword.ForgotPasswordBody
import com.example.ellcom.pojo.forgotpassword.ForgotPasswordResult
import com.example.ellcom.pojo.infoprofile.InfoBody
import com.example.ellcom.pojo.infoprofile.InfoResult
import com.example.ellcom.pojo.subcontracts.SubContractsBody
import com.example.ellcom.pojo.subcontracts.SubContractsResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    //Billing
    @Headers("Content-Type: application/json")
    @POST("MobileAuthService/")
    suspend fun auth(@Body body: AuthBody): AuthResult

    @Headers("Content-Type: application/json")
    @POST("MobileContractService/")
    suspend fun forgotPassword(@Body body: ForgotPasswordBody): ForgotPasswordResult

    @Headers("Content-Type: application/json")
    @POST("MobileAuthService/")
    suspend fun authOneTimePassword(@Body body: OneTimePasswordBody): AuthResult

    @Headers("Content-Type: application/json")
    @POST("MobileContractService/")
    suspend fun info(@Body body: InfoBody): InfoResult

    @Headers("Content-Type: application/json")
    @POST("MobileContractService/")
    suspend fun getSubContractsList(@Body body: SubContractsBody): SubContractsResult
}

object ApiUtils {
    private const val BASE_URL = "https://bill.ellcom.ru/bgbilling/ellcommobile/ru.ellcom.mobile/"

    val apiService: ApiService get() = RetrofitClient.getClient(BASE_URL)
}