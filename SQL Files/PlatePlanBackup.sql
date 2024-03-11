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
    reservation_slots integer,
    phone character varying(20),
    address character varying(500)
);


ALTER TABLE public.business OWNER TO postgres;

--
-- Name: menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menu (
    id character varying(200) NOT NULL,
    title character varying(200),
    price double precision,
    description character varying(500)
);


ALTER TABLE public.menu OWNER TO postgres;

--
-- Name: customer_menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer_menu (
)
INHERITS (public.menu);


ALTER TABLE public.customer_menu OWNER TO postgres;

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
-- Name: feedbacks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.feedbacks (
    id character varying(200) NOT NULL,
    customer_id character varying(200),
    rating integer,
    "timestamp" timestamp(0) without time zone,
    feedback character varying(500)
);


ALTER TABLE public.feedbacks OWNER TO postgres;

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

INSERT INTO public.business VALUES ('alfredo', 'password', '12:00:00', '23:59:00', 90, '(123) 456-7681', '123 Business St, Business City');


--
-- Data for Name: customer_menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.customer_menu VALUES ('4', 'Mushroom Risotto', 9.75, 'Creamy Arborio rice with wild mushrooms and a hint of truffle oil.');
INSERT INTO public.customer_menu VALUES ('5', 'Beef Burger', 10.99, 'Grilled beef patty with lettuce, tomato, and our secret sauce, served with fries.');
INSERT INTO public.customer_menu VALUES ('6', 'Grilled Salmon', 15.2, 'Freshly grilled salmon with a lemon butter sauce, served with vegetables.');
INSERT INTO public.customer_menu VALUES ('10', 'Vegetable Curry', 9, 'Mixed vegetables in a rich and creamy curry sauce, served with rice.');
INSERT INTO public.customer_menu VALUES ('7', 'Tomato Bruchett', 7.5, 'Grilled bread with tomato, garlic, basil, and olive oil topping.');
INSERT INTO public.customer_menu VALUES ('9dfc9014-d8f7-4f3e-a1d2-a1ef67a46093', 'Pecan Pie', 5.989999771118164, 'Pecan pie is a classic American dessert featuring a sweet, custard-like filling loaded with pecans, all encased in a flaky pastry crust.');
INSERT INTO public.customer_menu VALUES ('1', 'Margherita Pizza', 12.989999771118164, 'Classic pizza with fresh tomatoes, mozzarella cheese, and basil.');
INSERT INTO public.customer_menu VALUES ('9', 'Chicken Parmesan', 13.5, 'Breaded chicken breast topped with marinara sauce and melted cheese, served with pasta.');


--
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.customers VALUES ('john', 'john', 'doe', 'password');
INSERT INTO public.customers VALUES ('janedoe@email.com', 'jane', 'doe', 'password');
INSERT INTO public.customers VALUES ('max@email.com', 'max', 'payne', 'password');
INSERT INTO public.customers VALUES ('pouya@email.com', 'pouya', 'sameni', 'password');


