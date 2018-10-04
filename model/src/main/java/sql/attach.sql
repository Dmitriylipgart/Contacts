-- 
-- Set character set the client will use to send SQL statements to the server
--
SET NAMES 'utf8';

--
-- Set default database
--
USE contact_list;

--
-- Drop table `attachments`
--
DROP TABLE IF EXISTS attachments;

--
-- Set default database
--
USE contact_list;

--
-- Create table `attachments`
--
CREATE TABLE attachments (
  attachment_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  contact_id BIGINT(20) NOT NULL,
  attachment_filename VARCHAR(255) DEFAULT NULL,
  attachment_comment VARCHAR(50) DEFAULT NULL,
  attachment_date DATE DEFAULT NULL,
  deleted TINYINT(1) DEFAULT NULL,
  PRIMARY KEY (attachment_id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 11,
AVG_ROW_LENGTH = 1638,
CHARACTER SET utf8mb4,
COLLATE utf8mb4_general_ci;