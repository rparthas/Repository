SET SERVEROUTPUT ON;

DECLARE
    TYPE v_table_t IS
        TABLE OF VARCHAR2(30);
    v_table   v_table_t := v_table_t ();
BEGIN
    v_table.extend(3);
    v_table(1) := 'Apple';
    v_table(2) := 'Orange';
    v_table(3) := 'Mango';
    dbms_output.put_line(v_table.first);
    dbms_output.put_line(v_table.last);
    dbms_output.put_line(v_table.count);
END;