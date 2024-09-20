package cn.skuu.member.convert.user;

import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.member.api.user.dto.MemberUserRespDTO;
import cn.skuu.member.controller.admin.user.vo.MemberUserRespVO;
import cn.skuu.member.controller.admin.user.vo.MemberUserUpdateReqVO;
import cn.skuu.member.controller.app.user.vo.AppMemberUserInfoRespVO;
import cn.skuu.member.convert.address.AddressConvert;
import cn.skuu.member.dal.dataobject.group.MemberGroupDO;
import cn.skuu.member.dal.dataobject.level.MemberLevelDO;
import cn.skuu.member.dal.dataobject.tag.MemberTagDO;
import cn.skuu.member.dal.dataobject.user.MemberUserDO;
import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static cn.skuu.framework.common.util.collection.CollectionUtils.convertList;
import static cn.skuu.framework.common.util.collection.CollectionUtils.convertMap;

@Mapper(uses = {AddressConvert.class})
public interface MemberUserConvert {

    MemberUserConvert INSTANCE = Mappers.getMapper(MemberUserConvert.class);

    AppMemberUserInfoRespVO convert(MemberUserDO bean);

    @Mapping(source = "level", target = "level")
    @Mapping(source = "bean.experience", target = "experience")
    AppMemberUserInfoRespVO convert(MemberUserDO bean, MemberLevelDO level);

    MemberUserRespDTO convert2(MemberUserDO bean);

    List<MemberUserRespDTO> convertList2(List<MemberUserDO> list);

    @Mapping(source = "tagIds", target = "tagIds", qualifiedByName = "tagIdsToString")
    MemberUserDO convert(MemberUserUpdateReqVO bean);

    PageResult<MemberUserRespVO> convertPage(PageResult<MemberUserDO> page);

    @Mapping(source = "areaId", target = "areaName", qualifiedByName = "convertAreaIdToAreaName")
    @Mapping(source = "tagIds", target = "tagIds", qualifiedByName = "tagIdsStrToList")
    MemberUserRespVO convert03(MemberUserDO bean);

    @Named("tagIdsToString")
    default String tagIdsToString(List<Long> tagIds) {
        StringBuilder str = new StringBuilder();
        for (Long tagId : tagIds) {
            str.append(tagId).append(",");
        }
        return str.deleteCharAt(str.length() - 1).toString();
    }

    @Named("tagIdsStrToList")
    default List<Long>  tagIdsStrToList(String tagIds) {
        String[] split = tagIds.split(",");
        List<@Nullable Long> res = Lists.newArrayList();
        for (String s : split) {
            res.add(Long.parseLong(s));
        }
        return res;
    }

    default PageResult<MemberUserRespVO> convertPage(PageResult<MemberUserDO> pageResult,
                                                     List<MemberTagDO> tags,
                                                     List<MemberLevelDO> levels,
                                                     List<MemberGroupDO> groups) {
        PageResult<MemberUserRespVO> result = convertPage(pageResult);
        // 处理关联数据
        Map<Long, String> tagMap = convertMap(tags, MemberTagDO::getId, MemberTagDO::getName);
        Map<Long, String> levelMap = convertMap(levels, MemberLevelDO::getId, MemberLevelDO::getName);
        Map<Long, String> groupMap = convertMap(groups, MemberGroupDO::getId, MemberGroupDO::getName);
        // 填充关联数据
        result.getList().forEach(user -> {
            user.setTagNames(convertList(user.getTagIds(), tagMap::get));
            user.setLevelName(levelMap.get(user.getLevelId()));
            user.setGroupName(groupMap.get(user.getGroupId()));
        });
        return result;
    }

}
