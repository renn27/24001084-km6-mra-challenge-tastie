package com.refood.tastie.data.source.local.pref

import android.content.Context
import com.refood.tastie.utils.SharedPreferenceUtils
import com.refood.tastie.utils.SharedPreferenceUtils.set

interface UserPreference {
    fun getListMode(): Boolean

    fun setListMode(isUsingListMode: Boolean)
}

class UserPreferenceImpl(private val context: Context) : UserPreference {
    private val pref = SharedPreferenceUtils.createPreference(context, PREF_NAME)

    override fun getListMode(): Boolean = pref.getBoolean(KEY_IS_USING_LIST_MODE, false)

    override fun setListMode(isUsingListMode: Boolean) {
        pref[KEY_IS_USING_LIST_MODE] = isUsingListMode
    }

    companion object {
        const val PREF_NAME = "tastie-pref"
        const val KEY_IS_USING_LIST_MODE = "KEY_IS_USING_LIST_MODE"
    }
}
