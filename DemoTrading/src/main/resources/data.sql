
create table if not exists user_balances(
    id int auto_increment primary key ,
    user_id bigint unique ,
    balance double,
    foreign key (user_id) references users (id) on delete cascade
);

create table if not exists portfolio_items (
                                id int auto_increment primary key ,
                                user_id bigint,
                                ticker VARCHAR(10),
                                quantity int,
                                trade_action varchar(10),
                                purchase_price double,
                                foreign key (user_id) references users(id) on delete cascade
);