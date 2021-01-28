package com.example.ellcom.repository

import android.util.Log
import com.example.ellcom.api.ApiUtils
import com.example.ellcom.pojo.changepassword.ChangePasswordResult
import com.example.ellcom.pojo.changepassword.contract.ContractChangePasswordBody
import com.example.ellcom.pojo.changepassword.inet.InternetChangePasswordBody
import com.example.ellcom.pojo.changepassword.inet.ServiceInternetBody
import com.example.ellcom.pojo.changepassword.inet.ServiceInternetResult

class ChangePasswordRepository {
    private val tag = "Error: class -> ChangePasswordRepository: "

    suspend fun passwordChange(
        token: String,
        oldPassword: String,
        newPassword: String,
        isContact: Boolean,
        servId: Int
    ): ChangePasswordResult? {
        return try {
            if (isContact) {
                ApiUtils.apiService.passwordChangeContract(
                    ContractChangePasswordBody(
                        token,
                        oldPassword,
                        newPassword
                    )
                )
            } else {
                ApiUtils.apiService.passwordChangeInternet(
                    InternetChangePasswordBody(
                        token,
                        servId,
                        oldPassword,
                        newPassword
                    )
                )
            }
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }

    suspend fun getListServiceInternet(
        token: String
    ): ServiceInternetResult? {
        return try {
            ApiUtils.apiService.getServiceInternet(ServiceInternetBody(token))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}