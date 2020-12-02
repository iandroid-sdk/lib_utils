package com.iandroid.allclass.lib_utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by david on 2020/8/31.
 */
object MD5 {

    fun encodeMD5(str: String?): String {
        val buffBytes = encodeMD5Byte(str)
        return if (buffBytes == null) {
            ""
        } else {
            val encrypt = StringBuilder()
            for (i in buffBytes.indices) {
                if (buffBytes[i].toInt() and 255 < 16) {
                    encrypt.append("0")
                }
                encrypt.append((buffBytes[i].toLong() and 255).toString(16))
            }
            encrypt.toString()
        }
    }

    fun encode16BitMD5(str: String?): String {
        return encodeMD5(str).substring(8, 24)
    }

    private fun encodeMD5Byte(str: String?): ByteArray? {
        return if (str == null) {
            null
        } else {
            val strByte = str.toByteArray()
            try {
                val e = MessageDigest.getInstance("MD5")
                e.update(strByte)
                e.digest()
            } catch (var4: NoSuchAlgorithmException) {
                var4.printStackTrace()
                null
            }
        }
    }
}
