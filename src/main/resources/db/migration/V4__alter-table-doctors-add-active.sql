alter table doctors add active tinyint;
update doctors set doctors.active = 1;
