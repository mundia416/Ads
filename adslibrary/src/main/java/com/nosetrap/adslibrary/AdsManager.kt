package com.nosetrap.adslibrary

import android.content.Context
import com.nosetrap.storage.prefs.PreferenceHandler

class AdsManager(context: Context) {
    enum class AdsMode{
        PERSONALISED,
        NON_PERSONALISED
    }

    companion object {
        internal const val PERSONALISED = 1
        internal const val NON_PERSONALISED = 2
    }
    /**
     * determines whether ads are personalised ads or not
     */
    private val prefAdsMode = "Pref_adsMode"

    private val preferenceHandler = PreferenceHandler(context)

    /**
     *
     */
    fun setAdsMode(mode: AdsMode){
        val mode = if(mode == AdsMode.PERSONALISED) PERSONALISED else NON_PERSONALISED
        preferenceHandler.edit(prefAdsMode,mode)
    }

    /**
     *
     */
    internal fun getAdsMode(): AdsMode {
        val mode = preferenceHandler.get(prefAdsMode, PERSONALISED)
        return if(mode == PERSONALISED) AdsMode.PERSONALISED else AdsMode.NON_PERSONALISED
    }

}