create table user (
    username varchar(32) not null,
    email varchar(128) not null,

    primary key(username)
);

create table task (
    task_id int auto_increment not null,
    task_name varchar(128),
    comments text,

    -- foreign key
    username varchar(32) not null,

    primary key(task_id),
    
    constraint fk_username
        foreign key(username) references user(username)
);