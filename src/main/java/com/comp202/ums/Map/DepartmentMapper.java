package com.comp202.ums.Map;

import com.comp202.ums.Dto.department.DepartmentDto;
import com.comp202.ums.Entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
    Department CreateDtoToEntity(DepartmentDto createDto);
}
