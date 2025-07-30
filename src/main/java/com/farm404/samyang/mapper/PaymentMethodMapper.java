package com.farm404.samyang.mapper;

import com.farm404.samyang.dto.PaymentMethodDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentMethodMapper {
    
    // 결제수단 조회
    PaymentMethodDTO selectPaymentMethodById(Integer paymentMethodId);
    
    // 사용자별 결제수단 목록 조회
    List<PaymentMethodDTO> selectPaymentMethodsByUserId(Integer userId);
    
    // 모든 결제수단 조회 (관리자용)
    List<PaymentMethodDTO> selectAllPaymentMethods();
    
    // 결제수단 타입별 조회
    List<PaymentMethodDTO> selectPaymentMethodsByType(@Param("userId") Integer userId, 
                                                       @Param("paymentType") String paymentType);
    
    // 결제수단 등록
    int insertPaymentMethod(PaymentMethodDTO paymentMethod);
    
    // 결제수단 수정
    int updatePaymentMethod(PaymentMethodDTO paymentMethod);
    
    // 결제수단 삭제
    int deletePaymentMethod(Integer paymentMethodId);
    
    // 사용자의 기본 결제수단 조회
    PaymentMethodDTO selectDefaultPaymentMethod(Integer userId);
    
    // 카드번호 중복 체크
    int checkCardNumberExists(@Param("cardNumber") String cardNumber, 
                              @Param("excludeId") Integer excludeId);
}