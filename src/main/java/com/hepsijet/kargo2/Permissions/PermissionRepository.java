package com.hepsijet.kargo2.Permissions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity,Long> {

    @Query(value = "select role from permissions where endpoint = :url ",nativeQuery = true)
    public String checkPermission(String url);
}
