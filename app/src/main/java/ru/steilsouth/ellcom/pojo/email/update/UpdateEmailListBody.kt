package ru.steilsouth.ellcom.pojo.email.update

import com.google.gson.annotations.SerializedName
import ru.steilsouth.ellcom.pojo.email.EmailAddress

class UpdateEmailListBody(token: String, emails: MutableList<EmailAddress>) {
    @SerializedName("method")
    private val method = "updateEmailContactList"

    @SerializedName("params")
    private val params = Params(token, emails)

    class Params(token: String, emails: MutableList<EmailAddress>) {
        @SerializedName("token")
        private val tokenRes = token

        @SerializedName("email")
        private val emailsRes = emails
    }
}