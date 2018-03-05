package jp.local.yukichan.selfmanager.web.service

import jp.local.yukichan.selfmanager.data.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by takamk2 on 18/03/05.
 *
 * The Edit Fragment of Base Class.
 */
interface GitHubService {

    companion object {
        const val END_POINT = "https://api.github.com/"
    }

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>
}