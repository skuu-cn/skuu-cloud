package cn.skuu.framework.common.util.object;

import cn.skuu.framework.common.pojo.PageParam;

/**
 * {@link PageParam} 工具类
 *
 * @author dcx
 */
public class PageUtils {

    public static int getStart(PageParam pageParam) {
        return (pageParam.getPageNo() - 1) * pageParam.getPageSize();
    }

}
