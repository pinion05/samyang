네, `samyang` 데이터베이스에 있는 모든 테이블의 스키마(`CREATE TABLE` 구문)를 추출해 드리겠습니다.

아래는 현재 제공된 테이블 정의를 기반으로 한 전체 스키마입니다.

---

### `samyang` 데이터베이스 전체 테이블 스키마

**1. `User` 테이블**
```sql
CREATE TABLE `User` (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `PhoneNumber` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `IsAdmin` tinyint(1) DEFAULT '0',
  `CreatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**2. `Crop` 테이블**
```sql
CREATE TABLE `Crop` (
  `CropID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `CropName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Variety` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `PlantedDate` date DEFAULT NULL,
  `ExpectedHarvestDate` date DEFAULT NULL,
  `Status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`CropID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `Crop_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**3. `FarmingDiary` 테이블**
```sql
CREATE TABLE `FarmingDiary` (
  `FarmingDiaryID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `Date` date NOT NULL,
  `ActivityType` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Content` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `PhotoURL` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CreatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`FarmingDiaryID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `FarmingDiary_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**4. `Comment` 테이블**
```sql
CREATE TABLE `Comment` (
  `CommentID` int NOT NULL AUTO_INCREMENT,
  `PostID` int NOT NULL,
  `UserID` int NOT NULL,
  `Content` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `CreatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CommentID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `Comment_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**5. `Review` 테이블**
```sql
CREATE TABLE `Review` (
  `ReviewID` int NOT NULL AUTO_INCREMENT,
  `OrderItemID` int NOT NULL,
  `UserID` int NOT NULL,
  `Rating` int NOT NULL,
  `Content` text COLLATE utf8mb4_unicode_ci,
  `CreatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ReviewID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `Review_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`),
  CONSTRAINT `Review_chk_1` CHECK (((`Rating` >= 1) and (`Rating` <= 5)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**6. `Report` 테이블**
```sql
CREATE TABLE `Report` (
  `ReportID` int NOT NULL AUTO_INCREMENT,
  `ReporterUserID` int NOT NULL,
  `ReportType` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TargetID` int NOT NULL,
  `Reason` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `ReportedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `Status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT 'Received',
  PRIMARY KEY (`ReportID`),
  KEY `ReporterUserID` (`ReporterUserID`),
  CONSTRAINT `Report_ibfk_1` FOREIGN KEY (`ReporterUserID`) REFERENCES `User` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**7. `PaymentMethod` 테이블**
```sql
CREATE TABLE `PaymentMethod` (
  `PaymentMethodID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `CardNumber` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ExpiryDate` date NOT NULL,
  `CVC` varchar(4) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`PaymentMethodID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `PaymentMethod_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```