package com.itechart.model.sql;


public class ContactsSql {

    public static final String COUNT_ALL_SQL = "SELECT count(*) FROM contacts WHERE deleted IS NULL";
    public static final String CREATE_CONTACT_SQL = "INSERT INTO contacts "
            + "(first_name, last_name, middle_name, birth_date, sex, citizenship, family_status, "
            + "web_site, email, job, country, city, address, zip_code, avatar )"
            + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String UPDATE_CONTACT_SQL = "UPDATE contacts "
            + " SET first_name = ?, last_name = ?, middle_name = ?, birth_date = ?, sex = ?, citizenship = ?, family_status = ?, "
            + "web_site = ?, email = ?, job = ?, country = ?, city = ?, address = ?, zip_code = ?, avatar = ?"
            + " WHERE contact_id = ?";

    public static final String READ_ALL_CONTACTS_BY_LIMIT = "SELECT * from contacts WHERE deleted IS NULL ORDER by contact_id LIMIT ?,?;";
    public static final String READ_ALL_CONTACTS_BY_DATE = "SELECT * from contacts WHERE deleted IS NULL AND birth_date LIKE ?";
    public static final String READ_CONTACT_BY_ID = "SELECT * FROM contacts WHERE contact_id = ? AND deleted IS NULL";
    public static final String READ_CONTACTS_BY_ID = "SELECT * FROM contacts WHERE contact_id IN";
    public static final String DELETE_CONTACT_BY_ID = "UPDATE contacts SET deleted = 1 WHERE contact_id IN";




    public static final String READ_ALL_PHONES_BY_CONTACT_ID = "SELECT * FROM phones WHERE contact_id = ? AND deleted IS NULL";
    public static final String CREATE_PHONE_SQL = " INSERT INTO phones "
            + "(contact_id, phone_number, phone_description, phone_comment)"
            + "VALUES (?,?,?,?)";
    public static final String DELETE_PHONE_BY_CONTACT_ID = "UPDATE phones SET deleted = 1 WHERE contact_id IN";


    public static final String CREATE_ATTACHMENT_SQL = " INSERT INTO attachments "
            + "(contact_id, attachment_filename, attachment_comment, attachment_date)"
            + "VALUES (?,?,?,?)";
    public static final String DELETE_ATTACHMENT_BY_CONTACT_ID = "UPDATE attachments SET deleted = 1 WHERE contact_id IN";
    public static final String DELETE_ATTACHMENT_BY_ATTACHMENT_ID = "UPDATE attachments SET deleted = 1 WHERE attachment_id IN";
    public static final String READ_ALL_ATTACHMENTS_BY_CONTACT_ID = "SELECT * FROM attachments WHERE contact_id = ? AND deleted IS NULL";




}
