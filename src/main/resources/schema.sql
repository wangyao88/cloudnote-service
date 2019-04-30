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