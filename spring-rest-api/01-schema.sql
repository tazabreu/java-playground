-- Table: public.hero

-- DROP TABLE public.hero;

CREATE TABLE public.hero
(
    id bigint NOT NULL,
    "first_name" text COLLATE pg_catalog."default" NOT NULL,
    "last_name" text COLLATE pg_catalog."default",
    "quest_status" text COLLATE pg_catalog."default" NOT NULL,
    "role" text COLLATE pg_catalog."default" NOT NULL,
    "race" text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT hero_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.hero
    OWNER to postgres;