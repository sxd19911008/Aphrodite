package com.aphrodite.insurance.config.plugins;

import com.aphrodite.insurance.config.JanusSwitchConfig;
import com.eredar.janus.core.annotation.Global;
import com.eredar.janus.core.constants.JanusConstants;
import com.eredar.janus.core.dto.JanusContext;
import com.eredar.janus.core.plugin.JanusPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 分流插件，全局使用
 */
@Global
@Component
public class SwitchJanusPlugin implements JanusPlugin {

    @Autowired
    private JanusSwitchConfig janusSwitchConfig;

    @Override
    public void switchBranch(JanusContext context) {
        String methodId = context.getMethodId();
        Map<String, JanusSwitchConfig.SwitchProperty> switchMap = janusSwitchConfig.getJanus();
        JanusSwitchConfig.SwitchProperty switchProperty = switchMap.get(methodId);
        if (switchProperty != null && switchProperty.getIsOpen() != null && switchProperty.getIsOpen()) {
            // 分流开关为true，走 primary 分支
            context.setMasterBranchName(JanusConstants.PRIMARY);
        } else {
            // 未配置分流开关或者分流开关为false，走 secondary 分支
            context.setMasterBranchName(JanusConstants.SECONDARY);
        }
    }
}