--
-- Data for Name: feedbacks; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.feedbacks VALUES ('655', 'Anonymous', 1, '2024-02-07 23:26:29', 'Food was cold and unappetizing, which was surprising given the restaurant''s reputation. Will think twice before coming back.');
INSERT INTO public.feedbacks VALUES ('251', 'John Doe', 2, '2024-02-07 23:26:29', 'Not what I expected, quite bland. I had heard good things, so this was a letdown. Hope it improves.');
INSERT INTO public.feedbacks VALUES ('693', 'Jane Smith', 5, '2024-02-28 23:26:29', 'Excellent service and food! Every dish was a delight, and the staff went above and beyond to ensure a memorable experience.');
INSERT INTO public.feedbacks VALUES ('204', 'Anonymous', 5, '2024-02-17 23:26:29', 'Outstanding in every way! The attention to detail, the exquisite flavors, and the warm atmosphere made our dinner unforgettable.');
INSERT INTO public.feedbacks VALUES ('460', 'Linda Davis', 3, '2024-02-05 23:26:29', 'Average, nothing special. It was an okay visit, but nothing stood out to me as memorable or particularly enjoyable.');
INSERT INTO public.feedbacks VALUES ('778', 'David Wilson', 4, '2024-02-20 23:26:29', 'Enjoyed the meal, will come again. The dishes were well prepared, and I appreciated the unique flavors.');
INSERT INTO public.feedbacks VALUES ('160', 'Anonymous', 3, '2024-02-27 23:26:29', 'Average, nothing special. It was an okay visit, but nothing stood out to me as memorable or particularly enjoyable.');
INSERT INTO public.feedbacks VALUES ('390', 'Jennifer Garcia', 3, '2024-02-03 23:26:29', 'Okay food, but service lacks. I think with a bit more effort, this could be a great place, but it''s just not there yet.');
INSERT INTO public.feedbacks VALUES ('800', 'Anonymous', 4, '2024-02-15 14:35:20', 'Great ambiance and friendly service. The food was quite good, with some dishes being truly exceptional.');
INSERT INTO public.feedbacks VALUES ('801', 'Chris Evans', 2, '2024-02-16 17:30:45', 'The atmosphere was nice, but the food did not live up to expectations. Somewhat disappointed.');
INSERT INTO public.feedbacks VALUES ('802', 'Alex Johnson', 5, '2024-02-18 19:22:33', 'Fantastic experience! The culinary creativity is off the charts. Highly recommend.');
INSERT INTO public.feedbacks VALUES ('803', 'Sam Lee', 3, '2024-02-19 12:15:47', 'It was a decent meal, but I think the menu could use more variety. Service was good.');
INSERT INTO public.feedbacks VALUES ('804', 'Kimberly White', 1, '2024-02-21 20:45:10', 'Not worth the hype. Slow service and the food was below average. Unlikely to return.');
INSERT INTO public.feedbacks VALUES ('805', 'Patricia Brown', 4, '2024-02-22 18:30:55', 'Very enjoyable dinner with innovative dishes. A pleasant surprise and will visit again.');
INSERT INTO public.feedbacks VALUES ('806', 'Michael Scott', 3, '2024-02-23 16:40:29', 'The meal was okay, but nothing that makes me eager to come back. Just average.');
INSERT INTO public.feedbacks VALUES ('807', 'Anonymous', 4, '2024-02-24 21:55:13', 'Lovely place with a cozy atmosphere. The staff is welcoming, and the food is consistently good.');
INSERT INTO public.feedbacks VALUES ('808', 'Nancy Green', 2, '2024-02-25 13:05:22', 'Expected more based on reviews. The dishes were just okay, and the service was slow.');
INSERT INTO public.feedbacks VALUES ('809', 'Frank Wright', 5, '2024-02-26 22:10:36', 'An absolute gem! Every dish was delightful and presented beautifully. Will definitely return.');
INSERT INTO public.feedbacks VALUES ('810', 'Julia Roberts', 3, '2024-02-01 17:20:48', 'Had a better experience last time. The food was good but not outstanding. Service was great, though.');
INSERT INTO public.feedbacks VALUES ('811', 'Daniel Lewis', 1, '2024-02-02 15:35:29', 'Disappointing visit. Waited too long for our meals, and they were just lukewarm.');
INSERT INTO public.feedbacks VALUES ('812', 'Morgan Freeman', 4, '2024-02-04 18:45:55', 'Very good food with an excellent selection of wine. The ambiance adds to the experience.');
INSERT INTO public.feedbacks VALUES ('813', 'Emma Watson', 5, '2024-02-06 20:50:12', 'Incredible dining experience from start to finish. The staff made us feel special and the food was spectacular.');
INSERT INTO public.feedbacks VALUES ('814', 'Ryan Gosling', 2, '2024-02-08 12:30:44', 'Was expecting more from the rave reviews. Found the food to be quite average.');
INSERT INTO public.feedbacks VALUES ('815', 'Sophia Loren', 3, '2024-02-09 19:40:31', 'A nice place with solid service, but the food didn’t quite hit the mark for me.');
INSERT INTO public.feedbacks VALUES ('816', 'Leonardo DiCaprio', 4, '2024-02-11 14:25:53', 'Really enjoyed the meal here. The seafood dishes are a must-try!');
INSERT INTO public.feedbacks VALUES ('817', 'Sandra Bullock', 2, '2024-02-12 13:15:42', 'The service was good, but the food didn’t meet my expectations. Not sure I’d return.');
INSERT INTO public.feedbacks VALUES ('818', 'Tom Hanks', 5, '2024-02-13 22:05:29', 'Absolutely fantastic! The flavors are complex and satisfying. A must-visit for food lovers.');
INSERT INTO public.feedbacks VALUES ('819', 'Meryl Streep', 1, '2024-02-14 17:50:11', 'Very disappointed with the quality of the food. Expected much better.');


