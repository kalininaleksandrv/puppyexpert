
CREATE TABLE IF NOT EXISTS public.usr
(
    id character varying(255),
    name character varying(255),
    userpic character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    locale character varying(255) COLLATE pg_catalog."default",
    lastvisit bigint,
    registrationtime bigint,
    isregistered integer,

    CONSTRAINT usr_pkey PRIMARY KEY (id)
);