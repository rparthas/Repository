CREATE TABLE animal (
    id     NUMBER NOT NULL,
    name   VARCHAR2(100),
    CONSTRAINT animal_pk PRIMARY KEY ( id )
);

SELECT
    *
FROM
    animal;

SET SERVEROUTPUT ON;

DECLARE
    v_animal   animal%rowtype;
BEGIN
    v_animal.id := 1;
    v_animal.name := 'Tiger';
    INSERT INTO animal VALUES v_animal;

    SELECT
        *
    INTO
        v_animal
    FROM
        animal
    WHERE
        id = 1;

    dbms_output.put_line(v_animal.id);
    dbms_output.put_line(v_animal.name);
END;