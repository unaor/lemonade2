# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table business (
  id                        bigint not null,
  owner_id                  bigint,
  rnc                       bigint,
  business_name             varchar(255),
  address                   varchar(255),
  phone_number              varchar(255),
  fax_number                varchar(255),
  email                     varchar(255),
  start_date                timestamp,
  constraint pk_business primary key (id))
;

create table user (
  id                        bigint not null,
  email                     varchar(255),
  password                  varchar(255),
  name                      varchar(255),
  last_name                 varchar(255),
  admin                     boolean,
  registration_date         timestamp,
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id))
;

create sequence business_seq;

create sequence user_seq;

alter table business add constraint fk_business_owner_1 foreign key (owner_id) references user (id) on delete restrict on update restrict;
create index ix_business_owner_1 on business (owner_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists business;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists business_seq;

drop sequence if exists user_seq;

