package dev.sunnat629.shutterstockimages.models.networks

import dev.sunnat629.shutterstockimages.DSConstants.CONSUMER_KEY
import dev.sunnat629.shutterstockimages.DSConstants.CONSUMER_SECRET
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BasicAuthInterceptor : Interceptor {

    private val credentials: String = Credentials.basic(CONSUMER_KEY, CONSUMER_SECRET)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }
}