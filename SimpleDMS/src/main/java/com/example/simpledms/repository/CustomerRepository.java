package com.example.simpledms.repository;

import com.example.simpledms.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName : com.example.simpledms.repository
 * fileName : CustomerRepository
 * author : ds
 * date : 2022-11-08
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-08         ds          최초 생성
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Page<Customer> findAllByEmailContaining(String email, Pageable pageable);
}
