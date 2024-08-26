
create table if not exists users(
    id bigint primary key auto_increment,
    name varchar(50) not null ,
    email varchar(50) not null unique,
    role varchar(10) not null,
    password varchar(248) not null
);

create table if not exists tokens(
    id bigint primary key auto_increment,
    user_id bigint,
    value varchar(248) not null,
    category varchar(10) not null ,
    foreign key (user_id) references users(id) on delete set null
);