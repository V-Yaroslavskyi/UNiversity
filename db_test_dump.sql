--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 9.5.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'WIN1251';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: admin; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE admin (
    id integer NOT NULL,
    login character varying(50) NOT NULL,
    password character varying(50) NOT NULL
);


ALTER TABLE admin OWNER TO admin;

--
-- Name: admin_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE admin_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE admin_id_seq OWNER TO admin;

--
-- Name: admin_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE admin_id_seq OWNED BY admin.id;


--
-- Name: applicants; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE applicants (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    surname character varying(50) NOT NULL,
    password character varying(50),
    zno_ukr integer NOT NULL,
    zno_math integer NOT NULL,
    zno_3rd_points integer NOT NULL,
    attestate integer NOT NULL,
    facultee_id integer NOT NULL,
    status boolean NOT NULL,
    zno_3rd_id integer NOT NULL
);


ALTER TABLE applicants OWNER TO admin;

--
-- Name: applicants_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE applicants_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE applicants_id_seq OWNER TO admin;

--
-- Name: applicants_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE applicants_id_seq OWNED BY applicants.id;


--
-- Name: disciplines; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE disciplines (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE disciplines OWNER TO admin;

--
-- Name: disciplines_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE disciplines_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE disciplines_id_seq OWNER TO admin;

--
-- Name: disciplines_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE disciplines_id_seq OWNED BY disciplines.id;


--
-- Name: facultees; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE facultees (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    quote integer NOT NULL,
    zno_3rd_id integer NOT NULL
);


ALTER TABLE facultees OWNER TO admin;

--
-- Name: facultees_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE facultees_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE facultees_id_seq OWNER TO admin;

--
-- Name: facultees_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE facultees_id_seq OWNED BY facultees.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY admin ALTER COLUMN id SET DEFAULT nextval('admin_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY applicants ALTER COLUMN id SET DEFAULT nextval('applicants_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY disciplines ALTER COLUMN id SET DEFAULT nextval('disciplines_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY facultees ALTER COLUMN id SET DEFAULT nextval('facultees_id_seq'::regclass);


--
-- Data for Name: admin; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY admin (id, login, password) FROM stdin;
1	admin	admin
\.


--
-- Name: admin_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('admin_id_seq', 1, false);


--
-- Data for Name: applicants; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY applicants (id, name, surname, password, zno_ukr, zno_math, zno_3rd_points, attestate, facultee_id, status, zno_3rd_id) FROM stdin;
0	Володимир	Ярославський	йцукен	179	193	200	180	1	f	2
1	Оксана	Косткіна	qwerty	180	193	190	190	4	f	2
2	Олена	Косткіна	йцукен	180	193	190	190	4	f	2
\.


--
-- Name: applicants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('applicants_id_seq', 2, true);


--
-- Data for Name: disciplines; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY disciplines (id, name) FROM stdin;
1	Англійська мова
2	Фізика
\.


--
-- Name: disciplines_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('disciplines_id_seq', 1, false);


--
-- Data for Name: facultees; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY facultees (id, name, quote, zno_3rd_id) FROM stdin;
1	Скала-розробний факультет	10	2
2	Верстальний факультет	5	1
3	Інститут прикладного системного адміністрування	3	1
4	Джаваскріпто-інженерний факультет	4	2
\.


--
-- Name: facultees_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('facultees_id_seq', 1, false);


--
-- Name: admin_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY admin
    ADD CONSTRAINT admin_pkey PRIMARY KEY (id);


--
-- Name: applicants_id_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY applicants
    ADD CONSTRAINT applicants_id_pk PRIMARY KEY (id);


--
-- Name: disciplines_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY disciplines
    ADD CONSTRAINT disciplines_pkey PRIMARY KEY (id);


--
-- Name: facultees_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY facultees
    ADD CONSTRAINT facultees_pkey PRIMARY KEY (id);


--
-- Name: Applicants_id_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX "Applicants_id_uindex" ON applicants USING btree (id);


--
-- Name: admin_login_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX admin_login_uindex ON admin USING btree (login);


--
-- Name: fk_zno; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY facultees
    ADD CONSTRAINT fk_zno FOREIGN KEY (zno_3rd_id) REFERENCES disciplines(id) ON DELETE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: Владимир
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM "Владимир";
GRANT ALL ON SCHEMA public TO "Владимир";
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

