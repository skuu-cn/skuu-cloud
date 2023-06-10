package cn.skuu.framework.security.core.aop;

import cn.skuu.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.skuu.framework.common.exception.util.ServiceExceptionUtil;
import cn.skuu.framework.security.core.annotations.PreAuthenticated;
import cn.skuu.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Slf4j
public class PreAuthenticatedAspect {

    @Around("@annotation(preAuthenticated)")
    public Object around(ProceedingJoinPoint joinPoint, PreAuthenticated preAuthenticated) throws Throwable {
        if (SecurityFrameworkUtils.getLoginUser() == null) {
            throw ServiceExceptionUtil.exception(GlobalErrorCodeConstants.UNAUTHORIZED);
        }
        return joinPoint.proceed();
    }

}
