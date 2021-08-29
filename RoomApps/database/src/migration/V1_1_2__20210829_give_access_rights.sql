
---------------------------------------------------------------
-- Give access rights to roomreader and roomwriter to the
-- statusdb
--
-- HI-20
---------------------------------------------------------------
grant select, update, insert, delete on table statusdata.room to roomwriter;
grant select, update, insert, delete on table statusdata.roomdata to roomwriter;
grant select on table statusdata.room to roomreader;
grant select on table statusdata.roomdata to roomreader;
grant usage on sequence statusdata.roomdata_seq to roomwriter;
grant usage on sequence statusdata.room_seq to roomwriter;
grant usage on schema statusdata to roomwriter;
grant usage on schema statusdata to roomreader;
