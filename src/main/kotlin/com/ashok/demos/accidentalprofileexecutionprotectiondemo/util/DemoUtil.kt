package com.ashok.demos.accidentalprofileexecutionprotectiondemo.util

import java.net.InetAddress
import kotlin.experimental.and
import kotlin.experimental.or

class DemoUtil {

    companion object{

        fun isIPInRange(ip: String, range: String): Boolean {
            val ipRangeResult = calculateIPRange(range)

            if (ipRangeResult != null) {
                val (startIP, endIP) = ipRangeResult
                val ipAddressNumeric = ipToLong(ip)

                return ipAddressNumeric in startIP..endIP
            }

            return false
        }

        fun ipToLong(ipAddress: String): Long {
        val parts = InetAddress.getByName(ipAddress).address
        var result: Long = 0

        for (i in 0 until 4) {
            result = result shl 8 or (parts[i].toLong() and 0xFF)
        }

        return result
    }


        fun calculateIPRange(ipRange: String): Pair<Long, Long>? {

        try {
            val parts = ipRange.split("/")
            if (parts.size != 2) {
                return null  // Invalid input
            }

            val ipAddress = parts[0]
            var prefixLength = parts[1].toInt()

            val inetAddress = InetAddress.getByName(ipAddress).address
            val mask = ByteArray(4)

            for (i in 0 until 4) {
                mask[i] = (if (prefixLength > 8) 0xFF.toByte() else (0xFF shl (8 - prefixLength)).toByte()).toByte()
                prefixLength -= 8
            }

            val startIPBytes = ByteArray(4)
            val endIPBytes = ByteArray(4)

            for (i in 0 until 4) {
                startIPBytes[i] = (inetAddress[i] and  mask[i]).toByte()
                endIPBytes[i] = (inetAddress[i] or mask[i]).toByte()
            }

            val startIP = InetAddress.getByAddress(startIPBytes).hostAddress
            val endIP = InetAddress.getByAddress(endIPBytes).hostAddress

            return Pair(ipToLong(startIP), ipToLong(endIP))
        } catch (e: Exception) {
            println(e.stackTrace)

            return null  // Error occurred
        }
    }

    }
    fun main() {
        val ipRange = "192.168.1.0/24"
        val ipRangeResult = calculateIPRange(ipRange)

        if (ipRangeResult != null) {
            val (startIP, endIP) = ipRangeResult
            println("Start IP: $startIP")
            println("End IP: $endIP")
        } else {
            println("Invalid IP range.")
        }
    }
}
