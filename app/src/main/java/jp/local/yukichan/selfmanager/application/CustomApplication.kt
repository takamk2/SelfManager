package jp.local.yukichan.selfmanager.application

import android.app.Application
import android.arch.persistence.room.Room
import com.google.gson.GsonBuilder
import jp.local.yukichan.selfmanager.local.database.AppDatabase
import jp.local.yukichan.selfmanager.local.database.AppDatabase.Companion.DB_NAME
import jp.local.yukichan.selfmanager.samples.RandomUserApiService
import jp.local.yukichan.selfmanager.web.HttpHelper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

/**
 * Created by takamk2 on 18/03/01.
 *
 * The Edit Fragment of Base Class.
 */
class CustomApplication : Application() {

    lateinit var db: AppDatabase
    lateinit var randomUserApiService: RandomUserApiService

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        // DEBUG
        deleteDatabase(AppDatabase.DB_NAME)
        db = Room.databaseBuilder(this, AppDatabase::class.java, DB_NAME).build()

        randomUserApiService = create(RandomUserApiService::class.java, RandomUserApiService.endPoint)
    }

    private fun <S> create(serviceClass: Class<S>, endPoint: String): S {
        val gson = GsonBuilder()
                .serializeNulls()
                .create()

        // create retrofit
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(endPoint) // Put your base URL
                .client(HttpHelper.builder.build())
                .build()

        return retrofit.create(serviceClass)
    }

}