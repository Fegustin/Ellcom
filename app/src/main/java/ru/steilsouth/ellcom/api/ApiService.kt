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
import ru.steilsouth.ellcom.pojo.TotalReturnValue
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
import ru.steilsouth.ellcom.pojo.registration.RegistrationBody
import ru.steilsouth.ellcom.pojo.session.ActiveSessionBody
import ru.steilsouth.ellcom.pojo.session.HistorySessionBody
import ru.steilsouth.ellcom.pojo.session.SessionResult
import ru.steilsouth.ellcom.pojo.subcontracts.SubContractsBody
import ru.steilsouth.ellcom.pojo.subcontracts.SubContractsResult
import ru.steilsouth.ellcom.utils.enam.Token

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
    suspend fun subscribeNotification(@Body body: SubscribeNotificationsBody): TotalReturnValue

    @Headers("Content-Type: application/json")
    @POST("MobilePushService/")
    suspend fun readNotification(@Body body: ReadNotificationBody): TotalReturnValue

    @Headers("Content-Type: application/json")
    @POST("MobilePushService/")
    suspend fun deleteNotification(@Body body: DeleteNotificationBody): TotalReturnValue

    @Headers("Content-Type: application/json")
    @POST("MobileBalanceService/")
    suspend fun getBalance(@Body body: ContractBalanceBody): BalanceResult

    @Headers("Content-Type: application/json")
    @POST("MobileContractParameterService/")
    suspend fun getMobileContact(@Body body: MobileContactBody): MobileContactResult

    @Headers("Content-Type: application/json")
    @POST("MobileContractService/")
    suspend fun passwordChangeContract(@Body bodyContract: ContractChangePasswordBody): TotalReturnValue

    @Headers("Content-Type: application/json")
    @POST("MobileInetService/")
    suspend fun passwordChangeInternet(@Body bodyContract: InternetChangePasswordBody): TotalReturnValue

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
    suspend fun reallocationOfFunds(@Body body: ReallocationFundsBody): TotalReturnValue

    @Headers("Content-Type: application/json")
    @POST("MobileContractParameterService/")
    suspend fun getEmailList(@Body bodyGet: GetEmailListBody): EmailAddressResult

    @Headers("Content-Type: application/json")
    @POST("MobileContractParameterService/")
    suspend fun updateEmailList(@Body body: UpdateEmailListBody): TotalReturnValue


    // Cms ellco
    @Headers(
        "Content-Type: multipart/form-data",
        "Authorization: ir1nlHXr4t949MG3XE1pabUjhRjzNb6CcBhmrdslGmqlEfIiRVq4g"
    )
    @POST("ellcom/info/become.client.set")
    suspend fun registration(@Body body: RegistrationBody): Response<JsonObject>

    @Headers(
        "Content-Type: multipart/form-data",
        "Authorization: ir1nlHXr4t949MG3XE1pabUjhRjzNb6CcBhmrdslGmqlEfIiRVq4g"
    )
    @POST("onec/acc_hist")
    suspend fun getBillsList(@Body body: BillsListBody): BillsListResult

    @Headers(
        "Content-Type: multipart/form-data",
        "Authorization: ir1nlHXr4t949MG3XE1pabUjhRjzNb6CcBhmrdslGmqlEfIiRVq4g"
    )
    @POST("onec/cr_acc")
    suspend fun createBills(@Body body: CreateBillsBody): TotalReturnValue
}

object ApiUtils {
    private const val BASE_URL = "https://bill.ellcom.ru/bgbilling/ellcommobile/ru.ellcom.mobile/"
    private const val CMS_ELLCO = "https://cms.ellco.ru/api/"

    val apiService: ApiService get() = RetrofitClient.getClient(BASE_URL)
    val apiCms: ApiService get() = RetrofitClient.getClient(CMS_ELLCO)
}