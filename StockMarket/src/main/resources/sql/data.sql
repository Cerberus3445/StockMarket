

create table if not exists Stocks(
    id int auto_increment primary key ,
    ticker varchar(4) not null unique ,
    title varchar(50) not null
)