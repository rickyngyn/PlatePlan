--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.2 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: business; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.business (
    email character varying(50) NOT NULL,
    password character varying(50),
    "openFrom" time(6) without time zone,
    "openUntil" time(6) without time zone,
    "reservationSlots" integer
);


ALTER TABLE public.business OWNER TO postgres;

--
-- Name: customers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customers (
    email character varying(50) NOT NULL,
    firstname character varying(30),
    lastname character varying(30),
    password character varying(30)
);


ALTER TABLE public.customers OWNER TO postgres;

--
-- Name: reservations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reservations (
    id character varying(50)[] NOT NULL,
    "customerId" character varying(50)[],
    date date,
    "time" time(6) without time zone,
    "specialNotes" character varying(200),
    "tableId" character varying(50)[],
    "partySize" integer
);


ALTER TABLE public.reservations OWNER TO postgres;

--
-- Name: servers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.servers (
    id character varying(50) NOT NULL,
    firstname character varying(30),
    lastname character varying(30)
);


ALTER TABLE public.servers OWNER TO postgres;

--
-- Name: tables; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tables (
    id character varying(50) NOT NULL,
    capacity integer,
    server character varying(30)
);


ALTER TABLE public.tables OWNER TO postgres;

--
-- Data for Name: business; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.business (email, password, "openFrom", "openUntil", "reservationSlots") FROM stdin;
alfredo	password	12:00:00	23:59:00	90
\.


--
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customers (email, firstname, lastname, password) FROM stdin;
john	john	doe	password
janedoe@email.com	jane	doe	password
max@email.com	max	payne	password
\.


--
-- Data for Name: reservations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reservations (id, "customerId", date, "time", "specialNotes", "tableId", "partySize") FROM stdin;
\.


--
-- Data for Name: servers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.servers (id, firstname, lastname) FROM stdin;
1	Peter	Parker
2	Alex	Johnson
3	Maria	Gonzalez
4	James	Smith
5	Linda	Brown
\.


--
-- Data for Name: tables; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tables (id, capacity, server) FROM stdin;
table1	2	1
table2	4	2
table3	6	3
table4	8	4
table5	10	5
table6	2	1
table7	4	2
table8	6	3
table9	8	4
table10	10	5
\.


--
-- Name: business business_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.business
    ADD CONSTRAINT business_pkey PRIMARY KEY (email);


--
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (email);


--
-- Name: reservations reservations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT reservations_pkey PRIMARY KEY (id);


--
-- Name: servers servers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servers
    ADD CONSTRAINT servers_pkey PRIMARY KEY (id);


--
-- Name: tables tables_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tables
    ADD CONSTRAINT tables_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

