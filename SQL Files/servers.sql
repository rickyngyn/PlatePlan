-- Table: public.servers

-- DROP TABLE IF EXISTS public.servers;

CREATE TABLE IF NOT EXISTS public.servers
(
    id character varying(50) COLLATE pg_catalog."default",
    firstname character varying(30) COLLATE pg_catalog."default",
    lastname character varying(30) COLLATE pg_catalog."default"
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.servers
    OWNER to postgres;