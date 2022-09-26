-- Table: public.requestdata

-- DROP TABLE IF EXISTS public.requestdata;

CREATE TABLE IF NOT EXISTS public.requestdata
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    circulationdata timestamp without time zone,
    ipaddr character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT requestdata_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.requestdata
    OWNER to postgres;




-- Table: public.translatordata

-- DROP TABLE IF EXISTS public.translatordata;

CREATE TABLE IF NOT EXISTS public.translatordata
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    languagefrom character varying(10) COLLATE pg_catalog."default",
    languageto character varying(10) COLLATE pg_catalog."default",
    sourcetext character varying(255) COLLATE pg_catalog."default",
    targettext character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT origrequest_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.translatordata
    OWNER to postgres;
	
	
	

-- Table: public.word

-- DROP TABLE IF EXISTS public.word;

CREATE TABLE IF NOT EXISTS public.word
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    originalword character varying(255) COLLATE pg_catalog."default",
    translateword character varying(255) COLLATE pg_catalog."default",
    request_id character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT word_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.word
    OWNER to postgres;