package jp.local.yukichan.selfmanager.extensions

import android.content.Context
import jp.local.yukichan.selfmanager.CustomApplication

/**
 * Created by takamk2 on 18/03/01.
 *
 * The Edit Fragment of Base Class.
 */
fun Context.app(): CustomApplication = applicationContext as CustomApplication