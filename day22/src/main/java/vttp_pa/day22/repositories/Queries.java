package vttp_pa.day22.repositories;

public class Queries {

    public static final String SQL_INSERT_USER= "insert into user (username, password, email, phone, dob) values(?, sha(?), ?, ?, ?)";
    public static final String SQL_FIND_USER= "select count(*) as auth_state from user where username = ? and password = sha(?)";
    public static final String SQL_INSERT_TASK= "insert into task (task_name, priority, assign_to, completion_date) values(?, ?, ?, ?)";
    
}
