CREATE TABLE `cloudnote`.`cn_company` (
  `id` VARCHAR(36) NOT NULL,
  `name` VARCHAR(100) NULL,
  `flag` VARCHAR(100) NULL,
  `address` VARCHAR(100) NULL,
  `inDate` DATETIME NOT NULL,
  `outDate` DATETIME NULL,
  `createDate` DATETIME NULL,
  `userId` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin
COMMENT = '公司表';

CREATE TABLE `cloudnote`.`cn_company_note` (
  `id` VARCHAR(36) NOT NULL,
  `title` VARCHAR(200) NOT NULL,
  `content` TEXT(5000) NULL,
  `companyId` VARCHAR(36) NULL,
  `createDate` DATETIME NOT NULL,
  `userId` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin
COMMENT = '办公资料表';

CREATE TABLE `cloudnote`.`cn_project` (
  `id` VARCHAR(36) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `projectDescribe` TEXT(1000) NULL,
  `createDate` DATETIME NOT NULL,
  `userId` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin
COMMENT = '项目表';

CREATE TABLE `cloudnote`.`cn_project_note` (
  `id` VARCHAR(36) NOT NULL,
  `title` VARCHAR(200) NOT NULL,
  `content` TEXT(5000) NULL,
  `projectId` VARCHAR(36) NULL,
  `createDate` DATETIME NOT NULL,
  `userId` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin
COMMENT = '项目资料表';

CREATE TABLE `cloudnote`.`cn_todo` (
  `id` VARCHAR(36) NOT NULL,
  `title` VARCHAR(200) NOT NULL,
  `projectId` VARCHAR(36) NULL,
  `status` VARCHAR(2) NOT NULL DEFAULT '0' COMMENT '0 新增 1 开始 2 结束 3 超时',
  `expectedStartDate` DATETIME NOT NULL,
  `expectedEndDate` DATETIME NOT NULL,
  `createDate` DATETIME NOT NULL,
  `userId` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin
COMMENT = '待办事项表';

CREATE TABLE `cloudnote`.`cn_standard_work_datetime` (
  `id` VARCHAR(36) NOT NULL,
  `amStart` VARCHAR(10) NOT NULL,
  `amEnd` VARCHAR(10) NOT NULL,
  `pmStart` VARCHAR(10) NOT NULL,
  `pmEnd` VARCHAR(10) NOT NULL,
  `createTime` DATETIME NOT NULL,
  `userId` VARCHAR(36) NOT NULL,
  `active` VARCHAR(10) NOT NULL DEFAULT '生效' COMMENT '失效 生效',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin
COMMENT = '标准工作作息时间表';

CREATE TABLE `cloudnote`.`cn_absence` (
  `id` varchar(36) COLLATE utf8mb4_bin NOT NULL,
  `absenceStart` date NOT NULL,
  `absenceEnd` date NOT NULL,
  `name` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `tips` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `createDate` datetime NOT NULL,
  `userId` varchar(36) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='节假日表';

CREATE TABLE `cloudnote`.`cn_leave` (
  `id` varchar(36) COLLATE utf8mb4_bin NOT NULL,
  `leaveStart` datetime NOT NULL,
  `leaveEnd` datetime NOT NULL,
  `name` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `tips` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `createDate` datetime NOT NULL,
  `userId` varchar(36) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='请假表';
