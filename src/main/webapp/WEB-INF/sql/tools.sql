create table tools (
    id               integer not null generated always as identity (start with 1, increment by 1),
    label            varchar(20) NOT NULL,
    launch_url       varchar(500),
    register_url     varchar(500) NOT NULL,
    tool_state       varchar(10)  NOT NULL DEFAULT 'added',
    primary key (id),
    unique(label)
);

insert into tools (label, launch_url, register_url) values ('campuspack', 'http://www.google.com', 'http://www.google.com');
insert into tools (label, launch_url, register_url, tool_state) values ('lti-chat', 'http://lti-chat.paulgray.net', 'http://lti-chat.paulgray.net', 'registered');
insert into tools (label, launch_url, register_url, tool_state) values ('sample-lti', 'http://www.google.com', 'http://www.google.com', 'failed');
