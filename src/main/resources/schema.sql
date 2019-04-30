CREATE TABLE `cloudnote`.`cn_company` (
  `id` VARCHAR(36) NOT NULL,
  `name` VARCHAR(100) NULL,
  `flag` VARCHAR(100) NULL,
  `address` VARCHAR(100) NULL,
  `inDate` DATETIME NOT NULL,
  `outDate` DATETIME NULL,
  `createDate` DATETIME NULL,
  `userId` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `cloudnote`.`cn_company_note` (
  `id` VARCHAR(36) NOT NULL,
  `title` VARCHAR(200) NOT NULL,
  `content` TEXT(5000) NULL,
  `companyId` VARCHAR(36) NULL,
  `createDate` DATETIME NOT NULL,
  `userId` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `cloudnote`.`cn_project` (
  `id` VARCHAR(36) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `projectDescribe` TEXT(1000) NULL,
  `createDate` DATETIME NOT NULL,
  `userId` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `cloudnote`.`cn_project_note` (
  `id` VARCHAR(36) NOT NULL,
  `title` VARCHAR(200) NOT NULL,
  `content` TEXT(5000) NULL,
  `projectId` VARCHAR(36) NULL,
  `createDate` DATETIME NOT NULL,
  `userId` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `cloudnote`.`cn_todo` (
  `id` VARCHAR(36) NOT NULL,
  `title` VARCHAR(200) NOT NULL,
  `projectId` VARCHAR(36) NULL,
  `status` VARCHAR(2) NOT NULL DEFAULT '0' COMMENT '0 新增 1 开始 2 结束 3 超时',
  `expectedStartDate` DATETIME NOT NULL,
  `expectedEndDate` DATETIME NOT NULL,
  `createDate` DATETIME NOT NULL,
  `userId` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`));