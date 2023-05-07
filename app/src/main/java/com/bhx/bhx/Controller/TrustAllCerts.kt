package com.bhx.bhx.Controller

import java.security.cert.X509Certificate
import javax.net.ssl.*

class TrustAllCerts : X509TrustManager {

    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}

    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}

    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

    companion object {
        fun createSSLSocketFactory(): SSLSocketFactory {
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf<TrustManager>(TrustAllCerts()), null)
            return sslContext.socketFactory
        }

        fun TrustAllHostnameVerifier(): HostnameVerifier {
            return object : HostnameVerifier {
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    return true
                }
            }
        }
    }
}