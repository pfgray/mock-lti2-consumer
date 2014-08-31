create table tools (
    id               integer not null generated always as identity (start with 1, increment by 1),
    label            varchar(20) NOT NULL,
    register_url     varchar(500) NOT NULL,
    tool_state       varchar(10)  NOT NULL DEFAULT 'added',
    primary key (id),
    unique(label)
);

insert into tools (label, register_url) values ('campuspack', 'http://www.google.com');
insert into tools (label, register_url, tool_state) values ('lti-chat', 'http://lti-chat.paulgray.net', 'registered');
insert into tools (label, register_url, tool_state) values ('sample-lti', 'http://www.google.com', 'failed');


create table tool_proxies (
    id               integer not null generated always as identity (start with 1, increment by 1),
    tool             integer      NOT NULL,
    secure_url       varchar(500) NOT NULL,
    default_url      varchar(500) NOT NULL,
    secret           varchar(500) NOT NULL,
    primary key (id)
);

insert into tool_proxies (tool, secure_url, default_url, secret) values (2, 'http://lti-chat.paulgray.net/lti', 'http://lti-chat.paulgray.net/lti', 'secret');
