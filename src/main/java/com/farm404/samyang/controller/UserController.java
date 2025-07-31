package com.farm404.samyang.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.service.UserService;
import jakarta.servlet.http.HttpSession;

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
            // return "home";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자 목록을 불러오는 중 오류가 발생했습니다.");
            return "error";
        }
    }
    
    /**
     * 사용자 프로필 페이지
     */
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        UserDTO user = userService.getUserById(String.valueOf(currentUser.getUserId()));
        model.addAttribute("user", user);
        return "user/profile";
    }
    
    /**
     * 사용자 상세 페이지
     */
    @GetMapping("/detail/{사용자ID}")
    public String userDetail(@PathVariable Integer 사용자ID, Model model) {
        try {
            UserDTO user = userService.getUserById(String.valueOf(사용자ID));
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
            System.out.println("=== 회원가입 처리 시작 ===");
            System.out.println("사용자 정보: " + user.toString());
            
            // 비밀번호 확인 검증
            if (!user.getPassword().equals(user.getPasswordConfirm())) {
                redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                redirectAttributes.addFlashAttribute("user", user);
                return "redirect:/user/register";
            }
            
            userService.registerUser(user);
            System.out.println("회원가입 성공");
            redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다.");
            return "redirect:/user/list";
        } catch (Exception e) {
            System.err.println("회원가입 오류: " + e.getMessage());
            e.printStackTrace();
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
            UserDTO user = userService.getUserById(String.valueOf(사용자ID));
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
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                if (!user.getPassword().equals(user.getPasswordConfirm())) {
                    redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                    return "redirect:/user/edit/" + user.getUserId();
                }
            }
            
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "사용자 정보가 수정되었습니다.");
            return "redirect:/user/detail/" + user.getUserId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/edit/" + user.getUserId();
        }
    }
    
    /**
     * 사용자 삭제 처리
     */
    @PostMapping("/delete/{사용자ID}")
    public String userDelete(@PathVariable Integer 사용자ID, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(String.valueOf(사용자ID));
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
    public String login(@RequestParam String username, @RequestParam String password, 
                       HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            UserDTO user = userService.login(username, password);
            if (user != null) {
                session.setAttribute("user", user);
                redirectAttributes.addFlashAttribute("successMessage", user.getName() + "님 환영합니다!");
                return "redirect:/";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 올바르지 않습니다.");
                return "redirect:/user/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/login";
        }
    }
    
    /**
     * 로그아웃 처리
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    
    /**
     * 프로필 수정 처리
     */
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute UserDTO user, HttpSession session, 
                               RedirectAttributes redirectAttributes) {
        try {
            UserDTO currentUser = (UserDTO) session.getAttribute("user");
            if (currentUser == null) {
                return "redirect:/user/login";
            }
            
            // 현재 사용자 ID 설정
            user.setUserId(currentUser.getUserId());
            // TODO: [최소수정] loginId는 이제 email을 사용함
            user.setLoginId(currentUser.getLoginId()); // 로그인 ID는 변경 불가
            user.setRole(currentUser.getRole()); // 권한은 변경 불가
            
            userService.updateProfile(user);
            
            // 세션 정보 업데이트
            UserDTO updatedUser = userService.getUserById(String.valueOf(currentUser.getUserId()));
            session.setAttribute("user", updatedUser);
            
            redirectAttributes.addFlashAttribute("successMessage", "프로필이 수정되었습니다.");
            return "redirect:/user/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/profile";
        }
    }
    
    /**
     * 비밀번호 변경 처리
     */
    @PostMapping("/password/change")
    public String changePassword(@RequestParam String currentPassword,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            UserDTO currentUser = (UserDTO) session.getAttribute("user");
            if (currentUser == null) {
                return "redirect:/user/login";
            }
            
            // 새 비밀번호 확인
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("errorMessage", "새 비밀번호가 일치하지 않습니다.");
                return "redirect:/user/profile";
            }
            
            // 비밀번호 변경
            boolean success = userService.changePassword(String.valueOf(currentUser.getUserId()), currentPassword, newPassword);
            
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", "비밀번호가 변경되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "현재 비밀번호가 올바르지 않습니다.");
            }
            
            return "redirect:/user/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/profile";
        }
    }
    
    /**
     * 이메일 중복 체크 API
     */
    @GetMapping("/check-email")
    @ResponseBody
    public String checkEmail(@RequestParam String 이메일) {
        try {
            System.out.println("=== 이메일 중복 체크 시작 ===");
            System.out.println("요청 이메일: " + 이메일);
            System.out.println("UserService 호출 전");
            boolean exists = userService.checkEmailExists(이메일);
            System.out.println("UserService 호출 후 - 중복 체크 결과: " + exists);
            return exists ? "exists" : "available";
        } catch (Exception e) {
            System.err.println("=== MyBatis 매핑 오류 발생 ===");
            System.err.println("예외 타입: " + e.getClass().getSimpleName());
            System.err.println("예외 메시지: " + e.getMessage());
            System.err.println("상세 스택 트레이스:");
            e.printStackTrace();
            return "error";
        }
    }
}