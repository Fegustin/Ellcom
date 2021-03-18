package ru.steilsouth.ellcom.api

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*
import ru.steilsouth.ellcom.pojo.auth.AuthBody
import ru.steilsouth.ellcom.pojo.auth.AuthResult
import ru.steilsouth.ellcom.pojo.auth.OneTimePasswordBody
import ru.steilsouth.ellcom.pojo.balance.BalanceResult
import ru.steilsouth.ellcom.pojo.balance.ContractBalanceBody
import ru.steilsouth.ellcom.pojo.bills.createbills.CreateBillsBody
import ru.steilsouth.ellcom.pojo.bills.list.BillsListBody
import ru.steilsouth.ellcom.pojo.bills.list.BillsListResult
import ru.steilsouth.ellcom.pojo.changepassword.ChangePasswordResult
import ru.steilsouth.ellcom.pojo.changepassword.contract.ContractChangePasswordBody
import ru.steilsouth.ellcom.pojo.changepassword.inet.InternetChangePasswordBody
import ru.steilsouth.ellcom.pojo.changepassword.inet.ServiceInternetBody
import ru.steilsouth.ellcom.pojo.changepassword.inet.ServiceInternetResult
import ru.steilsouth.ellcom.pojo.contacts.MobileContactBody
import ru.steilsouth.ellcom.pojo.contacts.MobileContactResult
import ru.steilsouth.ellcom.pojo.distributefunds.ReallocationFundsBody
import ru.steilsouth.ellcom.pojo.email.get.EmailAddressResult
import ru.steilsouth.ellcom.pojo.email.get.GetEmailListBody
import ru.steilsouth.ellcom.pojo.email.update.UpdateEmailListBody
import ru.steilsouth.ellcom.pojo.forgotpassword.ForgotPasswordBody
import ru.steilsouth.ellcom.pojo.forgotpassword.ForgotPasswordResult
import ru.steilsouth.ellcom.pojo.infoprofile.InfoBody
import ru.steilsouth.ellcom.pojo.infoprofile.InfoResult
import ru.steilsouth.ellcom.pojo.notification.NotificationListBody
import ru.steilsouth.ellcom.pojo.notification.NotificationListResult
import ru.steilsouth.ellcom.pojo.notification.delete.DeleteNotificationBody
import ru.steilsouth.ellcom.pojo.notification.read.ReadNotificationBody
import ru.steilsouth.ellcom.pojo.notification.subscribe.SubscribeNotificationsBody
import ru.steilsouth.ellcom.pojo.session.ActiveSessionBody
import ru.steilsouth.ellcom.pojo.session.HistorySessionBody
import ru.steilsouth.ellcom.pojo.session.SessionResult
import ru.steilsouth.ellcom.pojo.subcontracts.SubContractsBody
import ru.steilsouth.ellcom.pojo.subcontracts.SubContractsResult

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
    @POST("MobilePushService/")
    suspend fun subscribeNotification(@Body body: SubscribeNotificationsBody): ChangePasswordResult

    @Headers("Content-Type: application/json")
    @POST("MobilePushService/")
    suspend fun readNotification(@Body body: ReadNotificationBody): ChangePasswordResult

    @Headers("Content-Type: application/json")
    @POST("MobilePushService/")
    suspend fun deleteNotification(@Body body: DeleteNotificationBody): ChangePasswordResult

    @Headers("Content-Type: application/json")
    @POST("MobileBalanceService/")
    suspend fun getBalance(@Body body: ContractBalanceBody): BalanceResult

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

    @Headers("Content-Type: application/json")
    @POST("MobileBalanceService/")
    suspend fun reallocationOfFunds(@Body body: ReallocationFundsBody): ChangePasswordResult

    @Headers("Content-Type: application/json")
    @POST("MobileContractParameterService/")
    suspend fun getEmailList(@Body bodyGet: GetEmailListBody): EmailAddressResult

    @Headers("Content-Type: application/json")
    @POST("MobileContractParameterService/")
    suspend fun updateEmailList(@Body body: UpdateEmailListBody): ChangePasswordResult


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

    // Cms ellco
    @Headers(
        "Content-Type: multipart/form-data",
        "Authorization: VjE4o2DzXytrXDHVy5rW1OdGzOpidENK91RP5XTUxkpdgKYbR1QaSw"
    )
    @POST("acc_hist")
    suspend fun getBillsList(@Body body: BillsListBody): BillsListResult

    @Headers(
        "Content-Type: multipart/form-data",
        "Authorization: VjE4o2DzXytrXDHVy5rW1OdGzOpidENK91RP5XTUxkpdgKYbR1QaSw"
    )
    @POST("cr_acc")
    suspend fun createBills(@Body body: CreateBillsBody): JsonObject
}

object ApiUtils {
    private const val BASE_URL = "https://bill.ellcom.ru/bgbilling/ellcommobile/ru.ellcom.mobile/"
    private const val REGISTRATION_URL = "https://bitrix24.ellcom.ru/pub/form/"
    private const val CMS_ELLCO = "https://cms.ellco.ru/api/onec/"

    val apiService: ApiService get() = RetrofitClient.getClient(BASE_URL)
    val apiRegistration: ApiService get() = RetrofitClient.getClient(REGISTRATION_URL)
    val apiCms: ApiService get() = RetrofitClient.getClient(CMS_ELLCO)
}