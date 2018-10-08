USE contact_list;

INSERT INTO phones
(
  phone_id
 ,contact_id
 ,phone_number
 ,phone_description
 ,phone_comment
 ,deleted
)
VALUES
(
  0 -- phone_id - BIGINT(20) NOT NULL
 ,0 -- contact_id - BIGINT(20) NOT NULL
 ,'' -- phone_number - VARCHAR(60) NOT NULL
 ,'' -- phone_description - VARCHAR(60)
 ,'' -- phone_comment - VARCHAR(60)
 ,0 -- deleted - TINYINT(1)
);