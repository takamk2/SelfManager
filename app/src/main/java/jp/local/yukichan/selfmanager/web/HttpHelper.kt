package jp.local.yukichan.selfmanager.web

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by takamk2 on 18/03/03.
 *
 * The Edit Fragment of Base Class.
 */
object HttpHelper {

    val builder: OkHttpClient.Builder

    init {
        builder = OkHttpClient.Builder()
                .addInterceptor({ chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .header("Client-OS", "Android")
                            .method(original.method(), original.body())
                            .build()
                    return@addInterceptor chain.proceed(request)
                })
                .readTimeout(10, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
    }
}

