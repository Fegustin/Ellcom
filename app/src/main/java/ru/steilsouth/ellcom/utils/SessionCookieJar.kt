package ru.steilsouth.ellcom.utils

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.*
import kotlin.collections.ArrayList


private var cookiesGlobal: List<Cookie>? = null

class SessionCookieJar : CookieJar {
    override fun saveFromResponse(
        url: HttpUrl,
        cookies: List<Cookie>
    ) {

        println("Это после запроса")
        println(url)
        println(cookiesGlobal)
        cookiesGlobal = null

        if (url.encodedPath().endsWith("new") && cookiesGlobal == null) {
            cookiesGlobal = ArrayList(cookies)
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        println("Это перед запросом")
        println(url)
        println(cookiesGlobal)
        return if (url.encodedPath().endsWith("new") && cookiesGlobal != null) {
            cookiesGlobal!!
        } else Collections.emptyList()
    }
}
