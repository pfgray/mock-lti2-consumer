create table tools (
    id         varchar(20) NOT NULL,
    label      varchar(20) NOT NULL,
    launch_url varchar(500) NOT NULL,
    primary key (id)
);

insert into tools (id, label, launch_url) values ('1', 'campuspack', 'http://www.google.com');
insert into tools (id, label, launch_url) values ('2', 'lti-chat', 'http://lti-chat.paulgray.net');
insert into tools (id, label, launch_url) values ('3', 'sample-lti', 'http://www.google.com');