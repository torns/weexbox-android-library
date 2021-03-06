package com.weexbox.core.module

import com.taobao.weex.annotation.JSMethod

/**
 * Author: Mario
 * Time: 2018/8/17 下午5:24
 * Description: This is StorageModule
 */

open class StorageModule : BaseModule() {

    @JSMethod(uiThread = true)
    open fun setData(key: String, value: String) {

    }

    @JSMethod(uiThread = true)
    open fun getData(key: String): String {
        return key
    }

    @JSMethod(uiThread = true)
    open fun deleteData(key: String) {

    }

    @JSMethod(uiThread = true)
    open fun deleteAll() {

    }
}