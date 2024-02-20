-- Table: public.business

-- DROP TABLE IF EXISTS public.business;

CREATE TABLE IF NOT EXISTS public.business
(
    email character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(50) COLLATE pg_catalog."default",
    "openFrom" time(6) without time zone,
    "openUntil" time(6) without time zone,
    "reservationSlots" integer,
    CONSTRAINT business_pkey PRIMARY KEY (email)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.business
    OWNER to postgres;