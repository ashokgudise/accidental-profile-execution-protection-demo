package com.ashok.demos.accidentalprofileexecutionprotectiondemo

import com.ashok.demos.accidentalprofileexecutionprotectiondemo.config.ProfileConfigEventListener
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class AccidentalProfileExecutionProtectionDemoApplication

fun main(args: Array<String>) {
    runApplication<AccidentalProfileExecutionProtectionDemoApplication>(*args)
}
@Bean
fun profileConfigEventListener(): ProfileConfigEventListener? = ProfileConfigEventListener()
