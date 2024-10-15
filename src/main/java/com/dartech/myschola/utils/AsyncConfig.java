package com.dartech.myschola.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {
    // You can leave this empty unless you want to customize the thread pool for async tasks.
}
