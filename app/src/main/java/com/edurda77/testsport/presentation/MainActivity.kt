package com.edurda77.testsport.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.telephony.TelephonyManager
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Chronometer
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edurda77.testsport.R
import com.edurda77.testsport.domain.model.Note
import com.edurda77.testsport.utils.SAVED_SETTINGS
import com.edurda77.testsport.utils.URL
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()
    private lateinit var webView: WebView
    private lateinit var errorTextView: TextView
    private lateinit var recycler: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var chronometer: Chronometer
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button
    private lateinit var progress: ProgressBar
    private lateinit var currentState: MaintActivityState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initElements()
        val sharedPref =
            this.getSharedPreferences(SAVED_SETTINGS, Context.MODE_PRIVATE)
        val sharedUrl = sharedPref.getString(URL, "")
        currentState = MaintActivityState.Loading
        viewModel.getFromLocal(
            pathUrl = sharedUrl ?: "",
            checkedInternetConnection = checkedInternetConnection(),
            checkSim = checkSim()
        )
        viewModel.showData.observe(this) { state ->
            when (state) {
                is MaintActivityState.SuccessConnect -> {
                    webView.isVisible = true
                    errorTextView.isVisible = false
                    recycler.isVisible = false
                    fab.isVisible = false
                    chronometer.isVisible = false
                    startButton.isVisible = false
                    stopButton.isVisible = false
                    resetButton.isVisible = false
                    progress.isVisible = false
                    currentState = state
                    initWebView(savedInstanceState, state.remoteData.urlPath)
                    val editor = sharedPref.edit()
                    editor.putString(URL, state.remoteData.urlPath)
                    editor.apply()
                }
                is MaintActivityState.NoInternet -> {
                    webView.isVisible = false
                    recycler.isVisible = false
                    errorTextView.isVisible = true
                    fab.isVisible = false
                    chronometer.isVisible = false
                    startButton.isVisible = false
                    stopButton.isVisible = false
                    resetButton.isVisible = false
                    progress.isVisible = false
                    currentState = state
                    errorTextView.text = state.message
                }
                is MaintActivityState.Loading -> {
                    webView.isVisible = false
                    recycler.isVisible = false
                    errorTextView.isVisible = true
                    fab.isVisible = false
                    chronometer.isVisible = false
                    fab.isVisible = false
                    startButton.isVisible = false
                    stopButton.isVisible = false
                    resetButton.isVisible = false
                    progress.isVisible = true
                    currentState = state
                }
                is MaintActivityState.Error -> {
                    webView.isVisible = false
                    recycler.isVisible = false
                    errorTextView.isVisible = true
                    errorTextView.text = state.message
                    fab.isVisible = false
                    chronometer.isVisible = false
                    fab.isVisible = false
                    startButton.isVisible = false
                    stopButton.isVisible = false
                    resetButton.isVisible = false
                    progress.isVisible = false
                    currentState = state
                }
                is MaintActivityState.NoteWork -> {
                    recycler.isVisible = true
                    webView.isVisible = false
                    errorTextView.isVisible = false
                    fab.isVisible = true
                    chronometer.isVisible = true
                    startButton.isVisible = true
                    stopButton.isVisible = true
                    resetButton.isVisible = true
                    progress.isVisible = false
                    currentState = state
                    setRecycledView(state.notes)
                }
            }
        }
        fab.setOnClickListener {
            val dialog = DialogAddNote()
            val manager = supportFragmentManager
            dialog.show(manager, "myDialog")
        }
        startButton.setOnClickListener {
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
        }

        stopButton.setOnClickListener {
            chronometer.stop()
        }

        resetButton.setOnClickListener {
            chronometer.base = SystemClock.elapsedRealtime()
        }

    }

    private fun setRecycledView(notes: List<Note>) {
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager
            .VERTICAL, false)
        val adapter = NoteAdapter()
        adapter.submitList(notes)
        recycler.adapter = adapter
    }

    private fun checkedInternetConnection() : Boolean {
        var result = false
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(savedInstanceState: Bundle?, url:String) {
        webView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        }
        else {
            webView.loadUrl(url)
        }
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        webSettings.javaScriptEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        webSettings.setSupportZoom(false)
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true

    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            when (currentState) {
                is MaintActivityState.SuccessConnect -> {

                }
                else -> {
                    super.onBackPressed()
                }
            }

        }
    }

    private fun initElements() {
        supportActionBar?.hide()
        errorTextView = findViewById(R.id.errorTv)
        webView = findViewById(R.id.webView)
        recycler = findViewById(R.id.noteRv)
        fab = findViewById(R.id.fab)
        chronometer = findViewById(R.id.chronometer)
        startButton = findViewById(R.id.start_bt)
        stopButton = findViewById(R.id.stop_bt)
        resetButton = findViewById(R.id.reset_bt)
        progress = findViewById(R.id.progress)
    }

    private fun checkSim(): Boolean {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.simState != TelephonyManager.SIM_STATE_ABSENT
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }
}