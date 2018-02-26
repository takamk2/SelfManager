package jp.local.yukichan.selfmanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.rx2.await
import kotlinx.coroutines.experimental.rx2.rxSingle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RxView.clicks(btClick).subscribe {
            launch(UI) {
                btClick.isEnabled = false
                val ten = async(CommonPool) { RxModel.returnTenAsync().await() }
                val twenty = async(CommonPool) { RxModel.returnTwentyAsync().await() }
                val result = ten.await() * twenty.await()
                showToast("result = $result")
                btClick.isEnabled = true
            }
        }
    }

    object RxModel {

        fun returnTenAsync() = rxSingle(CommonPool) {
            delay(1000)
            return@rxSingle 10
        }

        fun returnTwentyAsync() = rxSingle(CommonPool) {
            delay(2000)
            return@rxSingle 20
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
