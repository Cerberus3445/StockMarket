
create table if not exists stocks(
    id int auto_increment primary key ,
    ticker varchar(4) not null unique ,
    title varchar(50) not null
);

insert ignore stocks(ticker, title) VALUE ('AAPL', 'Apple Inc');
insert ignore stocks(ticker, title) VALUE ('TSLA', 'Tesla Inc');
insert ignore stocks(ticker, title) VALUE ('MSFT', 'Microsoft Corporation');
insert ignore stocks(ticker, title) VALUE ('INTC', 'Intel Corporation ');
insert ignore stocks(ticker, title) VALUE ('TEST', 'Test Company');

insert ignore stocks(ticker, title) VALUE ('AXP', 'American Express Company');
insert ignore stocks(ticker, title) VALUE ('CSCO', 'Cisco Systems Inc');
insert ignore stocks(ticker, title) VALUE ('UNH', 'Unitedhealth Group');
insert ignore stocks(ticker, title) VALUE ('MCD', 'McDonald’s Corporation');
insert ignore stocks(ticker, title) VALUE ('PG', 'Procter & Gamble Company');
insert ignore stocks(ticker, title) VALUE ('KO', 'Кока-Кола');