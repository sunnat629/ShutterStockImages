package dev.sunnat629.shutterstockimages.models.networks

import dev.sunnat629.shutterstockimages.DSConstants.CONSUMER_KEY
import dev.sunnat629.shutterstockimages.DSConstants.CONSUMER_SECRET
import dev.sunnat629.shutterstockimages.LoggingTags
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import dev.sunnat629.shutterstockimages.DSConstants.HEADER_AUTHORIZATION
import java.net.UnknownHostException


class BasicAuthInterceptor : Interceptor {

    private val credentials: String = Credentials.basic(CONSUMER_KEY, CONSUMER_SECRET)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val requestWithAuth = request.newBuilder()
                .header(HEADER_AUTHORIZATION, credentials).build()
         return chain.proceed(requestWithAuth)
    }
}