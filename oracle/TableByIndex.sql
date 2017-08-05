SET SERVEROUTPUT ON;

DECLARE
    TYPE v_index_t IS
        TABLE OF VARCHAR2(30) INDEX BY VARCHAR2(1);
    v_index           v_index_t;
    TYPE v_table_t IS
        TABLE OF VARCHAR2(30);
    v_table           v_table_t := v_table_t ();
    TYPE v_array_t IS
        VARRAY ( 3 ) OF VARCHAR2(30);
    v_array           v_array_t := v_array_t ();
    v_counter         BINARY_INTEGER;
    v_index_counter   VARCHAR2(1);
BEGIN
    v_index('a') := 'Apple';
    v_index('o') := 'Orange';
    v_index('m') := 'Mango';
    v_table.extend(3);
    v_table(1) := 'Apple';
    v_table(2) := 'Orange';
    v_table(3) := 'Mango';
    v_index.DELETE('o');
    v_table.DELETE(1);
    v_array.extend(3);
    v_array(1) := 'Apple';
    v_array(2) := 'Orange';
    v_array(3) := 'Mango';
    dbms_output.put_line('..COUNT..');
    dbms_output.put_line(v_index.count);
    dbms_output.put_line(v_table.count);
    dbms_output.put_line(v_array.count);
    dbms_output.put_line('..FIRST..');
    dbms_output.put_line(v_index.first);
    dbms_output.put_line(v_table.first);
    dbms_output.put_line(v_array.first);
    dbms_output.put_line('..LAST..');
    dbms_output.put_line(v_index.last);
    dbms_output.put_line(v_table.last);
    dbms_output.put_line(v_array.last);
    v_index_counter := v_index.first;
    LOOP
        EXIT WHEN
            v_index_counter IS NULL;
        dbms_output.put_line(v_index_counter
         || '-->'
         || v_index(v_index_counter) );
        v_index_counter := v_index.next(v_index_counter);
    END LOOP;

    v_counter := v_table.last;
    LOOP
        EXIT WHEN
            v_counter IS NULL;
        dbms_output.put_line(v_counter
         || '-->'
         || v_table(v_counter) );
        v_counter := v_table.PRIOR(v_counter);
    END LOOP;

    v_counter := v_array.first;
    FOR counter IN 1..v_array.count LOOP
        dbms_output.put_line(counter||'-->'||v_array(counter) );
    END LOOP;

END;