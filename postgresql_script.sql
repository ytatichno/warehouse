--
-- PostgreSQL database dump
--

-- Dumped from database version 15.0
-- Dumped by pg_dump version 15.0

-- Started on 2022-11-30 13:26:20

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
-- TOC entry 218 (class 1259 OID 16692)
-- Name: credentials; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credentials (
    id integer NOT NULL,
    email character varying NOT NULL,
    salt character varying NOT NULL,
    saltmode smallint,
    pwd character varying NOT NULL,
    usercard integer
);


ALTER TABLE public.credentials OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16691)
-- Name: credentials_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.credentials_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.credentials_id_seq OWNER TO postgres;

--
-- TOC entry 3392 (class 0 OID 0)
-- Dependencies: 217
-- Name: credentials_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.credentials_id_seq OWNED BY public.credentials.id;


--
-- TOC entry 220 (class 1259 OID 16715)
-- Name: goods; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.goods (
    id integer NOT NULL,
    name character varying NOT NULL,
    descr character varying,
    totalnumber integer
);


ALTER TABLE public.goods OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16714)
-- Name: goods_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.goods_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.goods_id_seq OWNER TO postgres;

--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 219
-- Name: goods_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.goods_id_seq OWNED BY public.goods.id;


--
-- TOC entry 224 (class 1259 OID 16738)
-- Name: incoming; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.incoming (
    id integer NOT NULL,
    usercard integer,
    datetime timestamp without time zone,
    rack integer,
    good integer,
    number integer,
    satisfied boolean
);


ALTER TABLE public.incoming OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16737)
-- Name: incoming_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.incoming_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.incoming_id_seq OWNER TO postgres;

--
-- TOC entry 3394 (class 0 OID 0)
-- Dependencies: 223
-- Name: incoming_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.incoming_id_seq OWNED BY public.incoming.id;


--
-- TOC entry 226 (class 1259 OID 16760)
-- Name: outcoming; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.outcoming (
    id integer NOT NULL,
    usercard integer,
    datetime timestamp without time zone,
    rack integer,
    good integer,
    number integer,
    satisfied boolean
);


ALTER TABLE public.outcoming OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16759)
-- Name: outcoming_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.outcoming_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.outcoming_id_seq OWNER TO postgres;

--
-- TOC entry 3395 (class 0 OID 0)
-- Dependencies: 225
-- Name: outcoming_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.outcoming_id_seq OWNED BY public.outcoming.id;


--
-- TOC entry 222 (class 1259 OID 16724)
-- Name: racks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.racks (
    id integer NOT NULL,
    addr character varying NOT NULL,
    good integer,
    number integer
);


ALTER TABLE public.racks OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16723)
-- Name: racks_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.racks_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.racks_id_seq OWNER TO postgres;

--
-- TOC entry 3396 (class 0 OID 0)
-- Dependencies: 221
-- Name: racks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.racks_id_seq OWNED BY public.racks.id;


--
-- TOC entry 214 (class 1259 OID 16560)
-- Name: t; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t (
    id integer NOT NULL,
    name character varying NOT NULL,
    price integer DEFAULT 0
);


ALTER TABLE public.t OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16682)
-- Name: usercards; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usercards (
    id integer NOT NULL,
    lastname character varying,
    firstname character varying,
    birthday date,
    roles character varying DEFAULT '----'::character varying
);


ALTER TABLE public.usercards OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16681)
-- Name: usercards_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usercards_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usercards_id_seq OWNER TO postgres;

--
-- TOC entry 3397 (class 0 OID 0)
-- Dependencies: 215
-- Name: usercards_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usercards_id_seq OWNED BY public.usercards.id;


--
-- TOC entry 3205 (class 2604 OID 16695)
-- Name: credentials id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credentials ALTER COLUMN id SET DEFAULT nextval('public.credentials_id_seq'::regclass);


--
-- TOC entry 3206 (class 2604 OID 16718)
-- Name: goods id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.goods ALTER COLUMN id SET DEFAULT nextval('public.goods_id_seq'::regclass);


--
-- TOC entry 3208 (class 2604 OID 16741)
-- Name: incoming id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incoming ALTER COLUMN id SET DEFAULT nextval('public.incoming_id_seq'::regclass);


--
-- TOC entry 3209 (class 2604 OID 16763)
-- Name: outcoming id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.outcoming ALTER COLUMN id SET DEFAULT nextval('public.outcoming_id_seq'::regclass);


--
-- TOC entry 3207 (class 2604 OID 16727)
-- Name: racks id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.racks ALTER COLUMN id SET DEFAULT nextval('public.racks_id_seq'::regclass);


--
-- TOC entry 3203 (class 2604 OID 16685)
-- Name: usercards id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usercards ALTER COLUMN id SET DEFAULT nextval('public.usercards_id_seq'::regclass);


--
-- TOC entry 3378 (class 0 OID 16692)
-- Dependencies: 218
-- Data for Name: credentials; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.credentials (id, email, salt, saltmode, pwd, usercard) FROM stdin;
1	meowmeow	inSPb	0	hah	1
\.


--
-- TOC entry 3380 (class 0 OID 16715)
-- Dependencies: 220
-- Data for Name: goods; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.goods (id, name, descr, totalnumber) FROM stdin;
\.


