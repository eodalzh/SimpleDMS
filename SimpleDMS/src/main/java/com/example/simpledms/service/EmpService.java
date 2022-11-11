package com.example.simpledms.service;

import com.example.simpledms.model.Emp;
import com.example.simpledms.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.example.simpledms.service
 * fileName : EmpService
 * author : ds
 * date : 2022-11-02
 * description : 부서 업무 서비스 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         ds          최초 생성
 */
@Service
public class EmpService {

    @Autowired
    EmpRepository empRepository;

    //    전체 조회 함수
    public Page<Emp> findAll(Pageable pageable) {
        Page<Emp> list = empRepository.findAll(pageable);

        return list;
    }

    //    전체 삭제 함수
    public void removeAll() {
        empRepository.deleteAll(); // 전체 삭제
    }

    //    사원 정보 저장/수정 함수
    public Emp save(Emp emp) {
        Emp emp2 = empRepository.save(emp);

        return emp2;
    }

    //    사원번호로 조회하는 함수
//    Optional 객체 : null 방지 객체
//    주요 함수 : .get() - 안에 있는 객체 꺼내기 함수
//              .isPresent() - 안에 객체 있어요? 있으면 true, 없으면 false
    public Optional<Emp> findById(int eno) {
//        findById(기본키속성)
        Optional<Emp> optionalEmp = empRepository.findById(eno);

        return optionalEmp;
    }

    //    사원번호(eno)로 삭제하는 함수
    public boolean removeById(int eno) {
//        existById(기본키) 있으면 삭제 실행
        if (empRepository.existsById(eno) == true) {
            empRepository.deleteById(eno);
            return true;
        }

//        없으면 그냥 false 리턴
        return false;
    }

    //    ename like 검색 함수
    public Page<Emp> findAllByEnameContaining(String ename, Pageable pageable) {
        Page<Emp> page = empRepository.findAllByEnameContaining(ename, pageable);

        return page;
    }
}























