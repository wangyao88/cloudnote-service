CREATE TABLE `cloudnote`.`cn_company` (
  `id` VARCHAR(36) NOT NULL,
  `name` VARCHAR(100) NULL,
  `flag` VARCHAR(100) NULL,
  `address` VARCHAR(100) NULL,
  `inDate` DATETIME NULL,
  `outDate` DATETIME NULL,
  `userId` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`));