
create table tools (
    id                            integer not null generated always as identity (start with 1, increment by 1),
    label                         varchar(20) NOT NULL,
    register_url                  varchar(500) NOT NULL,
    tool_state                    varchar(10)  NOT NULL DEFAULT 'added',
    latest_tool_proxy_submission  clob,
    launch_url                    varchar(256),
    primary key (id),
    unique(label)
);

insert into tools (label, register_url, tool_state) values ('campuspack', 'http://www.google.com', 'registered');
insert into tools (label, register_url) values ('lti-chat', 'http://lti-chat.paulgray.net');
insert into tools (label, register_url, tool_state) values ('sample-lti', 'http://www.google.com', 'failed');

create table tool_proxies (
    id               integer not null generated always as identity (start with 1, increment by 1),
    tool             integer not null,
    secure_url       varchar(500) NOT NULL,
    default_url      varchar(500) NOT NULL,
    lti_key          varchar(500) NOT NULL,
    lti_secret       varchar(500) NOT NULL,
    primary key (id),
    foreign key (tool) references tools (id)
);

insert into tool_proxies (tool, secure_url, default_url, lti_key, lti_secret)
       values (1, 'https://qa-paul.campuspack.net/control/lti', 'http://qa-paul.campuspack.net/control/lti', 'mock_lti2_consumer', 'wyxb-7tdsvjnk-c7zp');

create table gradebooks (
    id               integer not null generated always as identity (start with 1, increment by 1),
    context          varchar(500) not null,
    primary key (id)
);

create table gradebook_lineitems (
    id               integer not null generated always as identity (start with 1, increment by 1),
    gradebook_id     integer not null,
    resource_link_id varchar(500) not null,
    title varchar(500),
    activity_id varchar(500),
    primary key (id),
    foreign key (gradebook_id) references gradebooks (id)
);

create table gradebook_cells (
    id               integer not null generated always as identity (start with 1, increment by 1),
    gradebook_lineitem_id     integer not null,
    result_sourcedid varchar(500) not null,
    grade            varchar(500),
    primary key (id),
    foreign key (gradebook_lineitem_id) references gradebook_lineitems (id)
);