create table question (
  id integer generated by default as identity (start with 1),
  content varchar(255),
  owner integer, primary key (id)
);
create table user (
  userid integer generated by default as identity (start with 1),
  lastname varchar(255), login varchar(255), name varchar(255),
  password varchar(255), primary key (userid)
);
alter table user add constraint UK_ew1hvam8uwaknuaellwhqchhb unique (login);
alter table question add constraint FKyufbj6uqx0u7fglj0ydrwm6b foreign key (owner) references user;