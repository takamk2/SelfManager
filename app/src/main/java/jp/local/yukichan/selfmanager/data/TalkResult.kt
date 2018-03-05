package jp.local.yukichan.selfmanager.data

import com.google.gson.annotations.SerializedName

/**
 * {
 *   status: 200,
 *   message: "ok",
 *   results: [{
 *     perplexity: 2.3688167429546714,
 *     reply: "おはようございます"
 *   }],
 * }
 */
data class TalkResult(
        @SerializedName("apikey") var apikey: String,
        @SerializedName("query") var query: String,
        @SerializedName("status") var status: Int,
        @SerializedName("message") var message: String,
        @SerializedName("results") var results: List<Result>) {

    data class Result(
            @SerializedName("perplexity") var perplexity: Double,
            @SerializedName("reply") var reply: String)
}