package jp.local.yukichan.selfmanager.samples

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by takamk2 on 18/03/02.
 *
 * The Edit Fragment of Base Class.
 */
interface RandomUserApiService {

    @GET("api")
    fun getUser(): Call<RandomUserDemo>
}