--
-- TOC entry 3384 (class 0 OID 16738)
-- Dependencies: 224
-- Data for Name: incoming; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.incoming (id, usercard, datetime, rack, good, number, satisfied) FROM stdin;
\.


--
-- TOC entry 3386 (class 0 OID 16760)
-- Dependencies: 226
-- Data for Name: outcoming; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.outcoming (id, usercard, datetime, rack, good, number, satisfied) FROM stdin;
\.


--
-- TOC entry 3382 (class 0 OID 16724)
-- Dependencies: 222
-- Data for Name: racks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.racks (id, addr, good, number) FROM stdin;
\.


--
-- TOC entry 3374 (class 0 OID 16560)
-- Dependencies: 214
-- Data for Name: t; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t (id, name, price) FROM stdin;
\.


--
-- TOC entry 3376 (class 0 OID 16682)
-- Dependencies: 216
-- Data for Name: usercards; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usercards (id, lastname, firstname, birthday, roles) FROM stdin;
1	Safronov	\N	\N	----
\.


--
-- TOC entry 3398 (class 0 OID 0)
-- Dependencies: 217
-- Name: credentials_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.credentials_id_seq', 1, true);


--
-- TOC entry 3399 (class 0 OID 0)
-- Dependencies: 219
-- Name: goods_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.goods_id_seq', 1, false);


--
-- TOC entry 3400 (class 0 OID 0)
-- Dependencies: 223
-- Name: incoming_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.incoming_id_seq', 1, false);


--
-- TOC entry 3401 (class 0 OID 0)
-- Dependencies: 225
-- Name: outcoming_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.outcoming_id_seq', 1, false);


--
-- TOC entry 3402 (class 0 OID 0)
-- Dependencies: 221
-- Name: racks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.racks_id_seq', 1, false);


--
-- TOC entry 3403 (class 0 OID 0)
-- Dependencies: 215
-- Name: usercards_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usercards_id_seq', 1, true);


--
-- TOC entry 3215 (class 2606 OID 16699)
-- Name: credentials credentials_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT credentials_pkey PRIMARY KEY (id);


--
-- TOC entry 3217 (class 2606 OID 16722)
-- Name: goods goods_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.goods
    ADD CONSTRAINT goods_pkey PRIMARY KEY (id);


--
-- TOC entry 3221 (class 2606 OID 16743)
-- Name: incoming incoming_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incoming
    ADD CONSTRAINT incoming_pkey PRIMARY KEY (id);


--
-- TOC entry 3223 (class 2606 OID 16765)
-- Name: outcoming outcoming_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.outcoming
    ADD CONSTRAINT outcoming_pkey PRIMARY KEY (id);


--
-- TOC entry 3219 (class 2606 OID 16731)
-- Name: racks racks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.racks
    ADD CONSTRAINT racks_pkey PRIMARY KEY (id);


--
-- TOC entry 3211 (class 2606 OID 16567)
-- Name: t t_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t
    ADD CONSTRAINT t_pkey PRIMARY KEY (id);


--
-- TOC entry 3213 (class 2606 OID 16690)
-- Name: usercards usercards_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usercards
    ADD CONSTRAINT usercards_pkey PRIMARY KEY (id);


--
-- TOC entry 3224 (class 2606 OID 16700)
-- Name: credentials credentials_usercard_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT credentials_usercard_fkey FOREIGN KEY (usercard) REFERENCES public.usercards(id) ON DELETE SET NULL;


--
-- TOC entry 3226 (class 2606 OID 16754)
-- Name: incoming incoming_good_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incoming
    ADD CONSTRAINT incoming_good_fkey FOREIGN KEY (good) REFERENCES public.goods(id) ON DELETE SET NULL;


--
-- TOC entry 3227 (class 2606 OID 16749)
-- Name: incoming incoming_rack_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incoming
    ADD CONSTRAINT incoming_rack_fkey FOREIGN KEY (rack) REFERENCES public.racks(id) ON DELETE SET NULL;


--
-- TOC entry 3228 (class 2606 OID 16744)
-- Name: incoming incoming_usercard_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incoming
    ADD CONSTRAINT incoming_usercard_fkey FOREIGN KEY (usercard) REFERENCES public.usercards(id) ON DELETE SET NULL;


--
-- TOC entry 3229 (class 2606 OID 16776)
-- Name: outcoming outcoming_good_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.outcoming
    ADD CONSTRAINT outcoming_good_fkey FOREIGN KEY (good) REFERENCES public.goods(id) ON DELETE SET NULL;


--
-- TOC entry 3230 (class 2606 OID 16771)
-- Name: outcoming outcoming_rack_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.outcoming
    ADD CONSTRAINT outcoming_rack_fkey FOREIGN KEY (rack) REFERENCES public.racks(id) ON DELETE SET NULL;


--
-- TOC entry 3231 (class 2606 OID 16766)
-- Name: outcoming outcoming_usercard_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.outcoming
    ADD CONSTRAINT outcoming_usercard_fkey FOREIGN KEY (usercard) REFERENCES public.usercards(id) ON DELETE SET NULL;


--
-- TOC entry 3225 (class 2606 OID 16732)
-- Name: racks racks_good_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.racks
    ADD CONSTRAINT racks_good_fkey FOREIGN KEY (good) REFERENCES public.goods(id) ON DELETE SET NULL;


-- Completed on 2022-11-30 13:26:21

--
-- PostgreSQL database dump complete
--

