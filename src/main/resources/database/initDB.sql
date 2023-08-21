create table if not exists users
(
	id bigserial primary key,
	first_name varchar(200) not null,
	middle_name varchar(200),
	last_name varchar(200) not null
);

create table if not exists user_time_slots
(
	user_id bigserial not null,
	end_time time without time zone,
	start_time time without time zone,
	week_day smallint
); 

create table if not exists events
(
	id bigserial primary key,
	event_date_time timestamp without time zone not null,
	message varchar(200) not null
);