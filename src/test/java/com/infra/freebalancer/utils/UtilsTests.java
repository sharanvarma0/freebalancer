package com.infra.freebalancer.utils;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UtilsTests {

    @Test
    public void testIfConfigLoaderLoadsConfigs() {
        Config testConfig = ConfigLoader.getConfig();
        assert(testConfig.getType()).equals("roundrobin");
    }
}

