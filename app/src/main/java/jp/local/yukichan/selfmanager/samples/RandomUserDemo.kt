package jp.local.yukichan.selfmanager.samples

/**
 * Created by takamk2 on 18/03/02.
 *
 * The Edit Fragment of Base Class.
 */
data class RandomUserDemo(var info: Info,
                          var results: List<Result>)

data class Info(var seed: String,
                var results: Int,
                var page: Int,
                var version: String)

data class Result(var gender: String,
                  var email: String,
                  var registered: String,
                  var dob: String,
                  var phone: String,
                  var cell: String)