
CREATE SEQUENCE IF NOT EXISTS public.usr_id_seq;

CREATE TABLE IF NOT EXISTS public.user_role(
    user_id int8 not null,
    roles varchar(255)
);


CREATE TABLE IF NOT EXISTS public.usr
(
    id bigint NOT NULL DEFAULT nextval('usr_id_seq'::regclass),
    name character varying(255),
    externalid character varying(512),
    password character varying(255),
    userpic character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    locale character varying(255) COLLATE pg_catalog."default",
    lastvisit bigint,
    registrationtime bigint,
    isregistered integer,
    roles character varying(64),

    CONSTRAINT usr_pkey PRIMARY KEY (id)
);