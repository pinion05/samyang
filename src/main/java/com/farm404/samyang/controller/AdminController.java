package com.farm404.samyang.controller;

import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String userList(@RequestParam(defaultValue = "1") int page,
                          @RequestParam(required = false) String keyword,
                          @RequestParam(required = false) String role,
                          HttpSession session,
                          Model model) {
        
        // 관리자 권한 체크
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/user/login";
        }

        // 페이지당 항목 수
        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        // 사용자 목록 조회 (검색 조건 포함)
        List<UserDTO> userList = userService.getAllUsers();
        
        // 검색 필터링 (실제로는 서비스나 매퍼에서 처리하는 것이 좋음)
        if (keyword != null && !keyword.isEmpty()) {
            userList = userList.stream()
                .filter(user -> user.getName().contains(keyword) || 
                              user.getUsername().contains(keyword) || 
                              user.getEmail().contains(keyword))
                .toList();
        }
        
        if (role != null && !role.isEmpty()) {
            userList = userList.stream()
                .filter(user -> user.getRole().equals(role))
                .toList();
        }

        // 전체 사용자 수
        int totalCount = userList.size();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 페이징 처리
        int fromIndex = Math.min(offset, userList.size());
        int toIndex = Math.min(offset + pageSize, userList.size());
        List<UserDTO> pagedList = userList.subList(fromIndex, toIndex);

        model.addAttribute("userList", pagedList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        
        return "user/users";
    }
}