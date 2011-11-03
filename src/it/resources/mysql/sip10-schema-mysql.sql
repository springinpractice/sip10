-- ============================================================================
-- $Id: sip10-ddl.sql 18 2010-05-02 05:11:58Z  $
-- 
-- Copyright (c) 2010 Manning Publications Co.
-- 
-- Willie Wheeler (willie.wheeler@gmail.com)
-- 
-- Book web site   - http://www.manning.com/wheeler/
-- Book blog       - http://springinpractice.com/
-- Author web site - http://wheelersoftware.com/
-- ============================================================================

drop table if exists contact;

create table contact (
    id bigint unsigned not null auto_increment primary key,
    last_name varchar(40) not null,
    first_name varchar(40) not null,
    mi char(1),
    email varchar(80),
	date_created timestamp default 0,
	date_modified timestamp default current_timestamp on update current_timestamp,
	unique index contact_idx1 (last_name, first_name, mi)
) engine = InnoDB;
