package com.millet.mylibrary.test

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ThreadUtils
import com.millet.mylibrary.BuildConfig
import com.millet.mylibrary.R
import com.millet.mylibrary.mvvm.BaseBindingActivity
import com.millet.mylibrary.constant.ARouterPath
import com.millet.mylibrary.constant.Constant
import com.millet.mylibrary.databinding.ActivityProtocolBinding
import com.millet.mylibrary.ui.widget.HeadView
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_protocol.*
import wendu.dsbridge.CompletionHandler
import wendu.dsbridge.DWebView


/**
 * @author  zyl
 * @date  2020/6/29 6:02 PM
 */
@Route(path = ARouterPath.ProtocolWeb)
class ProtocolWebActivity : BaseBindingActivity<ActivityProtocolBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_protocol
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {
        // webSetting
        val webSetting = web_view.settings
        webSetting.javaScriptEnabled = true// JavaScript交互
        webSetting.useWideViewPort = true// 将图片调整到适合webview的大小
        webSetting.loadWithOverviewMode = true// 缩放至屏幕的大小
        webSetting.setSupportZoom(false) // 不支持缩放
        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE // 关闭webView缓存
        webSetting.javaScriptCanOpenWindowsAutomatically = true // 支持通过JS打开新窗口
        webSetting.loadsImagesAutomatically = true // 支持自动加载图片
        webSetting.domStorageEnabled = true
        webSetting.setAppCacheEnabled(false)
        webSetting.defaultTextEncodingName = "utf-8" // 设置编码格式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
        }
        if (BuildConfig.DEBUG) {
            // webview
            DWebView.setWebContentsDebuggingEnabled(true);
        }
        // 清除缓存
        web_view.clearCache(true)
        web_view.addJavascriptObject(JsApi(), null)
        web_view.disableJavascriptDialogBlock(true);

        val webViewUrl = intent.getStringExtra(Constant.Key.WebViewUrl)
        if (!TextUtils.isEmpty(webViewUrl)) {
            web_view.loadUrl(webViewUrl)
        }
    }

    override fun loadData(savedInstanceState: Bundle?) {

        web_view.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                p0: com.tencent.smtt.sdk.WebView?,
                p1: String?
            ): Boolean {
                web_view.loadUrl(p1)
                return true
            }

            override fun onReceivedSslError(
                p0: com.tencent.smtt.sdk.WebView?,
                p1: com.tencent.smtt.export.external.interfaces.SslErrorHandler?,
                p2: com.tencent.smtt.export.external.interfaces.SslError?
            ) {
                p1?.proceed()// 接受所有证书
                super.onReceivedSslError(p0, p1, p2)
            }

        }

        web_view.webChromeClient = object : WebChromeClient() {

            override fun onReceivedTitle(p0: com.tencent.smtt.sdk.WebView?, p1: String?) {
                findViewById<HeadView>(R.id.head_view).setMiddleString(p1)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web_view.canGoBack()) {
                web_view.goBack() //返回上一页面
                return true
            } else {
                finish()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    // js调用原生方法
    class JsApi {

        @JavascriptInterface
        public fun viewProjects(msg: Any, handler: CompletionHandler<String>) {
            ThreadUtils.getMainHandler().post {
                // 操作
                handler.complete()
            }
        }

    }

}