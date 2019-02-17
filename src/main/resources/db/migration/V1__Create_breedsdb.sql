create sequence hibernate_sequence start 1 increment 1;

CREATE SEQUENCE public.breeds_id_seq;

CREATE TABLE public.breeds
(
    id bigint NOT NULL DEFAULT nextval('breeds_id_seq'::regclass),
    active integer NOT NULL,
    agressive integer NOT NULL,
    blackorwhite character varying(255) COLLATE pg_catalog."default",
    care integer NOT NULL,
    comment character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    descriptionfull varchar(1024),
    favorite integer,
    fciid integer NOT NULL,
    foragility integer NOT NULL,
    forchild integer NOT NULL,
    forcompany integer NOT NULL,
    forguardterritory integer NOT NULL,
    forhunt integer NOT NULL,
    forobidience integer NOT NULL,
    forrunning integer NOT NULL,
    forzks integer NOT NULL,
    guard integer NOT NULL,
    hair character varying(255) COLLATE pg_catalog."default",
    hardy integer NOT NULL,
    hunt character varying(255) COLLATE pg_catalog."default",
    imageresourceid character varying(255) COLLATE pg_catalog."default",
    imageresourceidbig character varying(255) COLLATE pg_catalog."default",
    noalergy character varying(255) COLLATE pg_catalog."default",
    obidience integer NOT NULL,
    size integer NOT NULL,
    title character varying(1024),
    weblinc varchar(1024),
    weblincwiki varchar(1024),
    CONSTRAINT breeds_pkey PRIMARY KEY (id)
)WITH (
   OIDS=FALSE
 );

 ALTER TABLE public.breeds
   OWNER TO mrapiadmin;