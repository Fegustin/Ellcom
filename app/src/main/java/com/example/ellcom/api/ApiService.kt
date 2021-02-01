package com.example.ellcom.api

import com.example.ellcom.pojo.auth.AuthBody
import com.example.ellcom.pojo.auth.AuthResult
import com.example.ellcom.pojo.auth.OneTimePasswordBody
import com.example.ellcom.pojo.balance.ContractBalanceBody
import com.example.ellcom.pojo.changepassword.ChangePasswordResult
import com.example.ellcom.pojo.changepassword.contract.ContractChangePasswordBody
import com.example.ellcom.pojo.changepassword.inet.InternetChangePasswordBody
import com.example.ellcom.pojo.changepassword.inet.ServiceInternetBody
import com.example.ellcom.pojo.changepassword.inet.ServiceInternetResult
import com.example.ellcom.pojo.contacts.MobileContactBody
import com.example.ellcom.pojo.contacts.MobileContactResult
import com.example.ellcom.pojo.forgotpassword.ForgotPasswordBody
import com.example.ellcom.pojo.forgotpassword.ForgotPasswordResult
import com.example.ellcom.pojo.infoprofile.InfoBody
import com.example.ellcom.pojo.infoprofile.InfoResult
import com.example.ellcom.pojo.notification.NotificationListBody
import com.example.ellcom.pojo.notification.NotificationListResult
import com.example.ellcom.pojo.session.ActiveSessionBody
import com.example.ellcom.pojo.session.HistorySessionBody
import com.example.ellcom.pojo.session.SessionResult
import com.example.ellcom.pojo.subcontracts.SubContractsBody
import com.example.ellcom.pojo.subcontracts.SubContractsResult
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

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

    @Headers("Content-Type: application/json")
    @POST("MobilePushService/")
    suspend fun getNotificationList(@Body body: NotificationListBody): NotificationListResult

    @Headers("Content-Type: application/json")
    @POST("MobileBalanceService/")
    suspend fun getBalance(@Body body: ContractBalanceBody): NotificationListResult

    @Headers("Content-Type: application/json")
    @POST("MobileContractParameterService/")
    suspend fun getMobileContact(@Body body: MobileContactBody): MobileContactResult

    @Headers("Content-Type: application/json")
    @POST("MobileContractService/")
    suspend fun passwordChangeContract(@Body bodyContract: ContractChangePasswordBody): ChangePasswordResult

    @Headers("Content-Type: application/json")
    @POST("MobileInetService/")
    suspend fun passwordChangeInternet(@Body bodyContract: InternetChangePasswordBody): ChangePasswordResult

    @Headers("Content-Type: application/json")
    @POST("MobileInetService/")
    suspend fun getServiceInternet(@Body body: ServiceInternetBody): ServiceInternetResult

    @Headers("Content-Type: application/json")
    @POST("MobileInetService/")
    suspend fun getActiveSession(@Body body: ActiveSessionBody): SessionResult

    @Headers("Content-Type: application/json")
    @POST("MobileInetService/")
    suspend fun getHistorySession(@Body body: HistorySessionBody): SessionResult


    // Registration
    @POST("47_zayavka_na_podklyuchenie_yur_lits/l2mvkj/")
    @FormUrlEncoded
    suspend fun registration(
        @Field("LEAD_NAME") name: String,
        @Field("LEAD_COMPANY_TITLE") nameCompany: String,
        @Field("LEAD_UF_CRM_1487053251") phone: String,
        @Field("LEAD_UF_CRM_1436947672") address: String,
        @Field("LEAD_EMAIL") email: String
    ): Response<JsonObject>
}

object ApiUtils {
    private const val BASE_URL = "https://bill.ellcom.ru/bgbilling/ellcommobile/ru.ellcom.mobile/"
    private const val REGISTRATION_URL = "https://bitrix24.ellcom.ru/pub/form/"

    val apiService: ApiService get() = RetrofitClient.getClient(BASE_URL)
    val apiRegistration: ApiService get() = RetrofitClient.getClient(REGISTRATION_URL)
}