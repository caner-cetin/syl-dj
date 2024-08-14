package cansu.com.plugins

import okhttp3.OkHttpClient


fun HttpClientFactory(): OkHttpClient {
    return OkHttpClient()
}