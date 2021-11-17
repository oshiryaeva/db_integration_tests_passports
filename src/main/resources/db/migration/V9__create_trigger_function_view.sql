-- FUNCTION: public.update_view_trigger_func()

-- DROP FUNCTION public.update_view_trigger_func();

CREATE FUNCTION public.update_view_trigger_func()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE LEAKPROOF
AS $BODY$
BEGIN
    DROP VIEW IF EXISTS view_active_passports;
    CREATE VIEW view_active_passports AS
    SELECT row_number() OVER () AS id
    , pt.serial_number as serial_number
    , pt.number as number
    , pt.issue_date as issue_date
    , pt.expiration_date as expiration_date
    , pn.first_name as first_name
    , pn.last_name as last_name
    , pn.birth_date as birth_date
    FROM passport pt
    JOIN person pn ON pt.person_id = pn.id
    WHERE pt.active IS TRUE
    ORDER BY id;
    RETURN NULL;
END;
$BODY$;
