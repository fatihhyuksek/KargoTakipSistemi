package com.hepsijet.kargo2.Xdock;

import com.hepsijet.kargo2.Xdock.Models.XdockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface XdockRepo extends JpaRepository <XdockEntity,Long> {


    @Query(value = "SELECT xdock_number FROM xdock WHERE district = :district",nativeQuery = true)
    public String getXdockWithDistrict(String district);

    @Query(value = "SELECT transfer_center FROM xdock WHERE  district= :district",nativeQuery = true)
    public String getXdockTransferCenterWithDistrict(String district);

    @Query(value = "SELECT xdock_number FROM xdock WHERE  district= :district",nativeQuery = true)
    public String getXdock_numberByBarcode(String district);
}
