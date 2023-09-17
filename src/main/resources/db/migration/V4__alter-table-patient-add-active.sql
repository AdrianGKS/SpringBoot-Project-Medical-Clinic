alter table patient add column active tinyint;
update patient set patient.active = 1;
alter table patient modify active tinyint not null;