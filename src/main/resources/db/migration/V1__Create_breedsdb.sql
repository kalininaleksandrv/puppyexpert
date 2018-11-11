create sequence hibernate_sequence start 1 increment 1;

CREATE SEQUENCE public.breeds__id_seq;

CREATE TABLE public.breeds
(
    _id bigint NOT NULL DEFAULT nextval('breeds__id_seq'::regclass),
    active integer NOT NULL,
    agressive integer NOT NULL,
    blackorwhite character varying(255) COLLATE pg_catalog."default",
    care integer NOT NULL,
    comment character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    description_full varchar(1024),
    favorite integer,
    fciid integer NOT NULL,
    for_agility integer NOT NULL,
    for_child integer NOT NULL,
    for_company integer NOT NULL,
    for_guardterritory integer NOT NULL,
    for_hunt integer NOT NULL,
    for_obidience integer NOT NULL,
    for_running integer NOT NULL,
    for_zks integer NOT NULL,
    guard integer NOT NULL,
    hair character varying(255) COLLATE pg_catalog."default",
    hardy integer NOT NULL,
    hunt character varying(255) COLLATE pg_catalog."default",
    image_resource_id character varying(255) COLLATE pg_catalog."default",
    image_resource_id_big character varying(255) COLLATE pg_catalog."default",
    noalergy character varying(255) COLLATE pg_catalog."default",
    obidience integer NOT NULL,
    size integer NOT NULL,
    title character varying(1024),
    weblinc varchar(1024),
    weblinc_wiki varchar(1024),
    CONSTRAINT breeds_pkey PRIMARY KEY (_id)
);