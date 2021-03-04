-- Table: public.book_tags

-- DROP TABLE public.book_tags;

CREATE TABLE public.book_tags
(
    isbn_13 character varying(13) COLLATE pg_catalog."default" NOT NULL,
    tag_name character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT book_tags_pkey PRIMARY KEY (isbn_13),
    CONSTRAINT fk_isbn_13 FOREIGN KEY (isbn_13)
        REFERENCES public.books (isbn_13) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.book_tags
    OWNER to postgres;