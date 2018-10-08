-- 
-- Set character set the client will use to send SQL statements to the server
--
SET NAMES 'utf8';

--
-- Set default database
--
USE contact_list;

--
-- Drop table `phones`
--
DROP TABLE IF EXISTS phones;

--
-- Set default database
--
USE contact_list;

--
-- Create table `phones`
--
CREATE TABLE phones (
  phone_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  contact_id BIGINT(20) NOT NULL,
  phone_number VARCHAR(60) NOT NULL,
  phone_description VARCHAR(60) DEFAULT NULL,
  phone_comment VARCHAR(60) DEFAULT NULL,
  deleted TINYINT(1) DEFAULT NULL,
  PRIMARY KEY (phone_id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 139,
AVG_ROW_LENGTH = 121,
CHARACTER SET utf8mb4,
COLLATE utf8mb4_general_ci;

--
-- Create index `FK_phones_contact_id` on table `phones`
--
ALTER TABLE phones 
  ADD INDEX FK_phones_contact_id(contact_id);