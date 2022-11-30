create table rsvp (
	id int auto_increment not null,
    name varchar(128) not null,
    email varchar(64) UNIQUE not null,
    phone int,
    confirmation_date datetime,
    comments longtext,

    primary key(id)
);