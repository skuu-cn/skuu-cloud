package cn.skuu.framework.web.core.filter;

import cn.hutool.core.util.StrUtil;
import cn.skuu.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.framework.common.util.servlet.ServletUtils;
import cn.skuu.framework.web.core.util.WebFrameworkUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 演示 Filter，禁止用户发起写操作，避免影响测试数据
 *
 * @author skuu
 */
public class DemoFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String method = request.getMethod();
        return !StrUtil.equalsAnyIgnoreCase(method, "POST", "PUT", "DELETE")  // 写操作时，不进行过滤率
                || WebFrameworkUtils.getLoginUserId(request) == null; // 非登录用户时，不进行过滤
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        // 直接返回 DEMO_DENY 的结果。即，请求不继续
        ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodeConstants.DEMO_DENY));
    }

}
