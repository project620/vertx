--authority initialize
insert into jim_authority(id, name) values('1','assess_put');
insert into jim_authority(id, name) values('2','assess_delete');
insert into jim_authority(id, name) values('3','article_put');
insert into jim_authority(id, name) values('4','article_delete');
insert into jim_authority(id, name) values('5','user_put');
insert into jim_authority(id, name) values('6','user_delete');
insert into jim_authority(id, name) values('7','article_post');
--role initialize 
insert into jim_role(id, name) values ('1','游客');
insert into jim_role(id, name) values ('1','作者');
insert into jim_role(id, name) values ('1','管理');
--relation initialize
insert into jim_role_authority(id,role_id,authority_id) values('1','1','1');
insert into jim_role_authority(id,role_id,authority_id) values('2','2','1');
insert into jim_role_authority(id,role_id,authority_id) values('3','2','2');
insert into jim_role_authority(id,role_id,authority_id) values('4','2','3');
insert into jim_role_authority(id,role_id,authority_id) values('5','2','4');
insert into jim_role_authority(id,role_id,authority_id) values('6','2','7');
insert into jim_role_authority(id,role_id,authority_id) values('7','3','1');
insert into jim_role_authority(id,role_id,authority_id) values('8','3','2');
insert into jim_role_authority(id,role_id,authority_id) values('9','3','3');
insert into jim_role_authority(id,role_id,authority_id) values('10','3','4');
insert into jim_role_authority(id,role_id,authority_id) values('11','3','7');
insert into jim_role_authority(id,role_id,authority_id) values('12','3','5');
insert into jim_role_authority(id,role_id,authority_id) values('13','3','6');

--init admin
insert into jim_user values('1','admin','admin',null);