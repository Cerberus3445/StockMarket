
create table if not exists user_balance(
    id int auto_increment primary key ,
    user_id int ,
    balance double,
    foreign key (user_id) references user (id)
);

create table if not exists portfolio_item (
                                id int auto_increment primary key ,
                                user_id int,
                                ticker VARCHAR(10),
                                quantity int,
                                purchase_price double,
                                foreign key (customer_id) references user(id)
);