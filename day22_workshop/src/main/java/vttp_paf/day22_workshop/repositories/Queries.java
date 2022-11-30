package vttp_paf.day22_workshop.repositories;

public class Queries {
    public static final String SQL_GET_ALL_RSVP = "select id, name, email, phone, DATE_FORMAT(confirmation_date, \"%d/%m/%Y\") as confirmation_date, comments from rsvp";
    public static final String SQL_RSVP_BY_NAME = "select id, name, email, phone, DATE_FORMAT(confirmation_date, \"%d/%m/%Y\") as confirmation_date, comments from rsvp where name like ?";
    public static final String SQL_INSERT_RSVP = "insert into rsvp (name, email, phone, confirmation_date, comments) values (?, ?, ?, ?, ?)";
    public static final String SQL_FIND_RSVP_BY_EMAIL = "select id, name, email, phone, DATE_FORMAT(confirmation_date, \"%d/%m/%Y\") as confirmation_date, comments from rsvp where email = ?";
    public static final String SQL_UPDATE_RSVP = "update rsvp set name = ?, email = ?, phone = ? , confirmation_date = ?, comments = ? where email = ?";
    public static final String SQL_COUNT_RSVP = "select count(*) as total from rsvp";
}
