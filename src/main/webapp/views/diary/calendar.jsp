<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.YearMonth" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.farm404.samyang.dto.FarmingDiaryDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>영농일지 달력 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .calendar-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .calendar-header {
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #4CAF50;
        }
        
        .calendar-header h1 {
            color: #2c3e50;
            margin: 0 0 10px 0;
            font-size: 28px;
        }
        
        .calendar-nav {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        
        .month-display {
            font-size: 24px;
            font-weight: 600;
            color: #333;
        }
        
        .nav-buttons {
            display: flex;
            gap: 10px;
        }
        
        .btn {
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: all 0.3s;
            border: none;
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            gap: 5px;
        }
        
        .btn-primary {
            background-color: #4CAF50;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #45a049;
        }
        
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        
        .btn-nav {
            background-color: #2196F3;
            color: white;
            padding: 8px 16px;
        }
        
        .btn-nav:hover {
            background-color: #0b7dda;
        }
        
        .calendar-grid {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        
        .calendar-weekdays {
            display: grid;
            grid-template-columns: repeat(7, 1fr);
            background-color: #4CAF50;
            color: white;
            font-weight: 600;
        }
        
        .weekday {
            padding: 15px;
            text-align: center;
            border-right: 1px solid rgba(255,255,255,0.2);
        }
        
        .weekday:last-child {
            border-right: none;
        }
        
        .calendar-days {
            display: grid;
            grid-template-columns: repeat(7, 1fr);
        }
        
        .calendar-day {
            min-height: 120px;
            border: 1px solid #e9ecef;
            padding: 10px;
            position: relative;
            transition: background-color 0.3s;
        }
        
        .calendar-day:hover {
            background-color: #f8f9fa;
        }
        
        .day-number {
            font-weight: 600;
            color: #333;
            margin-bottom: 5px;
        }
        
        .other-month .day-number {
            color: #ccc;
        }
        
        .today {
            background-color: #e8f5e9;
        }
        
        .today .day-number {
            color: #4CAF50;
        }
        
        .diary-entries {
            margin-top: 5px;
        }
        
        .diary-entry {
            font-size: 12px;
            padding: 3px 6px;
            margin-bottom: 3px;
            border-radius: 3px;
            background-color: #e3f2fd;
            color: #1976d2;
            cursor: pointer;
            transition: all 0.2s;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        
        .diary-entry:hover {
            background-color: #bbdefb;
            transform: translateX(2px);
        }
        
        .diary-entry.파종 { background-color: #fff3e0; color: #e65100; }
        .diary-entry.관수 { background-color: #e1f5fe; color: #0277bd; }
        .diary-entry.시비 { background-color: #f3e5f5; color: #6a1b9a; }
        .diary-entry.방제 { background-color: #fce4ec; color: #c2185b; }
        .diary-entry.수확 { background-color: #e8f5e9; color: #2e7d32; }
        .diary-entry.기타 { background-color: #f5f5f5; color: #616161; }
        
        .add-diary-btn {
            position: absolute;
            bottom: 5px;
            right: 5px;
            width: 20px;
            height: 20px;
            background-color: #4CAF50;
            color: white;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 14px;
            cursor: pointer;
            opacity: 0;
            transition: opacity 0.3s;
        }
        
        .calendar-day:hover .add-diary-btn {
            opacity: 1;
        }
        
        .legend {
            margin-top: 30px;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 8px;
        }
        
        .legend-title {
            font-weight: 600;
            margin-bottom: 10px;
            color: #333;
        }
        
        .legend-items {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
        }
        
        .legend-item {
            display: flex;
            align-items: center;
            gap: 5px;
        }
        
        .legend-color {
            width: 20px;
            height: 20px;
            border-radius: 3px;
        }
        
        @media (max-width: 768px) {
            .calendar-nav {
                flex-direction: column;
                gap: 15px;
            }
            
            .calendar-day {
                min-height: 80px;
                padding: 5px;
            }
            
            .diary-entry {
                font-size: 11px;
                padding: 2px 4px;
            }
            
            .weekday {
                padding: 10px 5px;
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    
    <%
        // 연도와 월 파라미터 처리
        Integer year = (Integer) request.getAttribute("year");
        Integer month = (Integer) request.getAttribute("month");
        
        // YearMonth 객체 생성
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDay = yearMonth.atDay(1);
        LocalDate lastDay = yearMonth.atEndOfMonth();
        
        // 달력 시작일 계산 (일요일부터 시작)
        int firstDayOfWeek = firstDay.getDayOfWeek().getValue() % 7;
        LocalDate calendarStart = firstDay.minusDays(firstDayOfWeek);
        
        // 일지 데이터를 날짜별로 그룹화
        List<FarmingDiaryDTO> diaries = (List<FarmingDiaryDTO>) request.getAttribute("diaries");
        Map<LocalDate, List<FarmingDiaryDTO>> diaryMap = new HashMap<>();
        for (FarmingDiaryDTO diary : diaries) {
            LocalDate date = diary.getDate();
            diaryMap.computeIfAbsent(date, k -> new java.util.ArrayList<>()).add(diary);
        }
        
        // 이전/다음 월 계산
        YearMonth prevMonth = yearMonth.minusMonths(1);
        YearMonth nextMonth = yearMonth.plusMonths(1);
        
        // 오늘 날짜
        LocalDate today = LocalDate.now();
    %>
    
    <div class="calendar-container">
        <div class="calendar-header">
            <h1>영농일지 달력</h1>
            <div class="calendar-nav">
                <div class="month-display">
                    <%= yearMonth.format(DateTimeFormatter.ofPattern("yyyy년 MM월")) %>
                </div>
                <div class="nav-buttons">
                    <a href="${pageContext.request.contextPath}/diary/calendar?year=<%= prevMonth.getYear() %>&month=<%= prevMonth.getMonthValue() %>" 
                       class="btn btn-nav">
                        <i class="fas fa-chevron-left"></i> 이전달
                    </a>
                    <a href="${pageContext.request.contextPath}/diary/calendar" 
                       class="btn btn-secondary">
                        오늘
                    </a>
                    <a href="${pageContext.request.contextPath}/diary/calendar?year=<%= nextMonth.getYear() %>&month=<%= nextMonth.getMonthValue() %>" 
                       class="btn btn-nav">
                        다음달 <i class="fas fa-chevron-right"></i>
                    </a>
                    <a href="${pageContext.request.contextPath}/diary/list" 
                       class="btn btn-primary">
                        <i class="fas fa-list"></i> 목록보기
                    </a>
                </div>
            </div>
        </div>
        
        <div class="calendar-grid">
            <div class="calendar-weekdays">
                <div class="weekday">일</div>
                <div class="weekday">월</div>
                <div class="weekday">화</div>
                <div class="weekday">수</div>
                <div class="weekday">목</div>
                <div class="weekday">금</div>
                <div class="weekday">토</div>
            </div>
            
            <div class="calendar-days">
                <%
                    LocalDate currentDate = calendarStart;
                    for (int i = 0; i < 42; i++) {
                        boolean isCurrentMonth = currentDate.getMonth() == yearMonth.getMonth();
                        boolean isToday = currentDate.equals(today);
                        List<FarmingDiaryDTO> dayDiaries = diaryMap.get(currentDate);
                %>
                <div class="calendar-day <%= !isCurrentMonth ? "other-month" : "" %> <%= isToday ? "today" : "" %>">
                    <div class="day-number"><%= currentDate.getDayOfMonth() %></div>
                    <% if (dayDiaries != null && !dayDiaries.isEmpty()) { %>
                        <div class="diary-entries">
                            <% for (FarmingDiaryDTO diary : dayDiaries) { %>
                                <div class="diary-entry <%= diary.getActivityType() %>" 
                                     onclick="location.href='${pageContext.request.contextPath}/diary/detail/<%= diary.getFarmingDiaryId() %>'">
                                    <%= diary.getActivityType() %>
                                </div>
                            <% } %>
                        </div>
                    <% } %>
                    <% if (isCurrentMonth) { %>
                        <a href="${pageContext.request.contextPath}/diary/form?date=<%= currentDate %>" 
                           class="add-diary-btn" 
                           title="일지 작성">
                            +
                        </a>
                    <% } %>
                </div>
                <%
                        currentDate = currentDate.plusDays(1);
                    }
                %>
            </div>
        </div>
        
        <div class="legend">
            <div class="legend-title">활동 유형별 색상</div>
            <div class="legend-items">
                <div class="legend-item">
                    <div class="legend-color" style="background-color: #fff3e0;"></div>
                    <span>파종</span>
                </div>
                <div class="legend-item">
                    <div class="legend-color" style="background-color: #e1f5fe;"></div>
                    <span>관수</span>
                </div>
                <div class="legend-item">
                    <div class="legend-color" style="background-color: #f3e5f5;"></div>
                    <span>시비</span>
                </div>
                <div class="legend-item">
                    <div class="legend-color" style="background-color: #fce4ec;"></div>
                    <span>방제</span>
                </div>
                <div class="legend-item">
                    <div class="legend-color" style="background-color: #e8f5e9;"></div>
                    <span>수확</span>
                </div>
                <div class="legend-item">
                    <div class="legend-color" style="background-color: #f5f5f5;"></div>
                    <span>기타</span>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>