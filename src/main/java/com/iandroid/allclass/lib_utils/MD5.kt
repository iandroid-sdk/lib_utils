package com.iandroid.allclass.lib_utils

import java.io.File
import java.io.FileInputStream
import java.math.BigInteger
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


    /**
     * 获取单个文件的MD5值
     * @param file 文件
     * @param radix  位 16 32 64
     * @return
     */

    fun getFileMD5(file: File, radix: Int = 16): String? {
        if (!file.isFile) {
            return null
        }
        var digest: MessageDigest? = null
        var inp: FileInputStream? = null
        val buffer = ByteArray(1024)
        var len: Int
        try {
            digest = MessageDigest.getInstance("MD5")
            inp = FileInputStream(file)
            while (inp.read(buffer, 0, 1024).also { len = it } != -1) {
                digest.update(buffer, 0, len)
            }
            inp.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        val bigInt = BigInteger(1, digest.digest())
        return bigInt.toString(radix)
    }
}
