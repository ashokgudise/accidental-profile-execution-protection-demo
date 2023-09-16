package com.ashok.demos.accidentalprofileexecutionprotectiondemo

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.context.ApplicationListener


class ProfileConfigEventListener :
    ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    override fun onApplicationEvent(event: ApplicationEnvironmentPreparedEvent) {
        val environment = event.environment
        val activeProfiles = environment.activeProfiles

        // Check if the production profile is active
        for (profile in activeProfiles) {
            if ("prod" == profile) {
                System.err.println("Production profile is active. Aborting application startup.")
                System.exit(1) // Terminate the application
            }
        }
    }
}