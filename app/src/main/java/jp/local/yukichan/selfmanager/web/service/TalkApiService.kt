package jp.local.yukichan.selfmanager.web.service

import jp.local.yukichan.selfmanager.data.TalkResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TalkApiService {

    companion object {
        const val END_POINT = "https://api.a3rt.recruit-tech.co.jp/talk/v1/"
        const val API_KEY = "55lek6cYRivkgGM4TNBfOJwMcuEjM4AQ"
    }

//    fun talk(@Body body: Map<String, String>): Call<TalkResult>
    @FormUrlEncoded
    @POST("smalltalk")
    fun talk(@Field("apikey") apikey: String, @Field("query") query: String): Call<TalkResult>
}
