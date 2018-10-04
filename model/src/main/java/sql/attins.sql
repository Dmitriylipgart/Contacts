USE contact_list;

INSERT INTO attachments
(
  attachment_id
 ,contact_id
 ,attachment_filename
 ,attachment_comment
 ,attachment_date
 ,deleted
)
VALUES
(
  0 -- attachment_id - BIGINT(20) NOT NULL
 ,0 -- contact_id - BIGINT(20) NOT NULL
 ,'' -- attachment_filename - VARCHAR(255)
 ,'' -- attachment_comment - VARCHAR(50)
 ,NOW() -- attachment_date - DATE
 ,0 -- deleted - TINYINT(1)
);