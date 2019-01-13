CREATE SEQUENCE public.feedback__id_seq;

CREATE TABLE public.feedback
(
    id bigint NOT NULL DEFAULT nextval('breeds__id_seq'::regclass),
    dogid bigint,
    title character varying(255) COLLATE pg_catalog."default",
    description character varying(1024) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    username character varying(255) COLLATE pg_catalog."default",
    commenttime bigint,
    commenttimestr character varying(64) COLLATE pg_catalog."default",


    CONSTRAINT feedback_pkey PRIMARY KEY (id)
);