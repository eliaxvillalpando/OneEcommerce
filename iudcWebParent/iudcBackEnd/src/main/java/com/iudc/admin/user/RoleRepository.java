package com.iudc.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iudc.common.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
