package cn.skuu.framework.datapermission.core.utils;

import cn.skuu.framework.datapermission.core.aop.DataPermissionContextHolder;
import cn.skuu.framework.datapermission.core.util.DataPermissionUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataPermissionUtilsTest {

    @Test
    public void testExecuteIgnore() {
        DataPermissionUtils.executeIgnore(() -> assertFalse(DataPermissionContextHolder.get().enable()));
    }

}
