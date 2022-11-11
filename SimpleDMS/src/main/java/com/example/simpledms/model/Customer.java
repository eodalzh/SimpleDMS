package com.example.simpledms.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * packageName : com.example.simpledms.model
 * fileName : Customer
 * author : ds
 * date : 2022-11-08
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-08         ds          최초 생성
 */
@Entity
@Table(name = "TB_CUSTOMER")
@SequenceGenerator(
        name = "SQ_CUSTOMER_GENERATOR"
        , sequenceName = "SQ_CUSTOMER"
        , initialValue = 1
        , allocationSize = 1
)
// lombok 라이브러리 어노테이션
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
// null 무시 spl 문 자동생성하는 어노테이션
@DynamicInsert
@DynamicUpdate
// soft delete : 삭제하는 척만 하기 (화면에서는 안보이고, DB는 데이터를 삭제하지 않음)
// 법정 의무 보관 기간을 위해 실제 데이터를 삭제하지 않음
// 사용법 1) @SQLDelete(sql="update문") : delete 문이 실행되지 않고, 매개변수의 update 문이 실행되게함
//       2) @Where(clause="강제조건") : 대상클래스 붙이면 sql 문 실행 시 강제 조건이 붙어 실행 됨
@Where(clause = "DELETE_YN = 'N'")
@SQLDelete(sql="UPDATE TB_CUSTOMER SET DELETE_YN = 'Y', DELETE_TIME = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE CID = ?")
public class Customer extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "SQ_CUSTOMER_GENERATOR"
    )
    @Column(columnDefinition = "NUMBER")
    private Integer cid;

    @Column(columnDefinition = "VARCHAR2(255)")
    private String firstName;

    @Column(columnDefinition = "VARCHAR2(255)")
    private String lastName;

    @Column(columnDefinition = "VARCHAR2(255)")
    private String email;

    @Column(columnDefinition = "VARCHAR2(255)")
    private String phone;
}
