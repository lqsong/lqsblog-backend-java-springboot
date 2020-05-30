package cc.liqingsong.common.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
@EnableConfigurationProperties(JwtConfiguration.class)
public class JwtConfiguration {

    private Map<String,String> config;

    private Map<String,String> header;

}
