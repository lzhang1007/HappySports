package com.android.orient.sports.happy.encrypt

import java.util.*

/**
 * @PackageName com.android.orient.sports.happysports.encrypt
 * @date 2019/1/24 9:58
 * @author zhanglei
 */
private val KEYS = byteArrayOf(0x5B, 0x63, 0x72, 0x72, 0x63, 0x2E, 0x6A, 0x69, 0x61, 0x6E, 0x71, 0x2E, 0x63, 0x6F, 0x6D, 0x5D)

fun encodeSMS4toString(plaintext: String, key: String): String {
    return byte2hex(encodeSMS4(plaintext, key))
}

private fun byte2hex(b: ByteArray?): String {
    if (b == null) return ""
    var hs = ""
    var stemp: String
    for (n in b.indices) {
        stemp = Integer.toHexString(b[n].toInt() and 0XFF)
        if (stemp.length != 1) {
            hs += stemp
        } else {
            hs = hs + "0" + stemp
        }
    }
    return hs.toUpperCase()
}

fun encodeSMS4(plaintext: String?, key: String?): ByteArray? {
    var tempText = plaintext
    if (tempText == null || "" == tempText) {
        return null
    }
    for (i in tempText.toByteArray().size % 16..15) {
        tempText += " "
    }
    return encodeSMS4(tempText.toByteArray(), key?.let { getKeyBytes(it) } ?: KEYS)
}

private fun getKeyBytes(key: String): ByteArray {
    val bytes = key.toByteArray()
    val count = bytes.size % 16
    return if (count == 0) {
        Arrays.copyOfRange(bytes, 0, 16)
    } else {
        val newBytes = ByteArray(bytes.size + count)
        System.arraycopy(bytes, 0, newBytes, 0, bytes.size)
        System.arraycopy(randingBytes(count), 0, newBytes, bytes.size,
                count)
        Arrays.copyOfRange(newBytes, 0, 16)
    }
}

private fun randingBytes(count: Int): ByteArray {
    val bytes = ByteArray(count)
    for (i in 0 until count) {
        bytes[i] = 0x30
    }
    return bytes
}

/**
 * 不限明文长度的SMS4加密
 *
 * @param plaintext
 * @param key
 * @return
 */
private fun encodeSMS4(plaintext: ByteArray, key: ByteArray): ByteArray {
    val bytes = ByteArray(plaintext.size)
    var k = 0
    val plainLen = plaintext.size
    while (k + 16 <= plainLen) {
        val cellPlain = ByteArray(16)
        for (i in 0..15) {
            cellPlain[i] = plaintext[k + i]
        }
        val cellCipher = encode16(cellPlain, key)
        for (i in cellCipher.indices) {
            bytes[k + i] = cellCipher[i]
        }

        k += 16
    }

    return bytes
}

private fun encode16(plaintext: ByteArray, key: ByteArray): ByteArray {
    val cipher = ByteArray(16)
    SMSEncrypt().sms4(plaintext, 16, key, cipher, SMSEncrypt.ENCRYPT)
    return cipher
}
