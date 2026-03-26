package com.aphrodite.insurance.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

@RefreshScope
@Component
@ConfigurationProperties(prefix = "compare")
@Data
public class JanusSwitchConfig {

    private Map<String, SwitchProperty> janus;

    /**
     * 分流比对开关配置项
     */
    @Data
    public static class SwitchProperty {
        /*
         * 分流开关：true-走master分支；false-走secondary分支
         * 未配置的情况下，默认为 false。但是可以通过janus配置默认选项。
         */
        private Boolean isOpen;
    }
}
