-- MySQL Script generated by MySQL Workbench
-- Tue Jun 26 11:16:41 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema homeinventorydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `homeinventorydb` ;

-- -----------------------------------------------------
-- Schema homeinventorydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `homeinventorydb` DEFAULT CHARACTER SET latin1 ;
USE `homeinventorydb` ;

-- -----------------------------------------------------
-- Table `homeinventorydb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `homeinventorydb`.`users` (
  `Username` VARCHAR(10) NOT NULL,
  `Password` VARCHAR(20) NOT NULL,
  `Email` VARCHAR(50) NOT NULL,
  `FirstName` VARCHAR(50) NOT NULL,
  `LastName` VARCHAR(50) NOT NULL,
  `Active` BIT NOT NULL,
  `IsAdmin` BIT NOT NULL,
  `ReserPasswordUUID` VARCHAR(50) NULL,
  PRIMARY KEY (`Username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `homeinventorydb`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `homeinventorydb`.`categories` (
  `CategoryID` INT NOT NULL AUTO_INCREMENT,
  `CategoryName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`CategoryID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `homeinventorydb`.`items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `homeinventorydb`.`items` (
  `ItemID` INT NOT NULL AUTO_INCREMENT,
  `Category` INT NOT NULL,
  `ItemName` VARCHAR(45) NOT NULL,
  `Price` DOUBLE NOT NULL,
  `Owner` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`ItemID`),
  INDEX `fk_items_Categories_idx` (`Category` ASC),
  INDEX `fk_items_users1_idx` (`Owner` ASC),
  CONSTRAINT `fk_items_Categories`
    FOREIGN KEY (`Category`)
    REFERENCES `homeinventorydb`.`categories` (`CategoryID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_items_users1`
    FOREIGN KEY (`Owner`)
    REFERENCES `homeinventorydb`.`users` (`Username`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `categories` (`CategoryName`) VALUES ('kitchen');
INSERT INTO `categories` (`CategoryName`) VALUES ('bathroom');
INSERT INTO `categories` (`CategoryName`) VALUES ('living room');
INSERT INTO `categories` (`CategoryName`) VALUES ('basement');
INSERT INTO `categories` (`CategoryName`) VALUES ('bedrooom');
INSERT INTO `categories` (`CategoryName`) VALUES ('garage');
INSERT INTO `categories` (`CategoryName`) VALUES ('office');
INSERT INTO `categories` (`CategoryName`) VALUES ('utility room');
INSERT INTO `categories` (`CategoryName`) VALUES ('storage');
INSERT INTO `categories` (`CategoryName`) VALUES ('other');

INSERT INTO `users` (`Username`,`Password`,`Email`,`FirstName`,`LastName`,`Active`,`IsAdmin`) VALUES ('admin','password','cprg352+admin@gmail.com','Admin','Admin',1,1);
INSERT INTO `users` (`Username`,`Password`,`Email`,`FirstName`,`LastName`,`Active`,`IsAdmin`) VALUES ('admin2','password','cprg352+admin2@gmail.com','Admin2','Admin2',0,1);
INSERT INTO `users` (`Username`,`Password`,`Email`,`FirstName`,`LastName`,`Active`,`IsAdmin`) VALUES ('anne','password','cprg352+anne@gmail.com','Anne','Annerson',1,0);
INSERT INTO `users` (`Username`,`Password`,`Email`,`FirstName`,`LastName`,`Active`,`IsAdmin`) VALUES ('barb','password','cprg352+barb@gmail.com','Barb','Barber',0,0);

INSERT INTO `items` (`Category`,`ItemName`,`Price`,`Owner`) VALUES (1,'blender',29.99,'anne');
INSERT INTO `items` (`Category`,`ItemName`,`Price`,`Owner`) VALUES (1,'toaster',19.99,'anne');
INSERT INTO `items` (`Category`,`ItemName`,`Price`,`Owner`) VALUES (3,'lamp',5,'anne');
INSERT INTO `items` (`Category`,`ItemName`,`Price`,`Owner`) VALUES (6,'winter tires',200,'anne');
INSERT INTO `items` (`Category`,`ItemName`,`Price`,`Owner`) VALUES (5,'dresser',50,'anne');
