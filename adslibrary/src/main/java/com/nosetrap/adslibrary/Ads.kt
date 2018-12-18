package com.nosetrap.adslibrary

import android.app.Activity
import android.widget.FrameLayout
import android.os.Bundle
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.*


/**
 * a class that helps with adding ads to a view
 * when using a debug build, use the bannerDebug() method, on a release build use the bannerRelease() method()
 * only use 1 banner ad for each instantiation of this class
 */
class Ads(private val activity: Activity){
    var interstitialAd: InterstitialAd? = null
    var bannerAd: AdView? = null

    private val adsManager = AdsManager(activity)

    /**
     * call this in the on destry method of activity or fragment
     */
    fun destroy(){
        bannerAd?.destroy()
    }
    /**
     * add a banner ad to a view
     * @param unitAdId the unitAdId for the bannerAd
     * @param adContainer the container where the ad is inserted
     */
    fun bannerRelease(adUnitId: String,adContainer: FrameLayout){
        showBannerAd(adUnitId, adContainer)
    }

    /**
     * add a banner ad to a view using the test ad unit id
     *
     */
    fun bannerDebug(adContainer: FrameLayout){
        showBannerAd("ca-app-pub-3940256099942544/6300978111",adContainer)
    }

    /**
     *
     */
    private fun showBannerAd(adUnitId: String,adContainer: FrameLayout) {
        val adsMode = adsManager.getAdsMode()

        if (bannerAd != null) {
            throw IllegalStateException("banner ad was already instantiated, can only instantiate once")
        }

        bannerAd = AdView(activity)
        bannerAd!!.adSize = AdSize.BANNER
        //set the test unitId
        bannerAd!!.adUnitId = adUnitId

        activity.runOnUiThread { adContainer.addView(bannerAd) }
        val adRequestBuilder = AdRequest.Builder()

        if (adsMode == AdsManager.AdsMode.NON_PERSONALISED) {
            val extras = Bundle()
            extras.putString("npa", "1")
            adRequestBuilder.addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
        }

        activity.runOnUiThread { bannerAd!!.loadAd(adRequestBuilder.build()) }
    }


    /**
     * prepares the interstitial ad, should be called long before showIntertitial() , this is to
     * enable the ad to load
     */
    fun prepareInterstitialRelease(adUnitId: String){
        prepareInterstitialAd(adUnitId)
    }

    /**
     * prepares the interstial ad
     */
    fun prepareInsterstitialDebug(){
        prepareInterstitialAd("ca-app-pub-3940256099942544/1033173712")
    }

    private fun prepareInterstitialAd(adUnitId: String){
        val adsMode = adsManager.getAdsMode()

        interstitialAd = InterstitialAd(activity)
        interstitialAd?.adUnitId = adUnitId
        val adRequestBuilder = AdRequest.Builder()

        if (adsMode == AdsManager.AdsMode.NON_PERSONALISED) {
            val extras = Bundle()
            extras.putString("npa", "1")
            adRequestBuilder.addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
        }

        activity.runOnUiThread { interstitialAd?.loadAd(adRequestBuilder.build()) }

    }

    /**
     * show the intertitial ad
     */
    fun showInterstitialAd(){
        try {
            if (interstitialAd!!.isLoaded) {
                interstitialAd?.show()
            }
        }catch (e: Exception){}
    }
}