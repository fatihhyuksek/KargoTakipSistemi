package com.hepsijet.kargo2.Employee.Repositorys;

import com.hepsijet.kargo2.Employee.Models.EmployeeEntity;
import com.hepsijet.kargo2.JWT.auth.AuthenticationResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface EmployeeRepo extends JpaRepository<EmployeeEntity,Long> {

    @Query(value ="SELECT employee_id FROM employee where role='COURIER' and working_xdock= :xdock_number",nativeQuery = true)
    public Long getCourier(String xdock_number);

    @Query(value = "SELECT * FROM employee where employee_username = :username And employee_password= :password",nativeQuery = true)
    public EmployeeEntity getUserByUsernameAndPassword(String username,String password);

    @Query(value = "SELECT employee_id FROM employee where employee_username =  :username",nativeQuery = true)
    public Long getEmployeeIdByUsername(String username);

    //EmployeeEntity findByEmployee_username(String username);


    @Query(value = "select * from  employee where employee_username = :username",nativeQuery = true)
    Optional<EmployeeEntity> findByUsername(String username);



}
