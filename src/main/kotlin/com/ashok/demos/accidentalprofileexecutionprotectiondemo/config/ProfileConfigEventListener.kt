package com.ashok.demos.accidentalprofileexecutionprotectiondemo.config

import com.ashok.demos.accidentalprofileexecutionprotectiondemo.util.DemoUtil
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.context.ApplicationListener
import org.springframework.util.StringUtils
import java.net.InetAddress

class ProfileConfigEventListener :
    ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    override fun onApplicationEvent(event: ApplicationEnvironmentPreparedEvent) {
        val environment = event.environment
        val activeProfiles = environment.activeProfiles

        val ipRange = System.getProperty("PROD_IP_RANGE")

        println("Inside ProfileConfigEventListener -> onApplicationEvent")

        // Check if the production profile is active
        for (profile in activeProfiles) {

            if ("prod" == profile) {

                val inetAddress = InetAddress.getLocalHost()
                val hostIp = inetAddress.hostAddress

                if (!StringUtils.hasText(ipRange)
                            or (StringUtils.hasText(ipRange)
                                    and !DemoUtil.isIPInRange(hostIp, ipRange))) {

                    if(!StringUtils.hasText(ipRange)) println("PROD_IP_RANGE is not set.")

                    System.err.println("IP Address is not in specified range")

                    System.err.println("Value of PROD_IP_RANGE Given: $ipRange and Your IP Address is: $hostIp")

                    System.exit(1) // Terminate the application

                    System.err.println("Production profile is active. Aborting application startup.")
                }

                println("All Set to Run your App in Prod")
            }
        }
    }
}