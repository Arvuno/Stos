package com.m4ykey.stos.core.network

import com.m4ykey.stos.sites.data.helpers.SiteManager
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkClient {

    private const val API_VERSION = "2.3"
    private val API_KEY = API_KEY_VALUE
    private const val BASE_HOST = "api.stackexchange.com"

    fun create(engine : HttpClientEngine, siteManager: SiteManager) : HttpClient {
        return HttpClient(engine) {
            configureLogging()
            configureDefaultRequest(siteManager)
            configureTimeout()
            configureRetries()
            configureValidator()
            configureContentNegotiation()
            configureContentEncoding()
        }
    }

    private fun HttpClientConfig<*>.configureContentNegotiation() {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    private fun HttpClientConfig<*>.configureValidator() {
        HttpResponseValidator {
            validateResponse { response ->
                if (response.status.value >= 400) {
                    throw ResponseException(response, "Http ${response.status.value}: ${response.status.description}")
                }
            }
        }
    }

    private fun HttpClientConfig<*>.configureContentEncoding() {
        install(ContentEncoding) {
            gzip()
        }
    }

    private fun HttpClientConfig<*>.configureTimeout() {
        install(HttpTimeout) {
            requestTimeoutMillis = 15_000
            socketTimeoutMillis = 15_000
            connectTimeoutMillis = 10_000
        }
    }

    private fun HttpClientConfig<*>.configureDefaultRequest(siteManager: SiteManager) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_HOST

                if (pathSegments.isEmpty() || pathSegments.first() != API_VERSION) {
                    path(API_VERSION, "")
                }
            }
            contentType(ContentType.Application.Json)
        }

        install("SiteHeaderPlugin") {
            requestPipeline.intercept(HttpRequestPipeline.Before) {
                val url = context.url

                if (!url.parameters.contains("key")) {
                    url.parameters.append("key", API_KEY)
                }

                val isSitesRequest = url.pathSegments.contains("sites")

                if (!isSitesRequest && !url.parameters.contains("site")) {
                    url.parameters.append("site", siteManager.selectedSite.value)
                }
            }
        }
    }

    private fun HttpClientConfig<*>.configureRetries() {
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 3)
            exponentialDelay()
        }
    }

    private fun HttpClientConfig<*>.configureLogging() {
        install(Logging) {
            level = LogLevel.INFO
            logger = Logger.DEFAULT
        }
    }
}