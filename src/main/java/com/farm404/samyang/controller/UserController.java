package com.farm404.samyang.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.service.UserService;

/**
 * 사용자 컨트롤러
 * 사용자 관련 웹 요청 처리
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 사용자 목록 페이지
     */
    @GetMapping("/list")
    public String userList(UserDTO searchCondition, Model model) {
        try {
            List<UserDTO> userList = userService.getUserList(searchCondition);
            int totalCount = userService.getUserCount(searchCondition);
            
            model.addAttribute("userList", userList);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("searchCondition", searchCondition);
            
            return "user/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자 목록을 불러오는 중 오류가 발생했습니다.");
            return "error";
        }
    }
    
    /**
     * 사용자 상세 페이지
     */
    @GetMapping("/detail/{사용자ID}")
    public String userDetail(@PathVariable Integer 사용자ID, Model model) {
        try {
            UserDTO user = userService.getUserById(사용자ID);
            model.addAttribute("user", user);
            return "user/detail";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    
    /**
     * 사용자 등록 폼 페이지
     */
    @GetMapping("/register")
    public String userRegisterForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user/register";
    }
    
    /**
     * 사용자 등록 처리
     */
    @PostMapping("/register")
    public String userRegister(UserDTO user, RedirectAttributes redirectAttributes) {
        try {
            // 비밀번호 확인 검증
            if (!user.get비밀번호().equals(user.get비밀번호확인())) {
                redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                redirectAttributes.addFlashAttribute("user", user);
                return "redirect:/user/register";
            }
            
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다.");
            return "redirect:/user/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/user/register";
        }
    }
    
    /**
     * 사용자 수정 폼 페이지
     */
    @GetMapping("/edit/{사용자ID}")
    public String userEditForm(@PathVariable Integer 사용자ID, Model model) {
        try {
            UserDTO user = userService.getUserById(사용자ID);
            model.addAttribute("user", user);
            return "user/edit";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    
    /**
     * 사용자 수정 처리
     */
    @PostMapping("/edit")
    public String userEdit(UserDTO user, RedirectAttributes redirectAttributes) {
        try {
            // 비밀번호 변경 시 확인 검증
            if (user.get비밀번호() != null && !user.get비밀번호().isEmpty()) {
                if (!user.get비밀번호().equals(user.get비밀번호확인())) {
                    redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                    return "redirect:/user/edit/" + user.get사용자ID();
                }
            }
            
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "사용자 정보가 수정되었습니다.");
            return "redirect:/user/detail/" + user.get사용자ID();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/edit/" + user.get사용자ID();
        }
    }
    
    /**
     * 사용자 삭제 처리
     */
    @PostMapping("/delete/{사용자ID}")
    public String userDelete(@PathVariable Integer 사용자ID, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(사용자ID);
            redirectAttributes.addFlashAttribute("successMessage", "사용자가 삭제되었습니다.");
            return "redirect:/user/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/list";
        }
    }
    
    /**
     * 로그인 폼 페이지
     */
    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }
    
    /**
     * 로그인 처리
     */
    @PostMapping("/login")
    public String login(@RequestParam String 이메일, @RequestParam String 비밀번호, 
                       RedirectAttributes redirectAttributes) {
        try {
            UserDTO user = userService.login(이메일, 비밀번호);
            redirectAttributes.addFlashAttribute("successMessage", user.get이름() + "님 환영합니다!");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/login";
        }
    }
    
    /**
     * 이메일 중복 체크 API
     */
    @GetMapping("/check-email")
    @ResponseBody
    public String checkEmail(@RequestParam String 이메일) {
        try {
            boolean exists = userService.checkEmailExists(이메일);
            return exists ? "exists" : "available";
        } catch (Exception e) {
            return "error";
        }
    }
}