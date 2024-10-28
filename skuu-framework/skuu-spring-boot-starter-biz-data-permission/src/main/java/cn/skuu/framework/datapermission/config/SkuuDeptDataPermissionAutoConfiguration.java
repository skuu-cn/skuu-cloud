package cn.skuu.framework.datapermission.config;

import cn.skuu.framework.datapermission.core.rule.dept.DeptDataPermissionRule;
import cn.skuu.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import cn.skuu.framework.security.core.LoginUser;
import cn.skuu.framework.security.core.rpc.PermissionClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 基于部门的数据权限 AutoConfiguration
 *
 * @author dcx
 */
@AutoConfiguration
@ConditionalOnClass(LoginUser.class)
@ConditionalOnBean(value = DeptDataPermissionRuleCustomizer.class)
public class SkuuDeptDataPermissionAutoConfiguration {

    @Bean
    public DeptDataPermissionRule deptDataPermissionRule(PermissionClient permissionClient,
                                                         List<DeptDataPermissionRuleCustomizer> customizers) {

        // 创建 DeptDataPermissionRule 对象
        DeptDataPermissionRule rule = new DeptDataPermissionRule(permissionClient);
        // 补全表配置
        customizers.forEach(customizer -> customizer.customize(rule));
        return rule;
    }

}
