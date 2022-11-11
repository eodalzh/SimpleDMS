package com.example.simpledms.controller;

import com.example.simpledms.model.Dept;
import com.example.simpledms.model.Faq;
import com.example.simpledms.service.FaqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * packageName : com.example.simpledms.controller
 * fileName : FaqController
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
public class FaqController {

    @Autowired
    FaqService faqService; // @Autowired : 스프링부트가 가동될때 생성된 객체를 하나 받아오기

    //   frontend url(쿼리스트링방식) : ? 매개변수 전송방식 사용했으면 ----------> backend @RequestParam
    //   frontend url(파라메터방식) : /{} 매개변수 전송방식 사용했으면 ----------> backend @PathVariable
    @GetMapping("/faq")
    public ResponseEntity<Object> getFaqAll(@RequestParam(required = false) String title,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "3") int size
    ) {

        try {
//            페이지 객체 정의 ( page, size 값 설정 )
            Pageable pageable = PageRequest.of(page, size);

//            Page 객체 정의
            Page<Faq> faqPage;

//            findAll() 생량 해도 전체검색이 됨 :
//            why? like 검색시 매개변수가 ""이더라도 전체검색이 됨
            faqPage = faqService.findAllByTitleContaining(title, pageable);

//            맵 자료구조에 넣어서 전송
            Map<String, Object> response = new HashMap<>();
            response.put("faq", faqPage.getContent());
            response.put("currentPage", faqPage.getNumber());
            response.put("totalItems", faqPage.getTotalElements());
            response.put("totalPages", faqPage.getTotalPages());

            if (faqPage.isEmpty() == false) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/faq/all")
    public ResponseEntity<Object> removeAll() {

        try {
            faqService.removeAll();

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/faq")
    public ResponseEntity<Object> createFaq(@RequestBody Faq faq) {

        try {
            Faq faq2 = faqService.save(faq);

            return new ResponseEntity<>(faq2, HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/faq/{no}")
    public ResponseEntity<Object> getFaqId(@PathVariable int no) {

        try {
            Optional<Faq> optionalFaq = faqService.findById(no);

            if (optionalFaq.isPresent() == true) {
//                데이터 + 성공 메세지 전송
                return new ResponseEntity<>(optionalFaq.get(), HttpStatus.OK);
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

    @PutMapping("/faq/{no}")
    public ResponseEntity<Object> updateFaq(@PathVariable int no,
                                             @RequestBody Faq faq) {

        try {
            Faq faq2 = faqService.save(faq);

            return new ResponseEntity<>(faq2, HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/faq/deletion/{no}")
    public ResponseEntity<Object> deleteFaq(@PathVariable int no) {

        try {
            boolean bSuccess = faqService.removeById(no);

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





























