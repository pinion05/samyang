package com.farm404.samyang.controller;

import com.farm404.samyang.dto.CommentDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @GetMapping("/list")
    public String commentList(@RequestParam(required = false) Integer relatedId,
                             @RequestParam(required = false) String relatedType,
                             HttpSession session,
                             Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        List<CommentDTO> commentList;
        
        if (relatedId != null && relatedType != null) {
            // 특정 컨텐츠의 댓글 목록
            commentList = commentService.getCommentsByRelated(relatedId, relatedType);
            model.addAttribute("relatedId", relatedId);
            model.addAttribute("relatedType", relatedType);
        } else if ("ADMIN".equals(currentUser.getRole())) {
            // 관리자는 모든 댓글 조회
            commentList = commentService.getAllComments();
        } else {
            // 일반 사용자는 자신의 댓글만 조회
            Integer userId = Integer.parseInt(currentUser.getId());
            commentList = commentService.getCommentsByUserId(userId);
        }
        
        model.addAttribute("commentList", commentList);
        return "comment/list";
    }
    
    @GetMapping("/form")
    public String commentForm(@RequestParam(required = false) Integer commentId,
                             @RequestParam(required = false) Integer relatedId,
                             @RequestParam(required = false) String relatedType,
                             HttpSession session,
                             Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        CommentDTO comment;
        
        if (commentId != null) {
            // 수정 모드
            comment = commentService.getCommentById(commentId);
            if (comment == null) {
                return "redirect:/comment/list";
            }
            
            // 권한 체크
            Integer userId = Integer.parseInt(currentUser.getId());
            if (!commentService.hasPermission(commentId, userId, currentUser.getRole())) {
                return "redirect:/comment/list";
            }
            
            model.addAttribute("isEdit", true);
        } else {
            // 등록 모드
            comment = new CommentDTO();
            comment.setUserId(Integer.parseInt(currentUser.getId()));
            
            if (relatedId != null && relatedType != null) {
                comment.setRelatedId(relatedId);
                comment.setRelatedType(relatedType);
            }
            
            model.addAttribute("isEdit", false);
        }
        
        model.addAttribute("comment", comment);
        return "comment/form";
    }
    
    @PostMapping("/save")
    public String saveComment(@ModelAttribute CommentDTO comment,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        try {
            boolean success;
            
            if (comment.getCommentId() != null) {
                // 수정
                Integer userId = Integer.parseInt(currentUser.getId());
                if (!commentService.hasPermission(comment.getCommentId(), userId, currentUser.getRole())) {
                    redirectAttributes.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
                    return "redirect:/comment/list";
                }
                
                success = commentService.updateComment(comment);
            } else {
                // 등록
                comment.setUserId(Integer.parseInt(currentUser.getId()));
                success = commentService.registerComment(comment);
            }
            
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    comment.getCommentId() != null ? "댓글이 수정되었습니다." : "댓글이 등록되었습니다.");
                
                // 원래 페이지로 리다이렉트
                String redirectUrl = getRedirectUrl(comment.getRelatedType(), comment.getRelatedId());
                return "redirect:" + redirectUrl;
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "댓글 처리 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류: " + e.getMessage());
        }
        
        return "redirect:/comment/list";
    }
    
    @PostMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable Integer commentId,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        try {
            // 권한 체크
            Integer userId = Integer.parseInt(currentUser.getId());
            if (!commentService.hasPermission(commentId, userId, currentUser.getRole())) {
                redirectAttributes.addFlashAttribute("errorMessage", "삭제 권한이 없습니다.");
                return "redirect:/comment/list";
            }
            
            // 삭제 전 댓글 정보 가져오기 (리다이렉트용)
            CommentDTO comment = commentService.getCommentById(commentId);
            
            boolean success = commentService.deleteComment(commentId);
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", "댓글이 삭제되었습니다.");
                
                // 원래 페이지로 리다이렉트
                if (comment != null) {
                    String redirectUrl = getRedirectUrl(comment.getRelatedType(), comment.getRelatedId());
                    return "redirect:" + redirectUrl;
                }
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "삭제 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류: " + e.getMessage());
        }
        
        return "redirect:/comment/list";
    }
    
    // AJAX용 댓글 추가 API
    @PostMapping("/ajax/add")
    @ResponseBody
    public String addCommentAjax(@RequestBody CommentDTO comment,
                                HttpSession session) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "{\"success\":false,\"message\":\"로그인이 필요합니다.\"}";
        }
        
        try {
            comment.setUserId(Integer.parseInt(currentUser.getId()));
            boolean success = commentService.registerComment(comment);
            
            if (success) {
                return "{\"success\":true,\"message\":\"댓글이 등록되었습니다.\"}";
            } else {
                return "{\"success\":false,\"message\":\"댓글 등록에 실패했습니다.\"}";
            }
        } catch (Exception e) {
            return "{\"success\":false,\"message\":\"오류: " + e.getMessage() + "\"}";
        }
    }
    
    // AJAX용 댓글 목록 조회 API
    @GetMapping("/ajax/list")
    @ResponseBody
    public List<CommentDTO> getCommentsAjax(@RequestParam Integer relatedId,
                                           @RequestParam String relatedType) {
        return commentService.getCommentsByRelated(relatedId, relatedType);
    }
    
    // 관련 타입에 따른 리다이렉트 URL 생성
    private String getRedirectUrl(String relatedType, Integer relatedId) {
        switch (relatedType) {
            case "CROP":
                return "/crop/detail/" + relatedId;
            case "DIARY":
                return "/diary/detail/" + relatedId;
            case "REVIEW":
                return "/review/detail/" + relatedId;
            default:
                return "/comment/list";
        }
    }
}