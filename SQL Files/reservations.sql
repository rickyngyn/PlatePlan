-- Table: public.reservations

-- DROP TABLE IF EXISTS public.reservations;

CREATE TABLE IF NOT EXISTS public.reservations
(
    id character varying(50)[] COLLATE pg_catalog."default" NOT NULL,
    "customerId" character varying(50)[] COLLATE pg_catalog."default",
    date date,
    "time" time(6) without time zone,
    "specialNotes" character varying(200) COLLATE pg_catalog."default",
    "tableId" character varying(50)[] COLLATE pg_catalog."default",
    "partySize" integer,
    CONSTRAINT reservations_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.reservations
    OWNER to postgres;