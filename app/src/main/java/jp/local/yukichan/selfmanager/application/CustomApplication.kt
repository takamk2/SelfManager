package jp.local.yukichan.selfmanager.application

import android.app.Application
import android.arch.persistence.room.Room
import com.google.gson.GsonBuilder
import jp.local.yukichan.selfmanager.local.database.AppDatabase
import jp.local.yukichan.selfmanager.local.database.AppDatabase.Companion.DB_NAME
import jp.local.yukichan.selfmanager.web.service.RandomUserApiService
import jp.local.yukichan.selfmanager.web.HttpHelper
import jp.local.yukichan.selfmanager.web.service.GitHubService
import jp.local.yukichan.selfmanager.web.service.TalkApiService
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
    lateinit var talkApiService: TalkApiService
    lateinit var gitHubService: GitHubService

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        // DEBUG
        deleteDatabase(AppDatabase.DB_NAME)
        db = Room.databaseBuilder(this, AppDatabase::class.java, DB_NAME).build()

        randomUserApiService = create(RandomUserApiService::class.java, RandomUserApiService.END_POINT)
        talkApiService = create(TalkApiService::class.java, TalkApiService.END_POINT)
        gitHubService = create(GitHubService::class.java, GitHubService.END_POINT)
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