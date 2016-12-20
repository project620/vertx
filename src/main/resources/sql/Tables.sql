create database vertx3;
create table jim_user(id varchar(100), userId varchar(100), name varchar(100), password varchar(100));
create table jim_user_activity(id varchar(100), user_name varchar(100), activity_type varchar(100), activity_address varchar(100), created_on date, ref_table varchar(100), ref_id varchar(100));
create table jim_role(id varchar(100), name varchar(100));
create table jim_user_role(id varchar(100), user_id varchar(100), role_id varchar(100));
create table jim_authority(id varchar(100), name varchar(100));
create table jim_role_authority(id varchar(100), role_id varchar(100), authority_id varchar(100));
create table jim_article(id varchar(100), context varchar(100), user_id varchar(100), created_on date);
create table jim_article_assess(id varchar(100), article_id varchar(100), user_id varchar(100), context varchar(100), created_on date);