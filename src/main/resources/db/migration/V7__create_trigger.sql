CREATE FUNCTION public.update_view_trigger()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE LEAKPROOF
AS $BODY$
BEGIN
CREATE TRIGGER update_view_on_person_delete
AFTER DELETE
ON person
FOR EACH ROW
EXECUTE PROCEDURE show_active_passports();
END;
$BODY$;

ALTER FUNCTION public.update_view_trigger()
    OWNER TO postgres;
