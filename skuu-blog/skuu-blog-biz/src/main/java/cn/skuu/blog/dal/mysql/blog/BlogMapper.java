package cn.skuu.blog.dal.mysql.blog;

import cn.skuu.blog.controller.admin.blog.vo.BlogPageReqVO;
import cn.skuu.blog.dal.dataobject.blog.BlogDO;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.mybatis.core.mapper.BaseMapperX;
import cn.skuu.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 博客 Mapper
 *
 * @author skuu
 */
@Mapper
public interface BlogMapper extends BaseMapperX<BlogDO> {

    default PageResult<BlogDO> selectPage(BlogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BlogDO>()
                .eqIfPresent(BlogDO::getSquareId, reqVO.getSquareId())
                .eqIfPresent(BlogDO::getTopicId, reqVO.getTopicId())
                .eqIfPresent(BlogDO::getCategary, reqVO.getCategary())
                .eqIfPresent(BlogDO::getBlogType, reqVO.getBlogType())
                .eqIfPresent(BlogDO::getContent, reqVO.getContent())
                .eqIfPresent(BlogDO::getResources, reqVO.getResources())
                .eqIfPresent(BlogDO::getAddressId, reqVO.getAddressId())
                .eqIfPresent(BlogDO::getShareType, reqVO.getShareType())
                .betweenIfPresent(BlogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BlogDO::getId));
    }

}