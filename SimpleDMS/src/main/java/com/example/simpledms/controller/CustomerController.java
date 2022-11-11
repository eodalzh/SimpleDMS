package com.example.simpledms.controller;

import com.example.simpledms.model.Customer;
import com.example.simpledms.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * packageName : com.example.simpledms.controller
 * fileName : CustomerController
 * author : ds
 * date : 2022-11-01
 * description : 부서 컨트롤러 쿼리 메소드
 * 요약 :
 * 쿼리 메소드 : 자동으로 사용자 정의 sql문을 생성해주는 함수
 * 목적 : 기본 함수보다 다양한 sql문을 작성하기 위해 사용
 * 사용법 : 함수이름으로 sql 문장을 작성함 ( Repository 안에 함수명만 작성 )
 * ex) JPA 클래스 == 대상 테이블
 * find == select
 * all  == *
 * by   == from
 * 속성  == where 컬럼
 * orderBy == order by
 * 속성 desc == 컬럼 desc
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         ds          최초 생성
 */
@Slf4j
// CORS 보안 : 기본적으로 한사이트에서 포트를 달리 사용못함
// @CrossOrigin(허용할_사이트주소(Vue 사이트주소:포트)) : CORS 보안을 허용해주는 어노테이션
//@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerService customerService; // @Autowired : 스프링부트가 가동될때 생성된 객체를 하나 받아오기

    //   frontend url(쿼리스트링방식) : ? 매개변수 전송방식 사용했으면 ----------> backend @RequestParam
    //   frontend url(파라메터방식) : /{} 매개변수 전송방식 사용했으면 ----------> backend @PathVariable
//    @RequestParam(required = false) : false 매개변수 값이 없어도 에러가 발생하지 않음
//                                      기본값은 required = true
//    @RequestParam(defaultValue = "값") : 매개변수에 값이 없으면 기본값을 설정함
    @GetMapping("/customer")
    public ResponseEntity<Object> getCustomerAll(@RequestParam(required = false) String email,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "3") int size
    ) {

        try {
//            페이지 객체 정의 ( page, size 값 설정 )
            Pageable pageable = PageRequest.of(page, size);

//            Page 객체 정의
            Page<Customer> customerPage;
            
//            findAll() 생량 해도 전체검색이 됨 : 
//            why? like 검색시 매개변수가 ""이더라도 전체검색이 됨
            customerPage = customerService.findAllByEmailContaining(email, pageable);

//            맵 자료구조에 넣어서 전송
            Map<String, Object> response = new HashMap<>();
            response.put("customer", customerPage.getContent());
            response.put("currentPage", customerPage.getNumber());
            response.put("totalItems", customerPage.getTotalElements());
            response.put("totalPages", customerPage.getTotalPages());

            if (customerPage.isEmpty() == false) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/customer/all")
    public ResponseEntity<Object> removeAll() {

        try {
            customerService.removeAll();

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/customer")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {

        try {
            Customer customer2 = customerService.save(customer);

            return new ResponseEntity<>(customer2, HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/{cid}")
    public ResponseEntity<Object> getCustomerId(@PathVariable int cid) {

        try {
            Optional<Customer> optionalCustomer = customerService.findById(cid);

            if (optionalCustomer.isPresent() == true) {
//                데이터 + 성공 메세지 전송
                return new ResponseEntity<>(optionalCustomer.get(), HttpStatus.OK);
            } else {
//                데이터 없음 메세지 전송(클라이언트)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            // 서버에러 발생 메세지 전송(클라이언트)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/customer/{cid}")
    public ResponseEntity<Object> updateCustomer(@PathVariable int cid,
                                                 @RequestBody Customer customer) {

        try {
            Customer customer2 = customerService.save(customer);

            return new ResponseEntity<>(customer2, HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/customer/deletion/{cid}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable int cid) {

        try {
            boolean bSuccess = customerService.removeById(cid);

            if (bSuccess == true) {
//                데이터 + 성공 메세지 전송
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
//                데이터 없음 메세지 전송(클라이언트)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            // 서버에러 발생 메세지 전송(클라이언트)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}





























