create schema if not exists public
    authorization postgres;

create table if not exists public."Invoice"
(
    id character varying collate pg_catalog."default" not null,
    sum bigint not null,
    "time" time without time zone not null,
    "date" date not null,
    products_id character varying collate pg_catalog."default" not null,
    constraint "Invoice_pkey" primary key (id),
    constraint "Invoice_products_id_key" unique (products_id)
)

tablespace pg_default;


create table if not exists public."Laptop"
(
    id character varying collate pg_catalog."default" not null,
    model character varying collate pg_catalog."default" not null,
    manufacturer character varying collate pg_catalog."default" not null,
    constraint "Laptop_pkey" primary key (id),
    constraint "Invoice_id" foreign key (id)
        references public."Invoice" (products_id) match simple
        on update NO ACTION
        ON delete NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

alter TABLE IF EXISTS public."Laptop"
    OWNER to postgres;

create index if not exists "fki_Invoice_id"
    on public."Laptop" using btree
    (id COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;

create table if not exists public."Phone"
(
    id character varying collate pg_catalog."default" not null,
    model character varying collate pg_catalog."default" not null,
    manufacturer character varying collate pg_catalog."default" not null,
    constraint "Phone_pkey" primary key (id),
    constraint "Invoice_id" foreign key (id)
        references public."Invoice" (products_id) match simple
        on update NO ACTION
        ON delete NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

alter TABLE IF EXISTS public."Phone"
    OWNER to postgres;

create table if not exists public."Tablet"
(
    id character varying collate pg_catalog."default" not null,
    model character varying collate pg_catalog."default" not null,
    manufacturer character varying collate pg_catalog."default" not null,
    constraint "Tablet_pkey" primary key (id),
    constraint "Invoice_id" foreign key (id)
        references public."Invoice" (products_id) match simple
        on update NO ACTION
        ON delete NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

alter TABLE IF EXISTS public."Tablet"
    OWNER to postgres;