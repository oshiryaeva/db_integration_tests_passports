-- FUNCTION: public.cascade_delete_trigger_func()

-- DROP FUNCTION public.cascade_delete_trigger_func();

CREATE FUNCTION public.cascade_delete_trigger_func()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE LEAKPROOF
AS $BODY$
BEGIN
	DELETE FROM passport
	WHERE passport.person_id = OLD.id;
	RETURN OLD;
END;
$BODY$;
