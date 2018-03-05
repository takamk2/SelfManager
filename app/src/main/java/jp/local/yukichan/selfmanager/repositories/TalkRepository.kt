package jp.local.yukichan.selfmanager.repositories

import jp.local.yukichan.selfmanager.application.CustomApplication
import jp.local.yukichan.selfmanager.data.TalkResult
import jp.local.yukichan.selfmanager.web.service.TalkApiService.Companion.API_KEY
import timber.log.Timber

/**
 * Created by takamk2 on 18/03/04.
 *
 * The Edit Fragment of Base Class.
 */
class TalkRepository(application: CustomApplication) : Repository {

    private val talkApiService = application.talkApiService

    suspend fun talk(message: String): TalkResult? {
        val params = mutableMapOf<String, String>().apply {
            put("apikey", API_KEY)
            put("query", message)
        }
        val response = talkApiService.talk(API_KEY, message).execute()
        Timber.d("DEBUG:: response=${response.isSuccessful} : ${response.errorBody()?.string()} : ${response.raw()}")
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
