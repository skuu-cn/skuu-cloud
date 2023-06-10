package cn.skuu.system.convert.dept;

import cn.skuu.system.api.dept.dto.DeptRespDTO;
import cn.skuu.system.controller.admin.dept.vo.dept.DeptCreateReqVO;
import cn.skuu.system.controller.admin.dept.vo.dept.DeptRespVO;
import cn.skuu.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import cn.skuu.system.controller.admin.dept.vo.dept.DeptUpdateReqVO;
import cn.skuu.system.dal.dataobject.dept.DeptDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DeptConvert {

    DeptConvert INSTANCE = Mappers.getMapper(DeptConvert.class);

    List<DeptRespVO> convertList(List<DeptDO> list);

    List<DeptSimpleRespVO> convertList02(List<DeptDO> list);

    DeptRespVO convert(DeptDO bean);

    DeptDO convert(DeptCreateReqVO bean);

    DeptDO convert(DeptUpdateReqVO bean);

    List<DeptRespDTO> convertList03(List<DeptDO> list);

    DeptRespDTO convert03(DeptDO bean);

}
