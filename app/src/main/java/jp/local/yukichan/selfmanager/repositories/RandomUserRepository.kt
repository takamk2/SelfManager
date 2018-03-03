package jp.local.yukichan.selfmanager.repositories

import jp.local.yukichan.selfmanager.application.CustomApplication
import jp.local.yukichan.selfmanager.data.RandomUser
import kotlinx.coroutines.experimental.delay

/**
 * Created by takamk2 on 18/03/03.
 *
 * The Edit Fragment of Base Class.
 */
class RandomUserRepository(application: CustomApplication) : Repository {

    private val randomUserApiService = application.randomUserApiService

    suspend fun getRandomUser(): RandomUser? {
        delay(1000)
        val response = randomUserApiService.getUser().execute()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}