create table destino (
    id    bigint not null auto_increment,
    nome  varchar(200) not null,
    preco float not null,
    foto  blob,
    
    primary key(id)
);