create table depoimento (
    
    id         bigint not null auto_increment,
    nome       varchar(200) not null,
    depoimento varchar(2000) not null,
    foto       blob,
    
    primary key(id) 
);