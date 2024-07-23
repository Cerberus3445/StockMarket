
create table if not exists Users(
    id int primary key auto_increment,
    name varchar(50) not null ,
    email varchar(50) not null unique,
    role varchar(10) not null
)