package com.mysoft.leistd.runtime;

import com.mysoft.leistd.constant.EnvironmentConst;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * 运行时环境信息
 */
@Service
@RequiredArgsConstructor
public class EnvRuntime {
    final Environment environment;

    /**
     * 是否开发环境
     */
    public boolean isDevelopment() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            return true;
        }
        return EnvironmentConst.DEVELOPMENT_ENVIRONMENT.contains(activeProfiles[0].toLowerCase());
    }
}
