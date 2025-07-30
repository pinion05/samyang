package com.farm404.samyang.controller;

import com.farm404.samyang.dto.PaymentMethodDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentMethodController {
    
    @Autowired
    private PaymentMethodService paymentMethodService;
    
    @GetMapping("/list")
    public String paymentList(@RequestParam(required = false) String paymentType,
                             HttpSession session,
                             Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        List<PaymentMethodDTO> paymentList;
        
        // 관리자는 모든 결제수단 조회, 일반 사용자는 자신의 결제수단만 조회
        if ("ADMIN".equals(currentUser.getRole())) {
            paymentList = paymentMethodService.getAllPaymentMethods();
        } else {
            Integer userId = Integer.parseInt(currentUser.getId());
            if (paymentType != null && !paymentType.isEmpty()) {
                paymentList = paymentMethodService.getPaymentMethodsByType(userId, paymentType);
            } else {
                paymentList = paymentMethodService.getPaymentMethodsByUserId(userId);
            }
        }
        
        model.addAttribute("paymentList", paymentList);
        model.addAttribute("selectedType", paymentType);
        return "payment/list";
    }
    
    @GetMapping("/form")
    public String paymentForm(@RequestParam(required = false) Integer paymentId,
                             HttpSession session,
                             Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        PaymentMethodDTO payment;
        if (paymentId != null) {
            payment = paymentMethodService.getPaymentMethodById(paymentId);
            if (payment == null) {
                return "redirect:/payment/list";
            }
            
            // 권한 체크
            Integer userId = Integer.parseInt(currentUser.getId());
            if (!payment.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
                return "redirect:/payment/list";
            }
        } else {
            payment = new PaymentMethodDTO();
            payment.setUserId(Integer.parseInt(currentUser.getId()));
        }
        
        model.addAttribute("payment", payment);
        model.addAttribute("isEdit", paymentId != null);
        return "payment/form";
    }
    
    @PostMapping("/save")
    public String savePayment(@ModelAttribute PaymentMethodDTO payment,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        try {
            // 유효성 검사
            if (!paymentMethodService.isValidCardNumber(payment.getCardNumber())) {
                redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 카드번호입니다.");
                return "redirect:/payment/form";
            }
            
            if (!paymentMethodService.isValidExpiryDate(payment.getExpiryDate())) {
                redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 유효기간입니다.");
                return "redirect:/payment/form";
            }
            
            if (!paymentMethodService.isValidCvv(payment.getCvv(), payment.getPaymentType())) {
                redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 CVV입니다.");
                return "redirect:/payment/form";
            }
            
            boolean success;
            if (payment.getPaymentMethodId() != null) {
                // 수정
                PaymentMethodDTO existingPayment = paymentMethodService.getPaymentMethodById(payment.getPaymentMethodId());
                if (existingPayment == null) {
                    redirectAttributes.addFlashAttribute("errorMessage", "결제수단을 찾을 수 없습니다.");
                    return "redirect:/payment/list";
                }
                
                // 권한 체크
                Integer userId = Integer.parseInt(currentUser.getId());
                if (!existingPayment.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
                    redirectAttributes.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
                    return "redirect:/payment/list";
                }
                
                success = paymentMethodService.updatePaymentMethod(payment);
            } else {
                // 등록
                payment.setUserId(Integer.parseInt(currentUser.getId()));
                success = paymentMethodService.registerPaymentMethod(payment);
            }
            
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    payment.getPaymentMethodId() != null ? "결제수단이 수정되었습니다." : "결제수단이 등록되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "이미 등록된 카드번호입니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류: " + e.getMessage());
        }
        
        return "redirect:/payment/list";
    }
    
    @PostMapping("/delete/{paymentId}")
    public String deletePayment(@PathVariable Integer paymentId,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        try {
            PaymentMethodDTO payment = paymentMethodService.getPaymentMethodById(paymentId);
            if (payment == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "결제수단을 찾을 수 없습니다.");
                return "redirect:/payment/list";
            }
            
            // 권한 체크
            Integer userId = Integer.parseInt(currentUser.getId());
            if (!payment.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
                redirectAttributes.addFlashAttribute("errorMessage", "삭제 권한이 없습니다.");
                return "redirect:/payment/list";
            }
            
            boolean success = paymentMethodService.deletePaymentMethod(paymentId);
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", "결제수단이 삭제되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "삭제 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류: " + e.getMessage());
        }
        
        return "redirect:/payment/list";
    }
}