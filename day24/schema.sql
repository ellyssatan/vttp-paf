create table purchase_order (
	order_id varchar(8) not null,
    name varchar(128) not null,
    order_date date not null,
    order_address varchar(128),

    primary key(order_id)
);

create table order_items (
	order_id varchar(8) not null,
	item_id int auto_increment not null,
    description text not null,
    quantity int default '1',
    
    primary key(item_id),
    constraint fk_order_id
        foreign key(order_id) references purchase_order(order_id)
);