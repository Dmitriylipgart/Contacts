-- 
-- Set character set the client will use to send SQL statements to the server
--
SET NAMES 'utf8';

--
-- Set default database
--
USE contact_list;

--
-- Drop table `contacts`
--
DROP TABLE IF EXISTS contacts;

--
-- Set default database
--
USE contact_list;

--
-- Create table `contacts`
--
CREATE TABLE contacts (
  contact_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(60) DEFAULT NULL,
  last_name VARCHAR(60) DEFAULT NULL,
  middle_name VARCHAR(60) DEFAULT NULL,
  birth_date DATE DEFAULT NULL,
  sex ENUM('male','female') DEFAULT NULL,
  citizenship VARCHAR(60) DEFAULT NULL,
  family_status ENUM('married','single') DEFAULT NULL,
  web_site VARCHAR(60) DEFAULT NULL,
  email VARCHAR(50) DEFAULT NULL,
  job VARCHAR(60) DEFAULT NULL,
  country VARCHAR(60) DEFAULT NULL,
  city VARCHAR(60) DEFAULT NULL,
  address VARCHAR(60) DEFAULT NULL,
  zip_code VARCHAR(10) DEFAULT NULL,
  deleted TINYINT(1) DEFAULT NULL,
  avatar VARCHAR(60) DEFAULT NULL,
  PRIMARY KEY (contact_id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 121,
AVG_ROW_LENGTH = 409,
CHARACTER SET utf8mb4,
COLLATE utf8mb4_general_ci;