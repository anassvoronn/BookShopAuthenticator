-- Table: public.users

-- DROP TABLE IF EXISTS public.users;

CREATE SEQUENCE users_id_seq START 101;
CREATE SEQUENCE session_id_seq START 101;

CREATE TABLE IF NOT EXISTS public.users
(
    id integer NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_username_key UNIQUE (username)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.session
(
    id integer NOT NULL DEFAULT nextval('session_id_seq'::regclass),
    session_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT session_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.session
    OWNER to postgres;

ALTER TABLE session ADD COLUMN status character varying(50) COLLATE pg_catalog."default" NOT NULL,
ADD COLUMN "time" timestamp without time zone;

ALTER TABLE public.session
ADD CONSTRAINT fk_user
FOREIGN KEY (user_id) REFERENCES public.users (id)
ON DELETE CASCADE;

CREATE UNIQUE INDEX unique_active_session ON public.session (user_id, status) WHERE status = 'ACTIVE';