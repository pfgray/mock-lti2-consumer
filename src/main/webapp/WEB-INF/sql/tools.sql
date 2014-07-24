create table tools (
    id         varchar(20)  NOT NULL,
    label      varchar(20)  NOT NULL,
    launch_url varchar(500) NOT NULL,
    register_url varchar(500) NOT NULL,
    tool_state varchar(10)  NOT NULL DEFAULT 'added',
    primary key (id)
);

insert into tools (id, label, launch_url, register_url) values ('1', 'campuspack', 'http://www.google.com', 'http://www.google.com');
insert into tools (id, label, launch_url, register_url, tool_state) values ('2', 'lti-chat', 'http://lti-chat.paulgray.net', 'http://lti-chat.paulgray.net', 'registered');
insert into tools (id, label, launch_url, register_url, tool_state) values ('3', 'sample-lti', 'http://www.google.com', 'http://www.google.com', 'failed');
