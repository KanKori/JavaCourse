create schema if not exists public
    authorization postgres;

CREATE TABLE IF NOT EXISTS public."Invoice"
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    sum bigint,
    "time" time without time zone,
    "date" date,
    CONSTRAINT "Invoice_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;
ALTER TABLE IF EXISTS public."Invoice"
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public."Laptop"
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    model character varying COLLATE pg_catalog."default" NOT NULL,
    manufacturer character varying COLLATE pg_catalog."default" NOT NULL,
    title character varying COLLATE pg_catalog."default",
    count integer,
    price double precision,
    CONSTRAINT "Laptop_pkey" PRIMARY KEY (id),
    CONSTRAINT "Invoice_Laptop_id" FOREIGN KEY (id)
        REFERENCES public."Invoice" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Laptop"
    OWNER to postgres;

CREATE INDEX IF NOT EXISTS "fki_Invoice_Laptop_id"
    ON public."Laptop" USING btree
    (id COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public."Phone"
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    model character varying COLLATE pg_catalog."default" NOT NULL,
    manufacturer character varying COLLATE pg_catalog."default" NOT NULL,
    title character varying COLLATE pg_catalog."default",
    count integer,
    price double precision,
    CONSTRAINT "Phone_pkey" PRIMARY KEY (id),
    CONSTRAINT "Invoice_Phone_id" FOREIGN KEY (id)
        REFERENCES public."Invoice" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Phone"
    OWNER to postgres;


CREATE INDEX IF NOT EXISTS "fki_Invoice_Phone_id"
    ON public."Phone" USING btree
    (id COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;


CREATE TABLE IF NOT EXISTS public."Tablet"
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    model character varying COLLATE pg_catalog."default" NOT NULL,
    manufacturer character varying COLLATE pg_catalog."default" NOT NULL,
    title character varying COLLATE pg_catalog."default",
    count integer,
    price double precision,
    CONSTRAINT "Tablet_pkey" PRIMARY KEY (id),
    CONSTRAINT "Invoice_Tablet_id" FOREIGN KEY (id)
        REFERENCES public."Invoice" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Tablet"
    OWNER to postgres;

CREATE INDEX IF NOT EXISTS "fki_Invoice_Tablet_id"
    ON public."Tablet" USING btree
    (id COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;

