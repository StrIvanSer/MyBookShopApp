--liquibase formatted sql
--changeset somebody:fill-table-tag runInTransaction:true failOnError:true

CREATE TABLE public.tag
(
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    CONSTRAINT tags_pkey PRIMARY KEY (id)
);


CREATE TABLE public.book2tag
(
    book_id integer NOT NULL,
    tag_id integer NOT NULL,
    CONSTRAINT book2tag_pkey PRIMARY KEY (book_id, tag_id),
    CONSTRAINT book2tag_book_fk FOREIGN KEY (book_id)
        REFERENCES public.book (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT book2tag_tag_fk FOREIGN KEY (tag_id)
        REFERENCES public.tag (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.book2tag
    OWNER to postgres;

--rollback rollback;