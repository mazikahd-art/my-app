// File Path: app/src/main/java/com/freeshot/app/AdBlocker.kt
package com.freeshot.app

import android.net.Uri
import android.webkit.WebResourceResponse
import java.io.ByteArrayInputStream

object AdBlocker {
    private val adServers = setOf(
        "adservice.google.com",
        "googleads.g.doubleclick.net",
        "pagead2.googlesyndication.com",
        "ad.doubleclick.net",
        "ads.pubmatic.com",
        "ads.mopub.com",
        "adsystem.com",
        "popads.net",
        "popcash.net"
    )

    private val adKeywords = listOf(
        "/ads/", "/ad/", "/banner/", "?ad=", "&ad="
    )

    fun isAd(url: String): Boolean {
        try {
            val uri = Uri.parse(url)
            val host = uri.host ?: return false
            
            if (adServers.any { host.contains(it) }) {
                return true
            }

            if (adKeywords.any { url.contains(it, ignoreCase = true) }) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun createEmptyResource(): WebResourceResponse {
        val emptyData = ByteArrayInputStream("".toByteArray())
        return WebResourceResponse("text/plain", "UTF-8", emptyData)
    }
}
