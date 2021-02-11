package ru.steilsouth.ellcom.repository

import android.util.Log
import ru.steilsouth.ellcom.api.ApiUtils
import ru.steilsouth.ellcom.pojo.infoprofile.InfoBody
import ru.steilsouth.ellcom.pojo.infoprofile.InfoResult
import ru.steilsouth.ellcom.pojo.notification.NotificationListBody
import ru.steilsouth.ellcom.pojo.notification.NotificationListResult
import ru.steilsouth.ellcom.pojo.subcontracts.SubContractsBody
import ru.steilsouth.ellcom.pojo.subcontracts.SubContractsResult

class MainAndSubRepository {
    private val tag = "Error: class -> MainRepository: "

    suspend fun infoProfile(token: String): InfoResult? {
        return try {
            ApiUtils.apiService.info(InfoBody(token))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }

    suspend fun getSubContractsList(token: String): SubContractsResult? {
        return try {
            ApiUtils.apiService.getSubContractsList(SubContractsBody(token))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }

    suspend fun getNotificationList(
        token: String,
        notConfirm: Boolean,
        page: Int
    ): NotificationListResult? {
        return try {
            ApiUtils.apiService.getNotificationList(NotificationListBody(token, notConfirm, page))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}