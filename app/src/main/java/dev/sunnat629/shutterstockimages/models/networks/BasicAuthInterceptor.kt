package dev.sunnat629.shutterstockimages.models.networks

import dev.sunnat629.shutterstockimages.DSConstants.CONSUMER_KEY
import dev.sunnat629.shutterstockimages.DSConstants.CONSUMER_SECRET
import dev.sunnat629.shutterstockimages.DSConstants.HEADER_AUTHORIZATION
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * BasicAuthInterceptor.kt
 * It observes, modifies, and potentially short-circuits requests going out and the corresponding
 * responses coming back in.
 * @throws IOException if there is no internet or any other server related issue.
 */
class BasicAuthInterceptor : Interceptor {

    // It contains the CONSUMER_KEY and CONSUMER_SECRET of the Shutterstock app
    private val credentials: String = Credentials.basic(CONSUMER_KEY, CONSUMER_SECRET)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestWithAuth = request.newBuilder()
            .header(HEADER_AUTHORIZATION, credentials).build()
        return chain.proceed(requestWithAuth)
    }
}