prompt Importing table PI_BUSACTIONLOG...
set feedback off
set define off
insert into PI_BUSACTIONLOG (RECID, ACTIONTIME, ACTIONTYPE, REMARK, BUSID, DEPTID, PLACE)
values (24478668, to_date('12-11-2018 20:12:40', 'dd-mm-yyyy hh24:mi:ss'), 1, null, '9-7519', '090101', null);

prompt Done.


