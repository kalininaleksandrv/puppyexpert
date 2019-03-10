CREATE SEQUENCE public.breedrequests_id_seq;

CREATE TABLE public.breedrequests
(
    id bigint NOT NULL DEFAULT nextval('breedrequests_id_seq'::regclass),
    thetime integer NOT NULL,
    exp integer NOT NULL,
    age integer NOT NULL,
    athlet integer NOT NULL,
    cynologist integer NOT NULL,
    walk integer NOT NULL,
    family integer NOT NULL,
    grummer integer NOT NULL,
    foragility integer NOT NULL,
    forchild integer NOT NULL,
    forcompany integer NOT NULL,
    forguardter integer NOT NULL,
    forhunt integer NOT NULL,
    forobidience integer NOT NULL,
    forruning integer NOT NULL,
    forzks integer NOT NULL,
    hairsize character varying(32),
    blackorwhite character varying(32),
    sizeconstraintmin integer NOT NULL,
    sizeconstraintmax integer NOT NULL,
    rare character varying(32),

    CONSTRAINT breedrequests_pkey PRIMARY KEY (id)
)WITH (
   OIDS=FALSE
 );

 ALTER TABLE public.breedrequests
   OWNER TO mrapiadmin;