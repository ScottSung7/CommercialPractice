
package com.example.chatapi.config.property;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources(
        @PropertySource("classpath:env-chat-api.properties")
)
public class ChatPropertyConfig {

}
