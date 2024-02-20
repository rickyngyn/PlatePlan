-- Table: public.tables

-- DROP TABLE IF EXISTS public.tables;

CREATE TABLE IF NOT EXISTS public.tables
(
    id character varying(50) COLLATE pg_catalog."default" NOT NULL,
    capacity integer,
    server character varying(30) COLLATE pg_catalog."default",
    CONSTRAINT tables_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tables
    OWNER to postgres;