<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    </div> <!-- container main-content 종료 -->

    <!-- Footer -->
    <footer class="mt-auto">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <h5><i class="fas fa-seedling"></i> 삼양 농업 플랫폼</h5>
                    <p class="mb-0">농업인을 위한 스마트 농업 관리 시스템</p>
                </div>
                <div class="col-md-6 text-md-end">
                    <p class="mb-0">
                        <small>
                            © 2024 삼양 농업 플랫폼. All rights reserved.<br>
                            MyBatis + Spring Boot + JSP 학습용 프로젝트
                        </small>
                    </p>
                </div>
            </div>
        </div>
    </footer>

    <script>
        // 공통 JavaScript 함수들
        
        // 삭제 확인
        function confirmDelete(message) {
            return confirm(message || '정말로 삭제하시겠습니까?');
        }
        
        // 폼 제출 전 검증
        function validateForm(formId) {
            const form = document.getElementById(formId);
            const requiredFields = form.querySelectorAll('[required]');
            
            for (let field of requiredFields) {
                if (!field.value.trim()) {
                    alert('모든 필수 항목을 입력해주세요.');
                    field.focus();
                    return false;
                }
            }
            return true;
        }
        
        // 날짜 포맷 함수
        function formatDate(dateString) {
            if (!dateString) return '';
            const date = new Date(dateString);
            return date.toLocaleDateString('ko-KR');
        }
        
        // 페이지 로드 시 실행
        $(document).ready(function() {
            // 테이블 hover 효과
            $('.table tbody tr').hover(
                function() { $(this).addClass('table-hover'); },
                function() { $(this).removeClass('table-hover'); }
            );
            
            // 자동으로 사라지는 알림 메시지
            setTimeout(function() {
                $('.alert').fadeOut('slow');
            }, 5000);
        });
    </script>

</body>
</html>