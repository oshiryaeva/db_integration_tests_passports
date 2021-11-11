-- Trigger: cascade_delete_trigger

-- DROP TRIGGER cascade_delete_trigger ON public.person;

CREATE TRIGGER cascade_delete_trigger
    BEFORE DELETE
    ON public.person
    FOR EACH ROW
    EXECUTE FUNCTION public.cascade_delete_trigger_func();