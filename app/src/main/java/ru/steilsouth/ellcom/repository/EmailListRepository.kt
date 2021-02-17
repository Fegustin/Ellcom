package ru.steilsouth.ellcom.repository

import android.util.Log
import ru.steilsouth.ellcom.api.ApiUtils
import ru.steilsouth.ellcom.pojo.changepassword.ChangePasswordResult
import ru.steilsouth.ellcom.pojo.email.EmailAddress
import ru.steilsouth.ellcom.pojo.email.get.EmailAddressResult
import ru.steilsouth.ellcom.pojo.email.get.GetEmailListBody
import ru.steilsouth.ellcom.pojo.email.update.UpdateEmailListBody


class EmailListRepository {
    private val tag = "Error: class -> EmailListRepository: "

    suspend fun getEmailList(token: String): EmailAddressResult? {
        return try {
            ApiUtils.apiService.getEmailList(GetEmailListBody(token))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }

    suspend fun updateEmailList(
        token: String,
        emails: MutableList<EmailAddress>
    ): ChangePasswordResult? {
        return try {
            ApiUtils.apiService.updateEmailList(UpdateEmailListBody(token, emails))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}