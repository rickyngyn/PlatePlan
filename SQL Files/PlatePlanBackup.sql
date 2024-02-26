--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

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
    open_from time(6) without time zone,
    open_until time(6) without time zone,
    reservation_slots integer
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
-- Name: menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menu (
    title character varying(200),
    price double precision,
    description character varying(500)
);


ALTER TABLE public.menu OWNER TO postgres;

--
-- Name: reservations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reservations (
    id character varying(50) NOT NULL,
    customer_id character varying(50),
    date date,
    "time" time(6) without time zone,
    special_notes character varying(200),
    table_id character varying(50),
    party_size integer,
    server character varying(50)
);


ALTER TABLE public.reservations OWNER TO postgres;

--
-- Name: restaurant_tables; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.restaurant_tables (
    id character varying(50) NOT NULL,
    capacity integer,
    server character varying(30)
);


ALTER TABLE public.restaurant_tables OWNER TO postgres;

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
-- Data for Name: business; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.business VALUES ('alfredo', 'password', '12:00:00', '23:59:00', 90);


--
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.customers VALUES ('john', 'john', 'doe', 'password');
INSERT INTO public.customers VALUES ('janedoe@email.com', 'jane', 'doe', 'password');
INSERT INTO public.customers VALUES ('max@email.com', 'max', 'payne', 'password');
INSERT INTO public.customers VALUES ('pouya@email.com', 'pouya', 'sameni', 'password');


--
-- Data for Name: menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.menu VALUES ('Margherita Pizza', 12.99, 'Classic pizza with fresh tomatoes, mozzarella cheese, and basil.');
INSERT INTO public.menu VALUES ('Caesar Salad', 8.5, 'Crisp romaine lettuce, parmesan cheese, and croutons, dressed with Caesar dressing.');
INSERT INTO public.menu VALUES ('Spaghetti Carbonara', 11, 'Spaghetti with creamy sauce, pancetta, and a touch of garlic.');
INSERT INTO public.menu VALUES ('Mushroom Risotto', 9.75, 'Creamy Arborio rice with wild mushrooms and a hint of truffle oil.');
INSERT INTO public.menu VALUES ('Beef Burger', 10.99, 'Grilled beef patty with lettuce, tomato, and our secret sauce, served with fries.');
INSERT INTO public.menu VALUES ('Grilled Salmon', 15.2, 'Freshly grilled salmon with a lemon butter sauce, served with vegetables.');
INSERT INTO public.menu VALUES ('Tomato Bruschetta', 7.5, 'Grilled bread with tomato, garlic, basil, and olive oil topping.');
INSERT INTO public.menu VALUES ('Tiramisu', 6.5, 'Classic Italian dessert made with ladyfingers, coffee, mascarpone, and cocoa.');
INSERT INTO public.menu VALUES ('Chicken Parmesan', 13.5, 'Breaded chicken breast topped with marinara sauce and melted cheese, served with pasta.');
INSERT INTO public.menu VALUES ('Vegetable Curry', 9, 'Mixed vegetables in a rich and creamy curry sauce, served with rice.');


--
-- Data for Name: reservations; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.reservations VALUES ('9c0869bc-e7b7-497d-a425-dff575333452', 'john', '2024-02-27', '15:00:00', '', 'table1', 1, 'Peter Parker');
INSERT INTO public.reservations VALUES ('f637dde6-e333-4e1c-a105-07933059797b', 'john', '2024-02-28', '12:00:00', '', 'table1', 2, 'Peter Parker');
INSERT INTO public.reservations VALUES ('0037bbfb-ae7b-46a5-a2ed-82672f1ae47a', 'john', '2024-02-26', '15:00:00', 'This is an anniversary', 'table1', 1, 'Peter Parker');


--
-- Data for Name: restaurant_tables; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.restaurant_tables VALUES ('table1', 2, '1');
INSERT INTO public.restaurant_tables VALUES ('table2', 4, '2');
INSERT INTO public.restaurant_tables VALUES ('table3', 6, '3');
INSERT INTO public.restaurant_tables VALUES ('table4', 8, '4');
INSERT INTO public.restaurant_tables VALUES ('table5', 10, '5');
INSERT INTO public.restaurant_tables VALUES ('table6', 2, '1');
INSERT INTO public.restaurant_tables VALUES ('table7', 4, '2');
INSERT INTO public.restaurant_tables VALUES ('table8', 6, '3');
INSERT INTO public.restaurant_tables VALUES ('table9', 8, '4');
INSERT INTO public.restaurant_tables VALUES ('table10', 10, '1');


--
-- Data for Name: servers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.servers VALUES ('1', 'Peter', 'Parker');
INSERT INTO public.servers VALUES ('2', 'Alex', 'Johnson');
INSERT INTO public.servers VALUES ('3', 'Maria', 'Gonzalez');
INSERT INTO public.servers VALUES ('4', 'James', 'Smith');
INSERT INTO public.servers VALUES ('5', 'Linda', 'Brown');


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
-- Name: restaurant_tables tables_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.restaurant_tables
    ADD CONSTRAINT tables_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

