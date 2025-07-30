<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
보안을 위한 커스텀 태그 라이브러리
XSS 방지를 위한 HTML 이스케이프 함수 제공
--%>

<%-- HTML 이스케이프 함수 --%>
<c:set var="escapeHtml" value="${fn:escapeXml}" />

<%-- JavaScript 이스케이프 함수 --%>
<script>
function escapeJs(str) {
    if (!str) return '';
    return str.replace(/\\/g, '\\\\')
              .replace(/"/g, '\\"')
              .replace(/'/g, "\\'")
              .replace(/\n/g, '\\n')
              .replace(/\r/g, '\\r')
              .replace(/\t/g, '\\t')
              .replace(/</g, '\\x3C')
              .replace(/>/g, '\\x3E')
              .replace(/\//g, '\\/');
}
</script>