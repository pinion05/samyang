package com.farm404.samyang.util;

import org.springframework.web.util.HtmlUtils;
import java.util.regex.Pattern;

/**
 * 보안 유틸리티 클래스
 * XSS, SQL Injection 방지를 위한 유틸리티 메소드 제공
 */
public class SecurityUtils {
    
    // SQL Injection 위험 패턴
    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
        "('.+--)|(--.*)|(\\|\\|)|(\\*|\\%|\\+|\\-|\\/|\\=|\\>|\\<|\\!|\\~|\\|)|(;)|(\\{|\\})|" +
        "(script)|(exec)|(execute)|(select)|(insert)|(update)|(delete)|(drop)|(create)|" +
        "(alter)|(grant)|(revoke)|(union)|(where)|(order by)|(group by)",
        Pattern.CASE_INSENSITIVE
    );
    
    // 위험한 HTML 태그 패턴
    private static final Pattern DANGEROUS_HTML_PATTERN = Pattern.compile(
        "<(script|iframe|object|embed|form|input|button|select|textarea|style|link|meta|base)",
        Pattern.CASE_INSENSITIVE
    );
    
    /**
     * XSS 공격 방지를 위한 HTML 이스케이프
     * @param input 원본 문자열
     * @return HTML 이스케이프된 문자열
     */
    public static String escapeHtml(String input) {
        if (input == null) {
            return null;
        }
        return HtmlUtils.htmlEscape(input);
    }
    
    /**
     * XSS 공격 방지를 위한 JavaScript 이스케이프
     * @param input 원본 문자열
     * @return JavaScript 이스케이프된 문자열
     */
    public static String escapeJavaScript(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("'", "\\'")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t")
                   .replace("<", "\\x3C")
                   .replace(">", "\\x3E")
                   .replace("/", "\\/");
    }
    
    /**
     * SQL Injection 위험 검사
     * @param input 검사할 문자열
     * @return SQL Injection 위험이 있으면 true
     */
    public static boolean hasSqlInjectionRisk(String input) {
        if (input == null) {
            return false;
        }
        return SQL_INJECTION_PATTERN.matcher(input).find();
    }
    
    /**
     * 위험한 HTML 태그 포함 여부 검사
     * @param input 검사할 문자열
     * @return 위험한 HTML 태그가 포함되어 있으면 true
     */
    public static boolean hasDangerousHtml(String input) {
        if (input == null) {
            return false;
        }
        return DANGEROUS_HTML_PATTERN.matcher(input).find();
    }
    
    /**
     * 안전한 문자열로 변환 (알파벳, 숫자, 일부 특수문자만 허용)
     * @param input 원본 문자열
     * @return 안전한 문자열
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        // 한글, 영문, 숫자, 공백, 일부 특수문자만 허용
        return input.replaceAll("[^가-힣a-zA-Z0-9\\s\\-_.,!?@]", "");
    }
    
    /**
     * 파일명 검증 및 정제
     * @param filename 원본 파일명
     * @return 안전한 파일명
     */
    public static String sanitizeFilename(String filename) {
        if (filename == null) {
            return null;
        }
        // 경로 구분자 제거
        filename = filename.replaceAll("[\\\\/:]", "_");
        // 위험한 확장자 검사
        String[] dangerousExtensions = {".jsp", ".jspx", ".php", ".asp", ".aspx", ".exe", ".bat", ".sh"};
        String lowerFilename = filename.toLowerCase();
        for (String ext : dangerousExtensions) {
            if (lowerFilename.endsWith(ext)) {
                filename = filename + ".txt"; // 위험한 확장자는 .txt 추가
            }
        }
        return filename;
    }
}