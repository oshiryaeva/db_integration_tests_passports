-- Trigger: update_view_trigger

-- DROP TRIGGER update_view_trigger ON public.person;

CREATE TRIGGER update_view_trigger
    AFTER INSERT OR DELETE OR UPDATE
    ON public.person
    FOR EACH ROW
    EXECUTE FUNCTION public.update_view_trigger_func();