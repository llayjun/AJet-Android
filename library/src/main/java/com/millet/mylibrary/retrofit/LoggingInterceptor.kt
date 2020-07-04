package com.millet.mylibrary.retrofit

import com.blankj.utilcode.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit

/**
 * 日志拦截器
 */
class LoggingInterceptor : Interceptor {
    private val UTF8 = Charset.forName("UTF-8")

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // 这个 chain 里面包含了 request 和 response，所以你要什么都可以从这里拿
        // =========== 发送 ===========
        val request = chain.request()
        val requestBody = request.body
        var reqBodyStr: String? = null
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset = UTF8
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }
            reqBodyStr = buffer.readString(charset)
        }
        // 打印发送信息
        LogUtils.i(
            this.javaClass.simpleName, String.format(
                "发送请求 ===>  \nmethod：%s\nheaders: %s\nurl：%s\nbody：%s",
                request.method, request.headers, request.url, reqBodyStr
            )
        )

        //=========== 接收 ===========
        val startNs = System.nanoTime()
        val response = chain.proceed(request)
        val tookMs =
            TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        val responseBody = response.body
        var respBodyStr: String? = null
        val source = responseBody!!.source()
        source.request(Long.MAX_VALUE)
        val buffer = source.buffer()
        var charset = UTF8
        val contentType = responseBody.contentType()
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8)
            } catch (e: UnsupportedCharsetException) {
                e.printStackTrace()
            }
        }
        respBodyStr = buffer.clone().readString(charset)
        // 打印接收信息
        LogUtils.json(
            this.javaClass.simpleName, String.format(
                "收到响应 ===>  \n延迟时间：%s\n响应码：%s\n响应信息: %s\n响应body：%s",
                tookMs, response.code, response.message, respBodyStr
            )
        )
        return response
    }
}