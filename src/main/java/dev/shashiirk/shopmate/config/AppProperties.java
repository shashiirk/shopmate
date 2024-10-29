package dev.shashiirk.shopmate.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration class that binds external properties related to the application.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    public final Security security = new Security();

    @Getter
    @Setter
    public static class Security {

        private Jwt jwt;

        @Getter
        @Setter
        public static class Jwt {

            private String secret;
            private long tokenValidityInMilliSeconds;
        }
    }
}
