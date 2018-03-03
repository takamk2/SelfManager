package jp.local.yukichan.selfmanager.samples

import jp.local.yukichan.selfmanager.data.RandomUser
import retrofit2.Call
import retrofit2.http.GET

interface RandomUserApiService {

    companion object {
        const val endPoint = "http://randomuser.me/"
    }

    @GET("api")
    fun getUser(): Call<RandomUser>
}