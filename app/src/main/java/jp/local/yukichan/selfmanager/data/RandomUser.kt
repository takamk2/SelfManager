package jp.local.yukichan.selfmanager.data

import com.google.gson.annotations.SerializedName

/**
 * Created by takamk2 on 18/03/03.
 *
 * The Edit Fragment of Base Class.
 */
data class RandomUser(
        @SerializedName("info") var info: InfoModel,
        @SerializedName("results") var results: List<ResultModel>) {

    data class InfoModel(
            @SerializedName("seed") var seed: String,
            @SerializedName("results") var results: Int,
            @SerializedName("page") var page: Int,
            @SerializedName("version") var version: String)

    data class ResultModel(
            @SerializedName("gender") var gender: String,
            @SerializedName("email") var email: String,
            @SerializedName("registered") var registered: String,
            @SerializedName("dob") var dob: String,
            @SerializedName("phone") var phone: String,
            @SerializedName("cell") var cell: String,
            @SerializedName("name") var name: Name)

    data class Name(
            @SerializedName("first") var first: String,
            @SerializedName("last") var last: String,
            @SerializedName("title") var title: String)
}