package com.javalabs.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.javalabs.dto.GetUserResponse;
import com.javalabs.model.Project;
import com.javalabs.model.User;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    @Mapping(source ="manager.id", target ="managerId")
    @Mapping(source = "manager.username", target = "managerUsername")
    @Mapping(source = "projects", target = "assignedProjects")
    GetUserResponse mapToResponse(User user);
    
    List<GetUserResponse> mapToResponseList(List<User> users);

	/*
	 * @Mapping(source = "targetName", target = "sourceName")
	 * 
	 * @Mapping(source = "targetAge", target = "sourceAge")
	 * 
	 * @Mapping(source = "targetStreet", target = "sourceAddress.street")
	 * 
	 * @Mapping(source = "targetCity", target = "sourceAddress.city") User
	 * mapToBean(GetUserResponse response);
	 */
    
    default Set<String> mapProjectsToNames(Set<Project> projects) {
        return projects.stream()
                .map(Project::getName)
                .collect(Collectors.toSet());
    }
}