--
-- Data for Name: menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.menu VALUES ('4', 'Mushroom Risotto', 9.75, 'Creamy Arborio rice with wild mushrooms and a hint of truffle oil.');
INSERT INTO public.menu VALUES ('5', 'Beef Burger', 10.99, 'Grilled beef patty with lettuce, tomato, and our secret sauce, served with fries.');
INSERT INTO public.menu VALUES ('6', 'Grilled Salmon', 15.2, 'Freshly grilled salmon with a lemon butter sauce, served with vegetables.');
INSERT INTO public.menu VALUES ('10', 'Vegetable Curry', 9, 'Mixed vegetables in a rich and creamy curry sauce, served with rice.');
INSERT INTO public.menu VALUES ('7', 'Tomato Bruchett', 7.5, 'Grilled bread with tomato, garlic, basil, and olive oil topping.');
INSERT INTO public.menu VALUES ('9dfc9014-d8f7-4f3e-a1d2-a1ef67a46093', 'Pecan Pie', 5.989999771118164, 'Pecan pie is a classic American dessert featuring a sweet, custard-like filling loaded with pecans, all encased in a flaky pastry crust.');
INSERT INTO public.menu VALUES ('1', 'Margherita Pizza', 12.989999771118164, 'Classic pizza with fresh tomatoes, mozzarella cheese, and basil.');
INSERT INTO public.menu VALUES ('9', 'Chicken Parmesan', 13.5, 'Breaded chicken breast topped with marinara sauce and melted cheese, served with pasta.');


--
-- Data for Name: reservations; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.reservations VALUES ('85072301-79b0-4705-8be2-3175c1a1292d', 'john', '2024-02-19', '15:00:00', '', 'table1', 1, 'Peter Parker');
INSERT INTO public.reservations VALUES ('95c06bb9-fcb2-4679-bed4-29247836d6ea', 'john', '2024-02-27', '12:00:00', '', 'table1', 1, 'Peter Parker');
INSERT INTO public.reservations VALUES ('c8ebfc03-9c6c-4553-a10d-3e3516a0d984', 'idonotexist', '2024-02-27', '12:00:00', '', 'table1', 0, 'Peter Parker');
INSERT INTO public.reservations VALUES ('be02dcdd-0f5b-4460-bb4c-778dc8828d18', 'johndoe@example.com', '2024-03-02', '12:00:00', 'Near Window', 'table4', 8, 'James Smith');
INSERT INTO public.reservations VALUES ('9dcd2721-8e71-438c-814d-cafc81aa98d5', 'johndoe@example.com', '2024-03-02', '12:00:00', 'Near Window', 'table4', 8, 'James Smith');
INSERT INTO public.reservations VALUES ('28153cb5-c68f-4334-9876-9609d22ad347', 'johndoe@example.com', '2024-03-02', '12:00:00', 'Near Window', 'table4', 8, 'James Smith');
INSERT INTO public.reservations VALUES ('b84b7797-4b1e-4049-9c0c-fc54c6dd6936', 'johndoe@example.com', '2024-03-02', '12:00:00', 'Near Window', 'table4', 8, 'James Smith');
INSERT INTO public.reservations VALUES ('3173184f-b81a-418e-a4b8-492296ac0c5d', 'johndoe@example.com', '2024-03-02', '12:00:00', 'Near Window', 'table4', 8, 'James Smith');
INSERT INTO public.reservations VALUES ('b27632a6-c789-4c26-80c0-b9b30275881a', 'john', '2024-03-28', '18:00:00', '', 'table10', 7, 'Peter Parker');
INSERT INTO public.reservations VALUES ('c324a5de-9fd2-42d5-bcaf-f678cf98d0a9', 'john', '2024-03-30', '16:30:00', '', 'table10', 5, 'Peter Parker');
INSERT INTO public.reservations VALUES ('e5baaad8-75c4-48ab-86cd-afb2c7e689b3', 'john', '2024-03-24', '18:00:00', '', 'table10', 7, 'Peter Parker');


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
-- Name: customer_menu customer_menu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_menu
    ADD CONSTRAINT customer_menu_pkey PRIMARY KEY (id);


--
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (email);


--
-- Name: feedbacks feedback_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.feedbacks
    ADD CONSTRAINT feedback_pkey PRIMARY KEY (id);


--
-- Name: menu menu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu
    ADD CONSTRAINT menu_pkey PRIMARY KEY (id);


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

