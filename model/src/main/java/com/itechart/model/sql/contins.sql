USE contact_list;

INSERT INTO contacts
(
  contact_id
 ,first_name
 ,last_name
 ,middle_name
 ,birth_date
 ,sex
 ,citizenship
 ,family_status
 ,web_site
 ,email
 ,job
 ,country
 ,city
 ,address
 ,zip_code
 ,deleted
 ,avatar
)
VALUES
(
  0 -- contact_id - BIGINT(20) NOT NULL
 ,'' -- first_name - VARCHAR(60)
 ,'' -- last_name - VARCHAR(60)
 ,'' -- middle_name - VARCHAR(60)
 ,NOW() -- birth_date - DATE
 ,('0') -- sex - ENUM('male','female')
 ,'' -- citizenship - VARCHAR(60)
 ,('0') -- family_status - ENUM('married','single')
 ,'' -- web_site - VARCHAR(60)
 ,'' -- email - VARCHAR(50)
 ,'' -- job - VARCHAR(60)
 ,'' -- country - VARCHAR(60)
 ,'' -- city - VARCHAR(60)
 ,'' -- address - VARCHAR(60)
 ,'' -- zip_code - VARCHAR(10)
 ,0 -- deleted - TINYINT(1)
 ,'' -- avatar - VARCHAR(60)
);