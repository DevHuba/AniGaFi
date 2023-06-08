package eu.devhuba.anigafi.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.auth0.android.jwt.JWT
import dagger.hilt.android.internal.Contexts.getApplication
import eu.devhuba.anigafi.AniGaFiApplication
import eu.devhuba.anigafi.model.ApiConstants
import net.openid.appauth.AppAuthConfiguration
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.browser.BrowserAllowList
import net.openid.appauth.browser.VersionedBrowserMatcher
import org.json.JSONException
import timber.log.Timber
import java.security.MessageDigest
import java.security.SecureRandom

class MainViewMode(application: Application) : AndroidViewModel(application) {

    private var isAuthenticated by mutableStateOf(false)
    private var isFirstRun by mutableStateOf(true)

    private var authState: AuthState = AuthState()
    private var jwt: JWT? = null
    private lateinit var authorizationService: AuthorizationService
    lateinit var authServiceConfig: AuthorizationServiceConfiguration

    init {

//        authServiceConfig = AuthorizationServiceConfiguration(
//            Uri.parse(ApiConstants.ANIME_AUTH_URI),  // authorization endpoint
//            Uri.parse(ApiConstants.ANIME_TOKEN_URI)
//        ) // token endpoint


        fun restoreState() {
            val jsonString = getApplication(application)
                .getSharedPreferences(ApiConstants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getString(ApiConstants.AUTH_STATE, null)

            if (jsonString != null && !TextUtils.isEmpty(jsonString)) {
                try {
                    authState = AuthState.jsonDeserialize(jsonString)

                    if (!TextUtils.isEmpty(authState.idToken)) {
                        jwt = JWT(authState.idToken!!)
                    }

                } catch (_: JSONException) {
                }
            }
        }

        fun persistState() {
            getApplication(application).getSharedPreferences(
                ApiConstants.SHARED_PREFERENCES_NAME, Context
                    .MODE_PRIVATE
            )
                .edit()
                .putString(ApiConstants.AUTH_STATE, authState.jsonSerializeString())
                .commit()
        }

    }


    private fun initAuthService(application: AniGaFiApplication) {
        val appAuthConfiguration = AppAuthConfiguration.Builder()
            .setBrowserMatcher(
                BrowserAllowList(
                    VersionedBrowserMatcher.CHROME_CUSTOM_TAB,
                    VersionedBrowserMatcher.SAMSUNG_CUSTOM_TAB
                )
            )
            .build()

        authorizationService = AuthorizationService(
            getApplication(application),
            appAuthConfiguration
        )
    }


    fun login() {


        Timber.d("login")

        val secureRandom = SecureRandom()
        val bytes = ByteArray(64)
        secureRandom.nextBytes(bytes)

        val encoding = Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP
        val codeVerifier = Base64.encodeToString(bytes, encoding)

        val digest = MessageDigest.getInstance(ApiConstants.MESSAGE_DIGEST_ALGORITHM)
        val hash = digest.digest(codeVerifier.toByteArray())
        val codeChallenge = Base64.encodeToString(hash, encoding)

        val builder = AuthorizationRequest.Builder(
            authServiceConfig,
            ApiConstants.ANIME_CLIENT_ID,

            //TODO: maybe this is the error place check redirect url

            ApiConstants.ANIME_RESPONSE_TYPE, Uri.parse(ApiConstants.URL_AUTH_REDIRECT)
        )

        Timber.d("builder -> $builder")



        isAuthenticated = true
        isFirstRun = false

    }

    fun logOut() {
        isAuthenticated = false
    }

}