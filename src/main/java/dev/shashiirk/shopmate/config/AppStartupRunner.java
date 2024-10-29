package dev.shashiirk.shopmate.config;

import dev.shashiirk.shopmate.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements CommandLineRunner {

    private final UserService userService;

    private final AppProperties appProperties;

    public AppStartupRunner(UserService userService, AppProperties appProperties) {
        this.userService = userService;
        this.appProperties = appProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create admin user here if it doesn't exist already
    }
}
