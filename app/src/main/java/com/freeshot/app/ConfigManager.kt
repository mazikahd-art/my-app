// File Path: app/src/main/java/com/freeshot/app/ConfigManager.kt
package com.freeshot.app

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ConfigManager {
    private const val CONFIG_URL = "https://script.google.com/macros/s/AKfycbwvVcAbjCYwxXN8yw7N9mp3rmwEztvEqk9j5lbpINdspN-NLzOc3aj_nnyHzHnffdj8sg/exec"

    var homeUrl: String = "https://freeshots.top/"
    var moviesUrl: String = "https://freeshots.top/movies/"
    var liveTvUrl: String = "https://freeshots.top/live-tv/"
    var adblockEnabled: Boolean = true

    suspend fun fetchConfig(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(CONFIG_URL)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val json = JSONObject(response)

                    if (json.has("home_url")) homeUrl = json.getString("home_url")
                    if (json.has("movies_url")) moviesUrl = json.getString("movies_url")
                    if (json.has("live_tv_url")) liveTvUrl = json.getString("live_tv_url")
                    if (json.has("adblock_system")) adblockEnabled = json.getBoolean("adblock_system")
                    
                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
