CREATE SCHEMA `internetshop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internetshop`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`item_id`));

INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('item1', '101');
INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('item2', '150');
INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('item3', '200');
INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('item4', '250');
INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('item5', '300');
INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('item6', '350');
INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('item7', '400');
INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('item8', '450');
INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('item9', '500');
INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('item10', '550');