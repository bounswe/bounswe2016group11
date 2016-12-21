--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

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
-- Name: account_emailaddress; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE account_emailaddress (
    id integer NOT NULL,
    email character varying(254) NOT NULL,
    verified boolean NOT NULL,
    "primary" boolean NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.account_emailaddress OWNER TO cocouser;

--
-- Name: account_emailaddress_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE account_emailaddress_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_emailaddress_id_seq OWNER TO cocouser;

--
-- Name: account_emailaddress_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE account_emailaddress_id_seq OWNED BY account_emailaddress.id;


--
-- Name: account_emailconfirmation; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE account_emailconfirmation (
    id integer NOT NULL,
    created timestamp with time zone NOT NULL,
    sent timestamp with time zone,
    key character varying(64) NOT NULL,
    email_address_id integer NOT NULL
);


ALTER TABLE public.account_emailconfirmation OWNER TO cocouser;

--
-- Name: account_emailconfirmation_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE account_emailconfirmation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_emailconfirmation_id_seq OWNER TO cocouser;

--
-- Name: account_emailconfirmation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE account_emailconfirmation_id_seq OWNED BY account_emailconfirmation.id;


--
-- Name: auth_group; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE auth_group (
    id integer NOT NULL,
    name character varying(80) NOT NULL
);


ALTER TABLE public.auth_group OWNER TO cocouser;

--
-- Name: auth_group_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE auth_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_group_id_seq OWNER TO cocouser;

--
-- Name: auth_group_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE auth_group_id_seq OWNED BY auth_group.id;


--
-- Name: auth_group_permissions; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE auth_group_permissions (
    id integer NOT NULL,
    group_id integer NOT NULL,
    permission_id integer NOT NULL
);


ALTER TABLE public.auth_group_permissions OWNER TO cocouser;

--
-- Name: auth_group_permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE auth_group_permissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_group_permissions_id_seq OWNER TO cocouser;

--
-- Name: auth_group_permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE auth_group_permissions_id_seq OWNED BY auth_group_permissions.id;


--
-- Name: auth_permission; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE auth_permission (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    content_type_id integer NOT NULL,
    codename character varying(100) NOT NULL
);


ALTER TABLE public.auth_permission OWNER TO cocouser;

--
-- Name: auth_permission_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE auth_permission_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_permission_id_seq OWNER TO cocouser;

--
-- Name: auth_permission_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE auth_permission_id_seq OWNED BY auth_permission.id;


--
-- Name: auth_user; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE auth_user (
    id integer NOT NULL,
    password character varying(128) NOT NULL,
    last_login timestamp with time zone,
    is_superuser boolean NOT NULL,
    username character varying(150) NOT NULL,
    first_name character varying(30) NOT NULL,
    last_name character varying(30) NOT NULL,
    email character varying(254) NOT NULL,
    is_staff boolean NOT NULL,
    is_active boolean NOT NULL,
    date_joined timestamp with time zone NOT NULL
);


ALTER TABLE public.auth_user OWNER TO cocouser;

--
-- Name: auth_user_groups; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE auth_user_groups (
    id integer NOT NULL,
    user_id integer NOT NULL,
    group_id integer NOT NULL
);


ALTER TABLE public.auth_user_groups OWNER TO cocouser;

--
-- Name: auth_user_groups_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE auth_user_groups_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_user_groups_id_seq OWNER TO cocouser;

--
-- Name: auth_user_groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE auth_user_groups_id_seq OWNED BY auth_user_groups.id;


--
-- Name: auth_user_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE auth_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_user_id_seq OWNER TO cocouser;

--
-- Name: auth_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE auth_user_id_seq OWNED BY auth_user.id;


--
-- Name: auth_user_user_permissions; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE auth_user_user_permissions (
    id integer NOT NULL,
    user_id integer NOT NULL,
    permission_id integer NOT NULL
);


ALTER TABLE public.auth_user_user_permissions OWNER TO cocouser;

--
-- Name: auth_user_user_permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE auth_user_user_permissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_user_user_permissions_id_seq OWNER TO cocouser;

--
-- Name: auth_user_user_permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE auth_user_user_permissions_id_seq OWNED BY auth_user_user_permissions.id;


--
-- Name: cocomapapp_post; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE cocomapapp_post (
    id integer NOT NULL,
    content text NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    topic_id integer,
    user_id integer NOT NULL
);


ALTER TABLE public.cocomapapp_post OWNER TO cocouser;

--
-- Name: cocomapapp_post_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE cocomapapp_post_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cocomapapp_post_id_seq OWNER TO cocouser;

--
-- Name: cocomapapp_post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE cocomapapp_post_id_seq OWNED BY cocomapapp_post.id;


--
-- Name: cocomapapp_post_tags; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE cocomapapp_post_tags (
    id integer NOT NULL,
    post_id integer NOT NULL,
    tag_id text NOT NULL
);


ALTER TABLE public.cocomapapp_post_tags OWNER TO cocouser;

--
-- Name: cocomapapp_post_tags_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE cocomapapp_post_tags_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cocomapapp_post_tags_id_seq OWNER TO cocouser;

--
-- Name: cocomapapp_post_tags_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE cocomapapp_post_tags_id_seq OWNED BY cocomapapp_post_tags.id;


--
-- Name: cocomapapp_relation; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE cocomapapp_relation (
    id integer NOT NULL,
    label text NOT NULL,
    topic_from_id integer NOT NULL,
    topic_to_id integer NOT NULL,
    negative_reaction_count integer NOT NULL,
    positive_reaction_count integer NOT NULL,
    CONSTRAINT cocomapapp_relation_negative_reaction_count_check CHECK ((negative_reaction_count >= 0)),
    CONSTRAINT cocomapapp_relation_positive_reaction_count_check CHECK ((positive_reaction_count >= 0))
);


ALTER TABLE public.cocomapapp_relation OWNER TO cocouser;

--
-- Name: cocomapapp_relation_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE cocomapapp_relation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cocomapapp_relation_id_seq OWNER TO cocouser;

--
-- Name: cocomapapp_relation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE cocomapapp_relation_id_seq OWNED BY cocomapapp_relation.id;


--
-- Name: cocomapapp_tag; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE cocomapapp_tag (
    "wikidataID" text NOT NULL,
    name text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    hidden_tags character varying(15)[]
);


ALTER TABLE public.cocomapapp_tag OWNER TO cocouser;

--
-- Name: cocomapapp_topic; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE cocomapapp_topic (
    id integer NOT NULL,
    name text NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.cocomapapp_topic OWNER TO cocouser;

--
-- Name: cocomapapp_topic_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE cocomapapp_topic_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cocomapapp_topic_id_seq OWNER TO cocouser;

--
-- Name: cocomapapp_topic_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE cocomapapp_topic_id_seq OWNED BY cocomapapp_topic.id;


--
-- Name: cocomapapp_topic_tags; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE cocomapapp_topic_tags (
    id integer NOT NULL,
    topic_id integer NOT NULL,
    tag_id text NOT NULL
);


ALTER TABLE public.cocomapapp_topic_tags OWNER TO cocouser;

--
-- Name: cocomapapp_topic_tags_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE cocomapapp_topic_tags_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cocomapapp_topic_tags_id_seq OWNER TO cocouser;

--
-- Name: cocomapapp_topic_tags_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE cocomapapp_topic_tags_id_seq OWNED BY cocomapapp_topic_tags.id;


--
-- Name: cocomapapp_visit; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE cocomapapp_visit (
    id integer NOT NULL,
    visit_date timestamp with time zone NOT NULL,
    topic_id integer NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.cocomapapp_visit OWNER TO cocouser;

--
-- Name: cocomapapp_visit_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE cocomapapp_visit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cocomapapp_visit_id_seq OWNER TO cocouser;

--
-- Name: cocomapapp_visit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE cocomapapp_visit_id_seq OWNED BY cocomapapp_visit.id;


--
-- Name: cocomapapp_vote; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE cocomapapp_vote (
    id integer NOT NULL,
    is_positive boolean NOT NULL,
    post_id integer NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.cocomapapp_vote OWNER TO cocouser;

--
-- Name: cocomapapp_vote_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE cocomapapp_vote_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cocomapapp_vote_id_seq OWNER TO cocouser;

--
-- Name: cocomapapp_vote_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE cocomapapp_vote_id_seq OWNED BY cocomapapp_vote.id;


--
-- Name: django_admin_log; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE django_admin_log (
    id integer NOT NULL,
    action_time timestamp with time zone NOT NULL,
    object_id text,
    object_repr character varying(200) NOT NULL,
    action_flag smallint NOT NULL,
    change_message text NOT NULL,
    content_type_id integer,
    user_id integer NOT NULL,
    CONSTRAINT django_admin_log_action_flag_check CHECK ((action_flag >= 0))
);


ALTER TABLE public.django_admin_log OWNER TO cocouser;

--
-- Name: django_admin_log_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE django_admin_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.django_admin_log_id_seq OWNER TO cocouser;

--
-- Name: django_admin_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE django_admin_log_id_seq OWNED BY django_admin_log.id;


--
-- Name: django_content_type; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE django_content_type (
    id integer NOT NULL,
    app_label character varying(100) NOT NULL,
    model character varying(100) NOT NULL
);


ALTER TABLE public.django_content_type OWNER TO cocouser;

--
-- Name: django_content_type_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE django_content_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.django_content_type_id_seq OWNER TO cocouser;

--
-- Name: django_content_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE django_content_type_id_seq OWNED BY django_content_type.id;


--
-- Name: django_migrations; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE django_migrations (
    id integer NOT NULL,
    app character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    applied timestamp with time zone NOT NULL
);


ALTER TABLE public.django_migrations OWNER TO cocouser;

--
-- Name: django_migrations_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE django_migrations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.django_migrations_id_seq OWNER TO cocouser;

--
-- Name: django_migrations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE django_migrations_id_seq OWNED BY django_migrations.id;


--
-- Name: django_session; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE django_session (
    session_key character varying(40) NOT NULL,
    session_data text NOT NULL,
    expire_date timestamp with time zone NOT NULL
);


ALTER TABLE public.django_session OWNER TO cocouser;

--
-- Name: django_site; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE django_site (
    id integer NOT NULL,
    domain character varying(100) NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.django_site OWNER TO cocouser;

--
-- Name: django_site_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE django_site_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.django_site_id_seq OWNER TO cocouser;

--
-- Name: django_site_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE django_site_id_seq OWNED BY django_site.id;


--
-- Name: socialaccount_socialaccount; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE socialaccount_socialaccount (
    id integer NOT NULL,
    provider character varying(30) NOT NULL,
    uid character varying(191) NOT NULL,
    last_login timestamp with time zone NOT NULL,
    date_joined timestamp with time zone NOT NULL,
    extra_data text NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.socialaccount_socialaccount OWNER TO cocouser;

--
-- Name: socialaccount_socialaccount_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE socialaccount_socialaccount_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.socialaccount_socialaccount_id_seq OWNER TO cocouser;

--
-- Name: socialaccount_socialaccount_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE socialaccount_socialaccount_id_seq OWNED BY socialaccount_socialaccount.id;


--
-- Name: socialaccount_socialapp; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE socialaccount_socialapp (
    id integer NOT NULL,
    provider character varying(30) NOT NULL,
    name character varying(40) NOT NULL,
    client_id character varying(191) NOT NULL,
    secret character varying(191) NOT NULL,
    key character varying(191) NOT NULL
);


ALTER TABLE public.socialaccount_socialapp OWNER TO cocouser;

--
-- Name: socialaccount_socialapp_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE socialaccount_socialapp_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.socialaccount_socialapp_id_seq OWNER TO cocouser;

--
-- Name: socialaccount_socialapp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE socialaccount_socialapp_id_seq OWNED BY socialaccount_socialapp.id;


--
-- Name: socialaccount_socialapp_sites; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE socialaccount_socialapp_sites (
    id integer NOT NULL,
    socialapp_id integer NOT NULL,
    site_id integer NOT NULL
);


ALTER TABLE public.socialaccount_socialapp_sites OWNER TO cocouser;

--
-- Name: socialaccount_socialapp_sites_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE socialaccount_socialapp_sites_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.socialaccount_socialapp_sites_id_seq OWNER TO cocouser;

--
-- Name: socialaccount_socialapp_sites_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE socialaccount_socialapp_sites_id_seq OWNED BY socialaccount_socialapp_sites.id;


--
-- Name: socialaccount_socialtoken; Type: TABLE; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE TABLE socialaccount_socialtoken (
    id integer NOT NULL,
    token text NOT NULL,
    token_secret text NOT NULL,
    expires_at timestamp with time zone,
    account_id integer NOT NULL,
    app_id integer NOT NULL
);


ALTER TABLE public.socialaccount_socialtoken OWNER TO cocouser;

--
-- Name: socialaccount_socialtoken_id_seq; Type: SEQUENCE; Schema: public; Owner: cocouser
--

CREATE SEQUENCE socialaccount_socialtoken_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.socialaccount_socialtoken_id_seq OWNER TO cocouser;

--
-- Name: socialaccount_socialtoken_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cocouser
--

ALTER SEQUENCE socialaccount_socialtoken_id_seq OWNED BY socialaccount_socialtoken.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY account_emailaddress ALTER COLUMN id SET DEFAULT nextval('account_emailaddress_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY account_emailconfirmation ALTER COLUMN id SET DEFAULT nextval('account_emailconfirmation_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY auth_group ALTER COLUMN id SET DEFAULT nextval('auth_group_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY auth_group_permissions ALTER COLUMN id SET DEFAULT nextval('auth_group_permissions_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY auth_permission ALTER COLUMN id SET DEFAULT nextval('auth_permission_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY auth_user ALTER COLUMN id SET DEFAULT nextval('auth_user_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY auth_user_groups ALTER COLUMN id SET DEFAULT nextval('auth_user_groups_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY auth_user_user_permissions ALTER COLUMN id SET DEFAULT nextval('auth_user_user_permissions_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_post ALTER COLUMN id SET DEFAULT nextval('cocomapapp_post_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_post_tags ALTER COLUMN id SET DEFAULT nextval('cocomapapp_post_tags_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_relation ALTER COLUMN id SET DEFAULT nextval('cocomapapp_relation_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_topic ALTER COLUMN id SET DEFAULT nextval('cocomapapp_topic_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_topic_tags ALTER COLUMN id SET DEFAULT nextval('cocomapapp_topic_tags_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_visit ALTER COLUMN id SET DEFAULT nextval('cocomapapp_visit_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_vote ALTER COLUMN id SET DEFAULT nextval('cocomapapp_vote_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY django_admin_log ALTER COLUMN id SET DEFAULT nextval('django_admin_log_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY django_content_type ALTER COLUMN id SET DEFAULT nextval('django_content_type_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY django_migrations ALTER COLUMN id SET DEFAULT nextval('django_migrations_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY django_site ALTER COLUMN id SET DEFAULT nextval('django_site_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY socialaccount_socialaccount ALTER COLUMN id SET DEFAULT nextval('socialaccount_socialaccount_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY socialaccount_socialapp ALTER COLUMN id SET DEFAULT nextval('socialaccount_socialapp_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY socialaccount_socialapp_sites ALTER COLUMN id SET DEFAULT nextval('socialaccount_socialapp_sites_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY socialaccount_socialtoken ALTER COLUMN id SET DEFAULT nextval('socialaccount_socialtoken_id_seq'::regclass);


--
-- Data for Name: account_emailaddress; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY account_emailaddress (id, email, verified, "primary", user_id) FROM stdin;
1	kasdsd@asdads.asdsad	f	t	1
2	mustafa@erdogan.com	f	t	2
3	busra@hotmail.com	f	t	3
4	mehmetozdemir94@gmail.com	f	t	4
5	ozgur.akyazi@boun.edu.tr	f	t	5
\.


--
-- Name: account_emailaddress_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('account_emailaddress_id_seq', 5, true);


--
-- Data for Name: account_emailconfirmation; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY account_emailconfirmation (id, created, sent, key, email_address_id) FROM stdin;
\.


--
-- Name: account_emailconfirmation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('account_emailconfirmation_id_seq', 1, false);


--
-- Data for Name: auth_group; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY auth_group (id, name) FROM stdin;
\.


--
-- Name: auth_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('auth_group_id_seq', 1, false);


--
-- Data for Name: auth_group_permissions; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY auth_group_permissions (id, group_id, permission_id) FROM stdin;
\.


--
-- Name: auth_group_permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('auth_group_permissions_id_seq', 1, false);


--
-- Data for Name: auth_permission; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY auth_permission (id, name, content_type_id, codename) FROM stdin;
1	Can add tag	1	add_tag
2	Can change tag	1	change_tag
3	Can delete tag	1	delete_tag
4	Can add post	2	add_post
5	Can change post	2	change_post
6	Can delete post	2	delete_post
7	Can add relation	3	add_relation
8	Can change relation	3	change_relation
9	Can delete relation	3	delete_relation
10	Can add vote	4	add_vote
11	Can change vote	4	change_vote
12	Can delete vote	4	delete_vote
13	Can add topic	5	add_topic
14	Can change topic	5	change_topic
15	Can delete topic	5	delete_topic
16	Can add visit	6	add_visit
17	Can change visit	6	change_visit
18	Can delete visit	6	delete_visit
19	Can add log entry	7	add_logentry
20	Can change log entry	7	change_logentry
21	Can delete log entry	7	delete_logentry
22	Can add group	8	add_group
23	Can change group	8	change_group
24	Can delete group	8	delete_group
25	Can add permission	9	add_permission
26	Can change permission	9	change_permission
27	Can delete permission	9	delete_permission
28	Can add user	10	add_user
29	Can change user	10	change_user
30	Can delete user	10	delete_user
31	Can add content type	11	add_contenttype
32	Can change content type	11	change_contenttype
33	Can delete content type	11	delete_contenttype
34	Can add session	12	add_session
35	Can change session	12	change_session
36	Can delete session	12	delete_session
37	Can add site	13	add_site
38	Can change site	13	change_site
39	Can delete site	13	delete_site
40	Can add email confirmation	14	add_emailconfirmation
41	Can change email confirmation	14	change_emailconfirmation
42	Can delete email confirmation	14	delete_emailconfirmation
43	Can add email address	15	add_emailaddress
44	Can change email address	15	change_emailaddress
45	Can delete email address	15	delete_emailaddress
46	Can add social account	16	add_socialaccount
47	Can change social account	16	change_socialaccount
48	Can delete social account	16	delete_socialaccount
49	Can add social application token	17	add_socialtoken
50	Can change social application token	17	change_socialtoken
51	Can delete social application token	17	delete_socialtoken
52	Can add social application	18	add_socialapp
53	Can change social application	18	change_socialapp
54	Can delete social application	18	delete_socialapp
\.


--
-- Name: auth_permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('auth_permission_id_seq', 54, true);


--
-- Data for Name: auth_user; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY auth_user (id, password, last_login, is_superuser, username, first_name, last_name, email, is_staff, is_active, date_joined) FROM stdin;
1	pbkdf2_sha256$30000$qc0CanRu5ST9$efG69O+roqHdHwyTV5kBUs3fJAi15naZOLhZRh4cQ8w=	2016-12-21 16:47:14.193553+00	f	ktekelioglu			kasdsd@asdads.asdsad	f	t	2016-12-21 16:47:14.138168+00
4	pbkdf2_sha256$30000$knLaFIhW5HK7$Yc/Ds2mxj3A55HyICGOlhLI7zOlrDbrU2fRw/a4mbOg=	2016-12-21 17:50:44.030424+00	f	ozdemir08			mehmetozdemir94@gmail.com	f	t	2016-12-21 17:26:30.490893+00
3	pbkdf2_sha256$30000$IeFUJP7cvZNV$EYTfuvxYmfRjeLkhCW6HRlikkX6QWlMfETD83CM7Lcc=	2016-12-21 17:51:05.816051+00	f	busra			busra@hotmail.com	f	t	2016-12-21 17:24:11.800045+00
5	pbkdf2_sha256$30000$XwiKB6mG7YHu$6toEXMNYt5KiMGOIAjyREVhoHlzsHQXuVI5RjWkkRYM=	2016-12-21 17:52:38.992314+00	f	xoxgur123			ozgur.akyazi@boun.edu.tr	f	t	2016-12-21 17:46:02.494544+00
2	pbkdf2_sha256$30000$UH02fURGOyeY$reuMB1EY80/4SEtMcoo1ogxK1nOE2znbagj5PA5eWS0=	2016-12-21 18:35:59.615552+00	f	mustafa			mustafa@erdogan.com	f	t	2016-12-21 17:22:07.308029+00
\.


--
-- Data for Name: auth_user_groups; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY auth_user_groups (id, user_id, group_id) FROM stdin;
\.


--
-- Name: auth_user_groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('auth_user_groups_id_seq', 1, false);


--
-- Name: auth_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('auth_user_id_seq', 5, true);


--
-- Data for Name: auth_user_user_permissions; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY auth_user_user_permissions (id, user_id, permission_id) FROM stdin;
\.


--
-- Name: auth_user_user_permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('auth_user_user_permissions_id_seq', 1, false);


--
-- Data for Name: cocomapapp_post; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY cocomapapp_post (id, content, created_at, updated_at, topic_id, user_id) FROM stdin;
1	Whitney Elizabeth Houston was an American singer, actress, producer, and model. 	2016-12-21 17:28:06.165125+00	2016-12-21 17:28:06.165148+00	4	2
2	Rock music is a genre of popular music that originated as "rock and roll" in the United States in the 1950s, and developed into a range of different styles in the 1960s and later, particularly in the United Kingdom and the United States. It has its roots in 1940s' and 1950s' rock and roll, itself heavily influenced by blues, rhythm and blues and country music. Rock music also drew strongly on a number of other genres such as electric blues and folk, and incorporated influences from jazz, classical and other musical sources.\n\nSource: https://en.wikipedia.org/wiki/Rock_music	2016-12-21 17:28:40.487312+00	2016-12-21 17:28:40.487335+00	5	1
5	"Blowin' in the Wind" is a song written by Bob Dylan in 1962 and released as a single and on his album The Freewheelin' Bob Dylan in 1963. Although it has been described as a protest song, it poses a series of rhetorical questions about peace, war and freedom. The refrain "The answer, my friend, is blowin' in the wind" has been described as "impenetrably ambiguous: either the answer is so obvious it is right in your face, or the answer is as intangible as the wind".	2016-12-21 17:37:25.853682+00	2016-12-21 17:37:25.853706+00	9	1
6	 She died on February 11, 2012	2016-12-21 17:41:34.05816+00	2016-12-21 17:41:34.058183+00	4	1
7	Aziz Sancar (born 8 September 1946) is a Turkish-American biochemist and molecular biologist specializing in DNA repair, cell cycle checkpoints, and circadian clock. In 2015, he was awarded the Nobel Prize in Chemistry along with Tomas Lindahl and Paul L. Modrich for their mechanistic studies of DNA repair.	2016-12-21 17:42:15.533801+00	2016-12-21 17:42:15.533823+00	12	2
8	He is the winner of the nobel prize in literature in 2016. Such a talent in both music and literature <3	2016-12-21 17:42:22.85654+00	2016-12-21 17:42:22.856562+00	1	3
9	The Nobel Prize is a set of annual international awards bestowed in a number of categories by Swedish and Norwegian institutions in recognition of academic, cultural, and/or scientific advances.	2016-12-21 17:43:40.802924+00	2016-12-21 17:43:40.802946+00	11	2
10	Behzat Ç. Bir Ankara Polisiyesi (Behzat Ç. An Ankara Detective Story) is a crime-and-detective television series based on the novels Her Temas İz Bırakır (Every Contact Leaves a Trace) and Son Hafriyat (Last Excavation) by Emrah Serbes. 	2016-12-21 17:45:38.032756+00	2016-12-21 17:45:38.032778+00	8	2
11	Art is a diverse range of human activities in creating visual, auditory or performing artifacts (artworks), expressing the author's imaginative or technical skill, intended to be appreciated for their beauty or emotional power. In their most general form these activities include the production of works of art, the criticism of art, the study of the history of art, and the aesthetic dissemination of art.	2016-12-21 17:47:09.377063+00	2016-12-21 17:47:09.377087+00	13	2
12	Although everyone accepts his talent in music, I think he didn't deserve being the owner of the nobel prize.	2016-12-21 17:51:28.223049+00	2016-12-21 17:51:28.22307+00	1	4
13	In 1994, the song was inducted into the Grammy Hall of Fame. In 2004, it was ranked number 14 on Rolling Stone magazine's list of the "500 Greatest Songs of All Time".	2016-12-21 17:52:01.101966+00	2016-12-21 17:52:01.101989+00	9	2
14	It is really interesting that "Muptezeller" was published after a short while when Emrah Serbes said that he retired from writing and focused on his amateur box career.	2016-12-21 17:53:08.037345+00	2016-12-21 17:53:08.037366+00	10	4
15	Music ended when Mozart died... :((	2016-12-21 17:53:25.011902+00	2016-12-21 17:53:25.011924+00	2	3
16	What a romantic song <3<3	2016-12-21 17:54:27.379094+00	2016-12-21 17:54:27.379115+00	6	3
17	It i a small kind of guitar. In Boğaziçi uni. , there is a facebook group about this instrument. :) Love it.	2016-12-21 17:55:41.404475+00	2016-12-21 17:55:41.404497+00	15	5
18	He is a piece of lion.	2016-12-21 17:55:44.343448+00	2016-12-21 17:55:44.343469+00	16	2
19	The word derives from Greek μουσική (mousike; "art of the Muses").[1] In Greek mythology, the nine muses were the goddesses who inspired literature, science, and the arts and who were the source of the knowledge embodied in the poetry, song-lyrics, and myths in the Greek culture.	2016-12-21 17:57:12.516505+00	2016-12-21 17:57:12.516527+00	2	4
20	spoken words fly away, written words remain	2016-12-21 17:58:12.680554+00	2016-12-21 17:58:12.680576+00	3	3
21	The painting, thought to be a portrait of Lisa Gherardini, the wife of Francesco del Giocondo, is in oil on a white Lombardy poplar panel, and is believed to have been painted between 1503 and 1506. Leonardo may have continued working on it as late as 1517. It was acquired by King Francis I of France and is now the property of the French Republic, on permanent display at the Louvre Museum in Paris since 1797.	2016-12-21 17:59:23.120984+00	2016-12-21 17:59:23.121007+00	17	2
22	I have just started to learn playing ukulele. It is extremely joyful. But I wasn't accepted the facebook group about this instrument :(	2016-12-21 18:01:08.709899+00	2016-12-21 18:01:08.709931+00	15	4
23	Literature, in its broadest sense, is any single body of written works. More restrictively, it is writing considered as an art form, or any single writing deemed to have artistic or intellectual value, often due to deploying language in ways that differ from ordinary usage. 	2016-12-21 18:02:00.158755+00	2016-12-21 18:02:00.158776+00	3	2
24	"He is the reason of unfair competition." \nAkhisar Manager Cihat Arslan	2016-12-21 18:02:36.054489+00	2016-12-21 18:02:36.054514+00	16	4
25	The individual images that make up a film are called frames. During projection of traditional films, a rotating shutter causes intervals of darkness as each frame in turn is moved into position to be projected, but the viewer does not notice the interruptions because of an effect known as persistence of vision, whereby the eye retains a visual image for a fraction of a second after the source has been removed. The perception of motion is due to a psychological effect called phi phenomenon.	2016-12-21 18:03:11.0244+00	2016-12-21 18:03:11.024421+00	18	2
26	Known for inventing dynamite, Nobel also owned Bofors, which he had redirected from its previous role as primarily an iron and steel producer to a major manufacturer of cannon and other armaments. Nobel held 355 different patents, dynamite being the most famous. After reading a premature obituary which condemned him for profiting from the sales of arms, he bequeathed his fortune to institute the Nobel Prizes.	2016-12-21 18:03:55.530703+00	2016-12-21 18:03:55.530726+00	19	1
27	The ukulele is commonly associated with music from Hawaii where the name roughly translates as "jumping flea"	2016-12-21 18:04:50.606329+00	2016-12-21 18:04:50.606354+00	15	2
28	That was the best song that I and my girl friend liked. But we broke up 2 years ago. She is now married with another man and when I listen to this song, I remember her and I still say "I will always love you" :(\n\n	2016-12-21 18:05:20.149778+00	2016-12-21 18:05:57.935494+00	6	4
3	The best song made ever. In 1992, R&B singer Whitney Houston recorded a cover version of "I Will Always Love You" for the soundtrack to The Bodyguard, her film debut. <br><iframe width="220" src="https://www.youtube.com/embed/E2k5ZHowndE" frameborder="0" allowfullscreen></iframe>	2016-12-21 17:33:26.431205+00	2016-12-21 18:51:03.558921+00	6	2
29	Dynamite is an explosive made of nitroglycerin, sorbents (such as powdered shells or clay) and stabilizers. It was invented by the Swedish chemist and engineer Alfred Nobel in Geesthacht, and patented in 1867. It rapidly gained wide-scale use as a safer alternative to gun powder and nitroglycerin.	2016-12-21 18:06:24.000675+00	2016-12-21 18:06:24.000697+00	21	1
30	Nobel's third and longest-lasting relationship was with Sofie Hess from Vienna, whom he met in 1876. The liaison lasted for 18 years.	2016-12-21 18:07:14.72652+00	2016-12-21 18:07:14.726545+00	19	2
31	a 1992 movie played by whitney houston and kevin costner	2016-12-21 18:07:35.191651+00	2016-12-21 18:07:35.191672+00	20	3
32	a 1992 movie played by whitney houston and kevin costner	2016-12-21 18:07:53.505239+00	2016-12-21 18:07:53.50526+00	20	3
33	Da Vinci used golden ratio while painting it.	2016-12-21 18:08:14.020461+00	2016-12-21 18:08:14.020483+00	17	4
34	There is a cliche about movies which have book such as "I have also watched that movie, but it didn't give me the joy that the book gave.".	2016-12-21 18:09:51.102511+00	2016-12-21 18:09:51.102533+00	18	4
35	i feel like livin in the 80s when listening to them	2016-12-21 18:10:13.784133+00	2016-12-21 18:10:13.784157+00	7	3
36	It is the reason of loss of many lives.	2016-12-21 18:11:33.875932+00	2016-12-21 18:11:33.875959+00	21	4
37	TNT is not a kind of dynamite.	2016-12-21 18:12:44.427192+00	2016-12-21 18:12:44.427213+00	21	2
38	It is the one of two best songs of Bob Dylan. The other one is "One more cup of coffee".	2016-12-21 18:12:57.53444+00	2016-12-21 18:12:57.534463+00	9	4
39	I have the video records	2016-12-21 18:15:07.962273+00	2016-12-21 18:15:07.962298+00	22	3
40	WHY STOCKHOLMMM??	2016-12-21 18:15:38.459518+00	2016-12-21 18:15:38.459541+00	22	3
42	Amélie won Best Film at the European Film Awards; it also won four César Awards (including Best Film and Best Director), two BAFTA Awards (including Best Original Screenplay), and was nominated for five Academy Awards. A Broadway adaptation premiered at the Berkeley Repertory Theatre in Berkeley, California in September 2015.	2016-12-21 18:17:27.70535+00	2016-12-21 18:17:27.705371+00	23	2
43	We know Emrah Serbes with his thrillers but in this book he tells a different story. The story is about the young men's energic but at the same time tragic lives.	2016-12-21 18:17:33.625966+00	2016-12-21 18:17:33.625988+00	14	4
44	what if it had never been existed 	2016-12-21 18:17:52.361421+00	2016-12-21 18:17:52.361454+00	13	3
45	It is my favourite movie. I am watching it every night I want to relax.	2016-12-21 18:19:16.982585+00	2016-12-21 18:19:16.982606+00	23	4
46	the soundtrack is very beautiful	2016-12-21 18:19:23.538368+00	2016-12-21 18:19:23.538392+00	23	3
47	I should have gone that concert	2016-12-21 18:19:39.021387+00	2016-12-21 18:19:39.021408+00	22	2
41	Been there. Wayyyy better than 2nd april 2007, bob dylan stockholm concert	2016-12-21 18:16:08.323764+00	2016-12-21 18:21:47.651186+00	22	1
48	The Dark Knight is a 2008 superhero thriller film directed, produced, and co-written by Christopher Nolan. Featuring the DC Comics character Batman, the film is the second part of Nolan's The Dark Knight Trilogy and a sequel to 2005's Batman Begins, starring an ensemble cast including Christian Bale, Michael Caine, Heath Ledger, Gary Oldman, Aaron Eckhart, Maggie Gyllenhaal and Morgan Freeman. Adore this film.	2016-12-21 18:20:00.807505+00	2016-12-21 18:21:58.591646+00	24	5
51	I love his musics.	2016-12-21 18:24:52.326873+00	2016-12-21 18:24:52.326898+00	25	2
52	Yann Tiersen (born 23 June 1970) is a French musician and composer. \n	2016-12-21 18:25:01.906719+00	2016-12-21 18:25:01.906744+00	25	4
53	he is an american actor born in 1955	2016-12-21 18:25:20.150358+00	2016-12-21 18:25:20.150377+00	26	3
54	i don't like his acting	2016-12-21 18:25:36.975175+00	2016-12-21 18:25:36.9752+00	26	3
56	There was a movie that he is in which was about 4 hours. It was like watching an infinite movie.	2016-12-21 18:26:24.238081+00	2016-12-21 18:26:24.238105+00	26	2
58	we must be proud of him	2016-12-21 18:26:58.992899+00	2016-12-21 18:26:58.992918+00	12	3
70	<iframe width="250" height="150" src="https://www.youtube.com/embed/EXeTwQWrcwY" frameborder="0" allowfullscreen></iframe>	2016-12-21 18:40:48.386221+00	2016-12-21 18:41:47.697005+00	24	5
57	<a href="http://ec2-54-186-167-76.us-west-2.compute.amazonaws.com:8000/cocomapapp/topics/27/">The CoCoMap</a>	2016-12-21 18:26:34.836884+00	2016-12-21 18:28:18.132055+00	27	5
59	he is a very handsome man <3	2016-12-21 18:30:12.939082+00	2016-12-21 18:30:12.939107+00	29	3
60	Christian Charles Philip Bale (born 30 January 1974) is an English actor. He has starred in both blockbuster films and smaller projects from independent producers and art houses. He is also the best BATMAN.	2016-12-21 18:31:05.179358+00	2016-12-21 18:31:05.179382+00	29	2
61	he is an english actor born in 1974	2016-12-21 18:31:21.265623+00	2016-12-21 18:31:21.265646+00	29	3
62	Yey!!!!	2016-12-21 18:31:54.806317+00	2016-12-21 18:31:54.806349+00	30	2
63	yuppiii!!	2016-12-21 18:32:21.294553+00	2016-12-21 18:32:21.294574+00	30	3
65	She is really sweet actress	2016-12-21 18:33:26.890702+00	2016-12-21 18:33:26.890724+00	28	2
64	<image src="http://screenmusings.org/Amelie/images/Amelie-0230.jpg", style="width:200px;height:100px;">	2016-12-21 18:32:53.146032+00	2016-12-21 18:33:39.198441+00	23	1
66	my favorite web site <3	2016-12-21 18:34:19.495883+00	2016-12-21 18:34:19.495907+00	27	3
67	As a developer, I am so happy to see this achievement	2016-12-21 18:36:05.131246+00	2016-12-21 18:36:05.131273+00	30	5
68	i think she is not that beautiful at all	2016-12-21 18:36:58.703554+00	2016-12-21 18:36:58.703578+00	28	3
69	He is like Ronaldinho. <blockquote class="twitter-tweet" data-lang="en"><p lang="eu" dir="ltr">Fernando Muslera! Sanki Ronaldinho... 👽⚽️🇺🇾🇧🇷 <a href="https://t.co/Mp7JB5bfiZ">pic.twitter.com/Mp7JB5bfiZ</a></p>&mdash; Webaslan.com (@webaslan) <a href="https://twitter.com/webaslan/status/782983907075624960">October 3, 2016</a></blockquote>\n<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>	2016-12-21 18:38:48.708593+00	2016-12-21 18:38:48.708615+00	16	4
49	This is the best movie ever. Heath Ledger won oscar with his performance as joker in this movie after his death. <br> <img src="https://upload.wikimedia.org/wikipedia/en/thumb/8/8a/Dark_Knight.jpg/220px-Dark_Knight.jpg" style="width:220px;">	2016-12-21 18:21:42.841174+00	2016-12-21 18:39:00.089599+00	24	2
72	<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/Bob_Dylan_-_Azkena_Rock_Festival_2010_2.jpg/220px-Bob_Dylan_-_Azkena_Rock_Festival_2010_2.jpg" alt="Dylan at Azkena Rock Festival in Vitoria-Gasteiz, Spain, in June 2010" style="width:220px;">	2016-12-21 18:42:09.313345+00	2016-12-21 18:42:09.313369+00	1	2
4	Guns N' Roses is an American hard rock band from Los Angeles formed in 1985. Their classic lineup, as signed to Geffen Records in 1986, consisted of vocalist Axl Rose, lead guitarist Slash, rhythm guitarist Izzy Stradlin, bassist Duff McKagan, and drummer Steven Adler. The current lineup consists of Rose, Slash, McKagan, keyboardists Dizzy Reed and Melissa Reese, guitarist Richard Fortus and drummer Frank Ferrer. <br> <img src="https://static.rukkus.com/images/performer/headshots/guns-n-roses-tickets.jpg.870x570_q70_crop-smart_upscale.jpg" style="width:220px;">	2016-12-21 17:36:25.123327+00	2016-12-21 18:45:16.694818+00	7	2
74	I am looking forward to reading it!	2016-12-21 18:46:12.696606+00	2016-12-21 18:46:12.696627+00	10	3
73	good music	2016-12-21 18:44:28.684881+00	2016-12-21 18:44:28.684902+00	5	3
71	You can learn playing the song "Losing My Religion" via the Ukulele Teacher. <br/> \n<iframe width="220" height="150" src="https://www.youtube.com/embed/cEARK95ZHUo" frameborder="0" allowfullscreen></iframe>	2016-12-21 18:41:56.128715+00	2016-12-21 18:44:35.30448+00	15	4
75	I did not read this book but it's in my reading list.	2016-12-21 18:46:14.818838+00	2016-12-21 18:46:14.81886+00	14	2
76	He is an amateur boxer.	2016-12-21 18:46:22.737494+00	2016-12-21 18:46:22.737516+00	8	4
77	I like reading his books. He is also in a society of artists called "Afili Filintalar"	2016-12-21 18:46:50.831564+00	2016-12-21 18:46:50.831589+00	8	5
78	winners gain prestige	2016-12-21 18:47:19.423705+00	2016-12-21 18:47:19.423726+00	11	3
\.


--
-- Name: cocomapapp_post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('cocomapapp_post_id_seq', 78, true);


--
-- Data for Name: cocomapapp_post_tags; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY cocomapapp_post_tags (id, post_id, tag_id) FROM stdin;
1	1	Q177220
2	2	Q16247949
3	2	Q3933727
4	2	Q11399
12	5	Q3933727
13	5	Q640529
14	6	Q34389
15	6	Q393832
16	8	Q37922
17	10	Q4881030
18	14	Q6050007
19	15	Q14915027
20	17	Q638
21	18	Q495299
22	19	Q5602028
23	20	Q8171
24	22	Q355
25	23	Q52
26	25	Q11424
27	26	Q52
28	26	Q23810
29	27	Q782
31	28	Q316
32	29	Q16247949
33	29	Q3933727
34	30	Q23810
35	31	Q11930
36	31	Q34389
37	32	Q11930
38	32	Q34389
39	33	Q41690
40	38	Q3882521
42	42	Q139184
43	42	Q223740
44	42	Q19020
45	43	Q80930
49	41	Q16868788
50	48	Q48337
51	48	Q45772
53	51	Q157256
54	52	Q1121767
59	57	Q625420
60	57	Q7303332
61	60	Q2695156
62	60	Q33999
64	65	Q304123
65	64	Q18171830
66	67	Q2988681
67	69	Q7365428
68	49	Q40572
73	71	Q18809065
74	4	Q212699
75	4	Q11885
76	4	Q34166
77	4	Q320621
78	4	Q251865
79	76	Q227380
80	77	Q19611285
85	3	Q3790522
86	3	Q34389
\.


--
-- Name: cocomapapp_post_tags_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('cocomapapp_post_tags_id_seq', 86, true);


--
-- Data for Name: cocomapapp_relation; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY cocomapapp_relation (id, label, topic_from_id, topic_to_id, negative_reaction_count, positive_reaction_count) FROM stdin;
1	Is A Kind Of	5	2	0	0
2	makes	1	2	0	0
3	is awarded Nobel prize on	1	3	0	0
4	makes	4	2	0	0
5	song of	6	4	0	0
6	is a group of	7	2	0	0
7	covered the song of	7	1	0	0
8	Makes	1	5	0	0
9	genre	7	5	0	0
10	has the best	6	2	0	0
11	Is a Song By	9	1	0	0
12	is a work of	10	3	0	0
13	written by	10	8	0	0
14	is awarded on	1	11	0	0
15	has won	12	11	0	0
16	Is a Form Of	2	13	0	0
17	Is a Form Of	3	13	0	0
18	Is a Book By	14	8	0	0
19	in scope of	15	13	0	0
20	performs kind of	16	13	0	0
21	 the most parodied work of	17	13	0	0
22	Founded	19	11	0	0
23	lead role	20	4	0	0
24	is a type of 	20	18	0	0
25	soundtrack of	6	20	0	0
26	Is Invented By	21	19	0	0
27	will be performed by	22	1	0	0
28	is a	23	18	0	0
29	in category	24	18	0	0
30	is also	23	13	0	0
31	created soundtracks of 	25	23	0	0
32	played	26	20	0	0
33	is a piece of	27	13	0	0
34	played in 	28	23	0	0
35	plays in 	29	24	0	0
36	in	30	27	0	0
37	colleague	29	26	0	0
38	is a work of	14	3	0	0
39	in  scope of	9	2	0	0
40	was performed	22	2	0	0
41	loves	16	1	0	0
42	not interested in	12	22	0	0
43	plays like singing of	16	4	0	0
44	composer of	25	13	0	0
45	colleague	19	12	0	0
46	is played by	15	4	0	0
47	is dedicated to	30	16	0	0
48	plays	28	15	0	0
49	can be counted as	21	13	0	0
\.


--
-- Name: cocomapapp_relation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('cocomapapp_relation_id_seq', 49, true);


--
-- Data for Name: cocomapapp_tag; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY cocomapapp_tag ("wikidataID", name, created_at, updated_at, hidden_tags) FROM stdin;
Q203749	Fernando Muslera	2016-12-21 17:54:28.326801+00	2016-12-21 17:54:28.328052+00	{Q77,Q38,Q5,Q2736,Q414}
Q638	music	2016-12-21 17:25:27.581686+00	2016-12-21 17:55:41.408314+00	{Q735,Q11461,Q173799}
Q495299	Galatasaray S.K.	2016-12-21 17:55:44.350697+00	2016-12-21 17:55:44.351803+00	{Q476028,Q43}
Q177220	singer	2016-12-21 17:28:06.167848+00	2016-12-21 17:28:06.16888+00	{Q27939,Q28640,Q2643890}
Q5602028	Greek mythology in popular culture	2016-12-21 17:57:12.519197+00	2016-12-21 17:57:12.520291+00	\N
Q8171	word	2016-12-21 17:58:12.684479+00	2016-12-21 17:58:12.68612+00	{Q2944660}
Q11399	rock music	2016-12-21 17:28:40.467182+00	2016-12-21 17:32:56.458467+00	{Q188451,Q373342}
Q34389	Whitney Houston	2016-12-21 17:27:05.921207+00	2016-12-21 18:51:03.555184+00	{Q222749,Q959049,Q932586,Q5994,Q30,Q5,Q11424,Q207628,Q638,Q27939,Q17143560}
Q11885	Axl Rose	2016-12-21 17:36:25.138784+00	2016-12-21 18:45:16.667889+00	{Q5994,Q30,Q5,Q27939,Q638}
Q34166	Slash	2016-12-21 17:36:25.149444+00	2016-12-21 18:45:16.672299+00	{Q18074899,Q5,Q145,Q207628,Q258896,Q17344835}
Q320621	Izzy Stradlin	2016-12-21 17:36:25.160323+00	2016-12-21 18:45:16.686878+00	{Q18074899,Q30,Q5,Q27939}
Q251865	Duff McKagan	2016-12-21 17:36:25.171892+00	2016-12-21 18:45:16.691188+00	{Q36180,Q30,Q11030,Q5,Q638,Q27939}
Q3790522	I Will Always Love You: The Best of Whitney Houston	2016-12-21 17:33:26.450475+00	2016-12-21 18:51:03.5504+00	{Q222910}
Q762	Leonardo da Vinci	2016-12-21 17:58:49.445584+00	2016-12-21 17:58:49.447297+00	{Q336,Q77590,Q11634,Q385378,Q5891,Q482,Q5,Q333,Q1889,Q395,Q514,Q638,Q207628,Q11023,Q11629,Q148540,Q18119757}
Q7366	song	2016-12-21 17:37:25.835573+00	2016-12-21 17:37:25.836774+00	{Q7725634,Q207628,Q862597}
Q392	Bob Dylan	2016-12-21 17:25:19.822543+00	2016-12-21 17:37:25.841853+00	{Q103076,Q8242,Q222749,Q959049,Q932586,Q30,Q3429919,Q5,Q482,Q18074899,Q11424,Q207628,Q11629}
Q12418	Mona Lisa	2016-12-21 17:58:49.453261+00	2016-12-21 17:58:49.454996+00	{Q3305213,Q142}
Q484048	Amélie	2016-12-21 18:15:35.450913+00	2016-12-21 18:15:35.452021+00	{Q11424}
Q640529	Blowin' in the Wind	2016-12-21 17:37:25.846361+00	2016-12-21 17:37:25.860806+00	{Q134556}
Q8242	literature	2016-12-21 17:26:54.860457+00	2016-12-21 17:38:25.154432+00	{Q735}
Q393832	Death	2016-12-21 17:41:34.08224+00	2016-12-21 17:41:34.083301+00	{Q4167410}
Q15118973	Aziz Sancar	2016-12-21 17:41:36.538952+00	2016-12-21 17:41:36.54756+00	{Q336,Q7094,Q30,Q5,Q3918,Q7202,Q43}
Q37922	Nobel Prize in Literature	2016-12-21 17:42:22.859194+00	2016-12-21 17:42:22.860279+00	{Q7191,Q378427}
Q735	art	2016-12-21 17:25:27.587326+00	2016-12-21 17:43:34.962705+00	{Q2018526,Q3249551,Q1914636}
Q4881030	Behzat Ç. Bir Ankara Polisiyesi	2016-12-21 17:45:38.038496+00	2016-12-21 17:45:38.039646+00	{Q5398426}
Q571	book	2016-12-21 17:38:25.139383+00	2016-12-21 17:47:40.945051+00	{Q2342494,Q49848,Q732577,Q18593264}
Q355	Facebook	2016-12-21 18:01:08.721088+00	2016-12-21 18:01:08.72298+00	{Q3220391,Q30}
Q61285	ukulele	2016-12-21 17:50:23.971274+00	2016-12-21 17:50:23.972522+00	{Q230262}
Q6607	guitar	2016-12-21 17:50:23.977328+00	2016-12-21 17:50:23.978432+00	{Q230262}
Q6050007	Emrah Serbes	2016-12-21 17:36:35.292898+00	2016-12-21 17:53:08.039878+00	{Q103076,Q8242,Q36180,Q3429919,Q11030,Q5,Q58854,Q43}
Q14915027	Mozart	2016-12-21 17:53:25.014395+00	2016-12-21 17:53:25.015447+00	{Q3565868}
Q2512663	Movie	2016-12-21 18:01:52.410049+00	2016-12-21 18:01:52.411201+00	{Q4167410}
Q11930	Kevin Costner	2016-12-21 18:07:35.194128+00	2016-12-21 18:07:53.518857+00	{Q222749,Q18074899,Q932586,Q30,Q5,Q11424,Q506240,Q638,Q27939,Q5398426,Q17344835}
Q11424	film	2016-12-21 18:03:11.02701+00	2016-12-21 18:03:11.028105+00	{Q17537576,Q2431196,Q838948}
Q52	Wikipedia	2016-12-21 18:02:00.161277+00	2016-12-21 18:03:55.532872+00	{Q615699,Q14827288,Q15633582,Q1063801}
Q725946	The Bodyguard	2016-12-21 18:04:30.596434+00	2016-12-21 18:04:30.59756+00	{Q11424}
Q782	Hawaii	2016-12-21 18:04:50.612031+00	2016-12-21 18:04:50.613746+00	{Q18094,Q538,Q35657,Q30,Q33569,Q1860}
Q41690	golden ratio	2016-12-21 18:08:14.023658+00	2016-12-21 18:08:14.024747+00	{Q607728,Q186509,Q1321926,Q168817}
Q316	love	2016-12-21 18:05:20.152278+00	2016-12-21 18:05:57.927544+00	{Q9415,Q16748888}
Q80728	dynamite	2016-12-21 18:06:23.991449+00	2016-12-21 18:06:23.992982+00	{Q12870}
Q16247949	Definition	2016-12-21 17:28:40.489511+00	2016-12-21 18:06:24.014436+00	{Q169930}
Q3933727	Wikipedia	2016-12-21 17:28:40.495402+00	2016-12-21 18:06:24.019603+00	{Q4167410}
Q23810	Alfred Nobel	2016-12-21 18:03:55.523075+00	2016-12-21 18:07:14.734692+00	{Q3908516,Q34,Q5,Q2329,Q11023,Q18119757}
Q3882521	One More Cup of Coffee (Valley Below)	2016-12-21 18:12:57.537407+00	2016-12-21 18:12:57.550678+00	{Q7366}
Q182832	concert	2016-12-21 18:14:10.382237+00	2016-12-21 18:14:10.384556+00	{Q6942562}
Q1754	Stockholm	2016-12-21 18:14:10.390457+00	2016-12-21 18:14:10.392142+00	{Q3572,Q1899,Q1748,Q11194,Q1549591,Q3711,Q168652,Q515,Q1773,Q1770,Q13670,Q47094,Q1489,Q585,Q1757,Q226,Q216,Q10704,Q406,Q23564,Q19689,Q1764,Q34,Q5119,Q51103,Q656,Q3624}
Q45772	Christian Bale	2016-12-21 18:20:00.826915+00	2016-12-21 18:21:58.587274+00	{Q222749,Q184485,Q5,Q11424,Q145,Q22920017}
Q139184	BAFTA Award for Best Film	2016-12-21 18:17:27.707964+00	2016-12-21 18:17:27.70908+00	{Q732997}
Q223740	European Film Awards	2016-12-21 18:17:27.713968+00	2016-12-21 18:17:27.714988+00	{Q4220917}
Q19020	Academy Awards	2016-12-21 18:17:27.719451+00	2016-12-21 18:17:27.720617+00	{Q30,Q4220917}
Q80930	tragedy	2016-12-21 18:17:33.630972+00	2016-12-21 18:17:33.640481+00	{Q15850590,Q25372}
Q163872	The Dark Knight	2016-12-21 18:17:59.581664+00	2016-12-21 18:17:59.588818+00	{Q11424}
Q40572	Heath Ledger	2016-12-21 18:21:42.850276+00	2016-12-21 18:39:00.085151+00	{Q222749,Q184485,Q932586,Q5,Q408,Q11424,Q506240,Q5398426}
Q212699	Geffen Records	2016-12-21 17:36:25.128599+00	2016-12-21 18:45:16.663132+00	{Q18127}
Q2695156	Batman	2016-12-21 18:17:59.593554+00	2016-12-21 18:31:05.18274+00	{Q15632617,Q44554}
Q16868788	Bragging rights	2016-12-21 18:16:08.326443+00	2016-12-21 18:21:47.646733+00	{Q21278897}
Q639669	musician	2016-12-21 18:21:54.513683+00	2016-12-21 18:21:54.514855+00	{Q16010345,Q28640,Q483501,Q638}
Q48337	Morgan Freeman	2016-12-21 18:20:00.819399+00	2016-12-21 18:21:58.581593+00	{Q222749,Q184485,Q932586,Q30,Q5,Q11424,Q506240,Q5398426,Q765633}
Q15732375	Kevin Costner filmography	2016-12-21 18:23:32.662127+00	2016-12-21 18:23:32.663254+00	{Q1371849}
Q157256	Yann Tiersen	2016-12-21 18:24:52.329863+00	2016-12-21 18:24:52.331678+00	{Q27939,Q5994,Q5,Q142,Q492264,Q207628}
Q1121767	Composer	2016-12-21 18:25:01.909753+00	2016-12-21 18:25:01.912289+00	{Q4167410}
Q1045105	meta-	2016-12-21 18:26:34.823772+00	2016-12-21 18:26:34.824837+00	{Q134830}
Q3109046	this	2016-12-21 18:26:34.829665+00	2016-12-21 18:26:34.830726+00	\N
Q625420	perfect	2016-12-21 18:26:34.839252+00	2016-12-21 18:28:18.122913+00	{Q2374489,Q208084}
Q7303332	Recursion	2016-12-21 18:26:34.845277+00	2016-12-21 18:28:18.128408+00	{Q571}
Q3664194	Celebration	2016-12-21 18:30:59.399149+00	2016-12-21 18:30:59.41129+00	{Q482994}
Q228193	Coco	2016-12-21 18:30:59.416013+00	2016-12-21 18:30:59.417116+00	{Q4167410}
Q33999	actor	2016-12-21 18:29:19.880087+00	2016-12-21 18:31:05.188434+00	{Q222749,Q28640,Q483501}
Q304123	Audrey Tautou	2016-12-21 18:33:26.893282+00	2016-12-21 18:33:26.894284+00	{Q222749,Q5,Q17143560,Q142}
Q18171830	Amelie	2016-12-21 18:32:53.149307+00	2016-12-21 18:33:39.194482+00	{Q11879590}
Q2988681	achievement	2016-12-21 18:36:05.136979+00	2016-12-21 18:36:05.138632+00	\N
Q7365428	Ronaldinho Gaucho	2016-12-21 18:38:48.711504+00	2016-12-21 18:38:48.713519+00	\N
Q18809065	R.E.M.	2016-12-21 18:41:56.131458+00	2016-12-21 18:44:35.300028+00	{Q482994}
Q227380	Box	2016-12-21 18:46:22.740624+00	2016-12-21 18:46:22.743998+00	{Q4167410}
Q19611285	Filinta	2016-12-21 18:46:50.834855+00	2016-12-21 18:46:50.837429+00	{Q5398426}
\.


--
-- Data for Name: cocomapapp_topic; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY cocomapapp_topic (id, name, created_at, updated_at, user_id) FROM stdin;
1	Bob Dylan	2016-12-21 17:25:19.819906+00	2016-12-21 17:25:19.819929+00	2
2	Music	2016-12-21 17:25:27.579585+00	2016-12-21 17:25:27.579606+00	3
3	Literature	2016-12-21 17:26:54.858298+00	2016-12-21 17:26:54.858319+00	3
4	whitney houston	2016-12-21 17:27:05.918133+00	2016-12-21 17:27:05.918155+00	2
5	Rock	2016-12-21 17:28:40.465087+00	2016-12-21 17:28:40.465108+00	1
6	I Will Always Love You	2016-12-21 17:31:34.775936+00	2016-12-21 17:31:34.775958+00	2
7	Guns N Roses	2016-12-21 17:32:56.456294+00	2016-12-21 17:32:56.456315+00	3
8	Emrah Serbes	2016-12-21 17:36:35.280341+00	2016-12-21 17:36:35.280363+00	3
9	Blowin' in the Wind	2016-12-21 17:37:25.833252+00	2016-12-21 17:37:25.833272+00	1
10	Müptezeller	2016-12-21 17:38:25.137168+00	2016-12-21 17:38:25.137189+00	3
11	Nobel Prize	2016-12-21 17:39:42.884368+00	2016-12-21 17:39:42.884388+00	2
12	Aziz Sancar	2016-12-21 17:41:36.528612+00	2016-12-21 17:41:36.528633+00	2
13	Art	2016-12-21 17:43:34.960608+00	2016-12-21 17:43:34.960629+00	1
14	Erken Kaybedenler	2016-12-21 17:47:40.937219+00	2016-12-21 17:47:40.937239+00	1
15	Ukulele	2016-12-21 17:50:23.964022+00	2016-12-21 17:50:23.964044+00	5
16	Fernando Muslera	2016-12-21 17:54:28.324629+00	2016-12-21 17:54:28.324651+00	2
17	Mona Lisa	2016-12-21 17:58:49.426281+00	2016-12-21 17:58:49.426304+00	2
18	movie	2016-12-21 18:01:52.407742+00	2016-12-21 18:01:52.407764+00	3
19	Alfred Nobel	2016-12-21 18:03:55.520762+00	2016-12-21 18:03:55.520783+00	1
20	bodyguard	2016-12-21 18:04:30.58474+00	2016-12-21 18:04:30.584761+00	3
21	Dynamite	2016-12-21 18:06:23.988079+00	2016-12-21 18:06:23.9881+00	1
22	1st april 2007, bob dylan stockholm concert	2016-12-21 18:14:10.378731+00	2016-12-21 18:14:10.378751+00	3
23	Amelie	2016-12-21 18:15:35.448751+00	2016-12-21 18:15:35.448776+00	2
24	Batman Dark Knight	2016-12-21 18:17:59.579576+00	2016-12-21 18:17:59.579597+00	5
25	Yann Tiersen	2016-12-21 18:21:54.511398+00	2016-12-21 18:21:54.511419+00	4
26	kevin costner	2016-12-21 18:23:32.65986+00	2016-12-21 18:23:32.65988+00	3
27	CoCoMap	2016-12-21 18:26:34.821507+00	2016-12-21 18:26:34.821539+00	5
28	Audrey Tautou	2016-12-21 18:29:06.43614+00	2016-12-21 18:29:06.436162+00	4
29	christian bale	2016-12-21 18:29:19.877722+00	2016-12-21 18:29:19.877744+00	3
30	Our First 30th Topic	2016-12-21 18:30:59.396934+00	2016-12-21 18:30:59.396956+00	5
\.


--
-- Name: cocomapapp_topic_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('cocomapapp_topic_id_seq', 30, true);


--
-- Data for Name: cocomapapp_topic_tags; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY cocomapapp_topic_tags (id, topic_id, tag_id) FROM stdin;
1	1	Q392
2	2	Q638
3	2	Q735
4	3	Q8242
5	4	Q34389
6	5	Q11399
7	5	Q638
8	7	Q11399
9	8	Q6050007
10	8	Q8242
11	9	Q7366
12	9	Q392
13	9	Q640529
14	10	Q571
15	10	Q8242
16	12	Q15118973
17	13	Q735
18	14	Q571
19	14	Q6050007
20	15	Q61285
21	15	Q6607
22	16	Q203749
23	17	Q762
24	17	Q12418
25	18	Q2512663
26	19	Q23810
27	20	Q725946
28	21	Q80728
29	22	Q182832
30	22	Q1754
31	23	Q484048
32	24	Q163872
33	24	Q2695156
34	25	Q639669
35	26	Q15732375
36	27	Q1045105
37	27	Q3109046
38	29	Q33999
39	30	Q3664194
40	30	Q228193
\.


--
-- Name: cocomapapp_topic_tags_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('cocomapapp_topic_tags_id_seq', 40, true);


--
-- Data for Name: cocomapapp_visit; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY cocomapapp_visit (id, visit_date, topic_id, user_id) FROM stdin;
1	2016-12-21 17:25:31.886645+00	1	2
2	2016-12-21 17:25:49.543663+00	2	1
3	2016-12-21 17:27:43.633578+00	4	2
4	2016-12-21 17:28:08.827238+00	4	2
5	2016-12-21 17:29:36.753167+00	5	1
6	2016-12-21 17:31:46.06718+00	6	2
7	2016-12-21 17:33:29.13742+00	6	2
8	2016-12-21 17:34:43.590456+00	7	2
9	2016-12-21 17:36:26.537387+00	7	2
10	2016-12-21 17:39:42.524539+00	1	3
11	2016-12-21 17:40:21.63244+00	4	1
12	2016-12-21 17:40:46.803923+00	10	2
13	2016-12-21 17:41:35.454754+00	4	1
14	2016-12-21 17:41:39.542086+00	10	4
15	2016-12-21 17:42:05.5559+00	12	2
16	2016-12-21 17:42:18.034513+00	12	2
17	2016-12-21 17:42:23.971412+00	1	3
18	2016-12-21 17:42:35.339297+00	1	3
19	2016-12-21 17:43:29.222245+00	11	2
20	2016-12-21 17:43:43.324999+00	11	2
21	2016-12-21 17:44:28.086922+00	8	2
22	2016-12-21 17:44:29.203758+00	9	3
23	2016-12-21 17:44:35.2328+00	13	1
24	2016-12-21 17:44:40.292954+00	5	3
25	2016-12-21 17:45:21.334323+00	10	4
26	2016-12-21 17:45:34.233739+00	10	4
27	2016-12-21 17:45:40.796193+00	8	2
28	2016-12-21 17:45:53.541278+00	10	2
29	2016-12-21 17:46:04.745038+00	10	2
30	2016-12-21 17:46:13.33958+00	4	3
31	2016-12-21 17:46:17.243668+00	10	4
32	2016-12-21 17:46:36.395592+00	2	3
33	2016-12-21 17:46:54.212389+00	1	4
34	2016-12-21 17:46:56.181515+00	13	2
35	2016-12-21 17:47:17.242624+00	13	2
36	2016-12-21 17:47:35.733021+00	2	3
37	2016-12-21 17:47:41.490659+00	2	3
38	2016-12-21 17:48:18.745975+00	2	3
39	2016-12-21 17:48:39.889711+00	1	4
40	2016-12-21 17:48:48.080692+00	1	4
41	2016-12-21 17:48:55.371738+00	1	4
42	2016-12-21 17:48:55.411875+00	2	3
43	2016-12-21 17:49:16.853989+00	1	4
44	2016-12-21 17:49:38.728191+00	2	3
45	2016-12-21 17:49:57.403292+00	1	4
46	2016-12-21 17:50:08.467649+00	2	3
47	2016-12-21 17:50:45.259002+00	15	5
48	2016-12-21 17:50:58.78454+00	1	4
49	2016-12-21 17:51:12.397913+00	9	2
50	2016-12-21 17:51:29.215855+00	1	4
51	2016-12-21 17:51:55.158583+00	15	5
52	2016-12-21 17:51:59.468655+00	15	5
53	2016-12-21 17:52:02.3288+00	9	2
54	2016-12-21 17:52:05.308507+00	15	5
55	2016-12-21 17:52:07.068245+00	10	4
56	2016-12-21 17:52:09.884442+00	15	5
57	2016-12-21 17:52:31.740777+00	5	2
58	2016-12-21 17:53:01.550983+00	15	5
59	2016-12-21 17:53:01.598026+00	2	3
60	2016-12-21 17:53:11.032603+00	10	4
61	2016-12-21 17:53:26.626856+00	2	3
62	2016-12-21 17:53:41.344889+00	12	4
63	2016-12-21 17:53:59.63076+00	6	3
64	2016-12-21 17:54:29.003576+00	6	3
65	2016-12-21 17:54:43.356744+00	2	4
66	2016-12-21 17:54:43.380937+00	2	2
67	2016-12-21 17:54:52.726347+00	2	3
68	2016-12-21 17:55:08.09023+00	16	2
69	2016-12-21 17:55:20.443387+00	12	3
70	2016-12-21 17:55:42.421604+00	15	5
71	2016-12-21 17:55:47.112246+00	16	2
72	2016-12-21 17:56:57.283037+00	14	2
73	2016-12-21 17:57:13.256272+00	3	3
74	2016-12-21 17:57:15.726045+00	2	4
75	2016-12-21 17:57:51.517858+00	15	4
76	2016-12-21 17:58:14.090666+00	3	3
77	2016-12-21 17:58:19.927993+00	3	3
78	2016-12-21 17:58:27.817652+00	8	1
79	2016-12-21 17:58:35.529276+00	8	1
80	2016-12-21 17:58:39.360988+00	8	1
81	2016-12-21 17:58:42.304036+00	8	1
82	2016-12-21 17:58:45.708524+00	8	1
83	2016-12-21 17:58:49.19604+00	8	1
84	2016-12-21 17:59:14.792209+00	17	2
85	2016-12-21 17:59:26.030625+00	17	2
86	2016-12-21 17:59:38.207629+00	10	2
87	2016-12-21 18:00:12.831734+00	2	1
88	2016-12-21 18:00:23.655145+00	2	1
89	2016-12-21 18:00:25.97864+00	10	2
90	2016-12-21 18:00:29.843504+00	2	1
91	2016-12-21 18:00:34.821819+00	2	1
92	2016-12-21 18:00:47.856641+00	1	2
93	2016-12-21 18:01:09.804916+00	15	4
94	2016-12-21 18:01:18.099471+00	3	2
95	2016-12-21 18:01:39.084242+00	16	4
96	2016-12-21 18:02:03.532767+00	3	2
97	2016-12-21 18:02:10.501671+00	3	2
98	2016-12-21 18:02:27.38277+00	18	2
99	2016-12-21 18:02:37.150327+00	16	4
100	2016-12-21 18:02:59.815258+00	3	4
101	2016-12-21 18:03:13.793653+00	3	4
102	2016-12-21 18:03:13.888507+00	18	2
103	2016-12-21 18:03:34.213787+00	6	4
104	2016-12-21 18:03:56.789493+00	15	2
105	2016-12-21 18:04:51.859365+00	15	2
106	2016-12-21 18:05:23.245068+00	6	4
107	2016-12-21 18:05:42.997534+00	19	2
108	2016-12-21 18:06:00.925502+00	6	4
109	2016-12-21 18:06:25.656299+00	20	3
110	2016-12-21 18:06:37.067029+00	17	4
111	2016-12-21 18:07:16.281096+00	19	2
112	2016-12-21 18:07:42.923914+00	20	2
113	2016-12-21 18:07:56.040021+00	20	3
114	2016-12-21 18:08:00.028977+00	16	2
115	2016-12-21 18:08:17.138883+00	17	4
116	2016-12-21 18:08:28.826329+00	16	2
117	2016-12-21 18:08:44.284932+00	7	5
118	2016-12-21 18:08:48.414259+00	6	2
119	2016-12-21 18:08:49.46612+00	18	4
120	2016-12-21 18:08:52.922307+00	7	3
121	2016-12-21 18:09:44.99539+00	18	2
122	2016-12-21 18:09:52.234326+00	18	4
123	2016-12-21 18:10:18.191353+00	7	3
124	2016-12-21 18:10:27.601093+00	7	3
125	2016-12-21 18:10:49.565083+00	21	4
126	2016-12-21 18:10:58.590105+00	21	2
127	2016-12-21 18:11:35.362854+00	21	4
128	2016-12-21 18:11:44.686044+00	7	5
129	2016-12-21 18:12:02.369478+00	9	4
130	2016-12-21 18:12:48.463803+00	21	2
131	2016-12-21 18:12:58.902233+00	9	4
132	2016-12-21 18:13:10.100662+00	15	5
133	2016-12-21 18:13:28.203733+00	7	4
134	2016-12-21 18:14:00.784335+00	7	2
135	2016-12-21 18:14:39.490407+00	22	3
136	2016-12-21 18:15:10.814363+00	22	3
137	2016-12-21 18:15:15.954092+00	22	1
138	2016-12-21 18:15:32.340707+00	14	4
139	2016-12-21 18:15:40.513136+00	22	3
140	2016-12-21 18:15:49.648414+00	22	3
141	2016-12-21 18:16:11.469961+00	22	1
142	2016-12-21 18:16:24.573429+00	23	2
143	2016-12-21 18:17:22.033301+00	13	3
144	2016-12-21 18:17:31.189358+00	23	2
145	2016-12-21 18:17:36.744303+00	14	4
146	2016-12-21 18:17:54.428769+00	13	3
147	2016-12-21 18:18:12.230225+00	23	4
148	2016-12-21 18:18:29.41918+00	13	3
149	2016-12-21 18:19:03.695655+00	24	5
150	2016-12-21 18:19:09.201407+00	23	3
151	2016-12-21 18:19:20.090239+00	23	4
152	2016-12-21 18:19:21.61286+00	22	2
153	2016-12-21 18:19:26.639796+00	23	3
154	2016-12-21 18:19:42.570626+00	22	2
155	2016-12-21 18:20:03.717785+00	24	5
156	2016-12-21 18:20:03.948479+00	17	3
157	2016-12-21 18:20:24.280365+00	24	2
158	2016-12-21 18:20:53.486496+00	22	1
159	2016-12-21 18:21:08.037617+00	23	4
160	2016-12-21 18:21:23.507655+00	21	3
161	2016-12-21 18:21:32.211792+00	24	5
162	2016-12-21 18:21:44.5727+00	24	2
163	2016-12-21 18:21:50.56342+00	22	1
164	2016-12-21 18:22:01.625396+00	24	5
165	2016-12-21 18:22:33.21509+00	24	5
166	2016-12-21 18:22:43.83307+00	24	5
167	2016-12-21 18:22:51.124297+00	24	2
168	2016-12-21 18:23:19.160164+00	24	2
169	2016-12-21 18:23:58.151057+00	25	2
170	2016-12-21 18:24:02.581527+00	25	4
171	2016-12-21 18:24:52.078839+00	26	3
172	2016-12-21 18:24:55.703701+00	25	2
173	2016-12-21 18:25:05.421424+00	25	4
174	2016-12-21 18:25:23.061979+00	26	3
175	2016-12-21 18:25:25.493484+00	26	2
176	2016-12-21 18:25:39.110611+00	26	3
177	2016-12-21 18:25:44.127811+00	26	3
178	2016-12-21 18:25:52.409512+00	25	3
179	2016-12-21 18:26:08.781986+00	26	3
180	2016-12-21 18:26:20.642327+00	12	3
181	2016-12-21 18:26:25.956454+00	26	2
182	2016-12-21 18:26:48.086662+00	26	2
183	2016-12-21 18:27:06.492938+00	12	3
184	2016-12-21 18:27:07.39324+00	27	5
185	2016-12-21 18:27:21.757494+00	27	1
186	2016-12-21 18:27:22.824807+00	26	3
187	2016-12-21 18:27:34.494981+00	27	5
188	2016-12-21 18:27:34.664461+00	26	3
189	2016-12-21 18:27:51.820138+00	26	3
190	2016-12-21 18:27:57.247026+00	26	3
191	2016-12-21 18:28:20.745198+00	27	5
192	2016-12-21 18:28:27.406349+00	27	5
193	2016-12-21 18:28:45.239067+00	27	5
194	2016-12-21 18:29:21.335974+00	27	2
195	2016-12-21 18:29:32.180782+00	27	2
196	2016-12-21 18:29:39.015694+00	29	3
197	2016-12-21 18:29:54.960011+00	29	2
198	2016-12-21 18:30:11.668417+00	6	4
199	2016-12-21 18:30:15.485988+00	29	3
200	2016-12-21 18:30:29.65124+00	29	3
201	2016-12-21 18:31:08.547341+00	29	2
202	2016-12-21 18:31:41.205654+00	30	2
203	2016-12-21 18:31:52.776083+00	30	5
204	2016-12-21 18:31:57.877903+00	30	2
205	2016-12-21 18:32:08.763894+00	30	3
206	2016-12-21 18:32:24.656787+00	23	1
207	2016-12-21 18:32:25.584504+00	30	3
208	2016-12-21 18:32:38.025676+00	30	3
209	2016-12-21 18:32:41.30427+00	28	2
210	2016-12-21 18:32:54.310354+00	23	1
211	2016-12-21 18:33:09.636374+00	29	3
212	2016-12-21 18:33:29.861238+00	28	2
213	2016-12-21 18:33:31.365063+00	28	3
214	2016-12-21 18:33:42.064419+00	23	1
215	2016-12-21 18:34:01.514899+00	27	3
216	2016-12-21 18:34:02.096718+00	23	2
217	2016-12-21 18:34:23.085157+00	27	3
218	2016-12-21 18:34:25.523993+00	23	2
219	2016-12-21 18:34:46.454443+00	13	2
220	2016-12-21 18:34:49.658498+00	27	3
221	2016-12-21 18:34:56.702047+00	13	2
222	2016-12-21 18:35:13.860802+00	26	3
223	2016-12-21 18:35:34.533399+00	23	3
224	2016-12-21 18:35:36.169241+00	23	2
225	2016-12-21 18:36:08.983785+00	30	5
226	2016-12-21 18:36:17.508596+00	27	3
227	2016-12-21 18:36:28.305472+00	28	3
228	2016-12-21 18:37:00.726791+00	28	3
229	2016-12-21 18:37:12.424613+00	24	2
230	2016-12-21 18:38:09.901488+00	16	4
231	2016-12-21 18:38:47.776626+00	26	5
232	2016-12-21 18:38:51.980581+00	16	4
233	2016-12-21 18:39:02.969732+00	24	2
234	2016-12-21 18:39:09.209856+00	26	5
235	2016-12-21 18:39:58.469855+00	1	2
236	2016-12-21 18:40:11.097459+00	21	3
237	2016-12-21 18:40:18.915414+00	20	3
238	2016-12-21 18:40:29.014208+00	19	3
239	2016-12-21 18:40:38.32753+00	24	5
240	2016-12-21 18:40:39.122574+00	18	3
241	2016-12-21 18:40:41.296681+00	15	4
242	2016-12-21 18:40:49.110869+00	17	3
243	2016-12-21 18:40:50.350251+00	24	5
244	2016-12-21 18:41:16.244865+00	16	3
245	2016-12-21 18:41:27.749569+00	1	2
246	2016-12-21 18:41:35.911635+00	15	3
247	2016-12-21 18:41:44.55809+00	1	3
248	2016-12-21 18:41:50.654166+00	24	5
249	2016-12-21 18:41:53.12174+00	2	3
250	2016-12-21 18:41:59.362919+00	15	4
251	2016-12-21 18:42:01.734288+00	3	3
252	2016-12-21 18:42:08.15004+00	4	3
253	2016-12-21 18:42:11.134374+00	1	2
254	2016-12-21 18:42:15.704961+00	5	3
255	2016-12-21 18:42:51.12665+00	15	4
256	2016-12-21 18:43:17.151132+00	5	3
257	2016-12-21 18:43:19.018344+00	7	2
258	2016-12-21 18:43:34.158931+00	15	4
259	2016-12-21 18:43:54.413279+00	15	4
260	2016-12-21 18:44:30.506923+00	5	3
261	2016-12-21 18:44:38.271906+00	15	4
262	2016-12-21 18:44:38.371774+00	6	3
263	2016-12-21 18:44:57.588873+00	5	3
264	2016-12-21 18:45:07.291878+00	8	3
265	2016-12-21 18:45:19.595607+00	7	2
266	2016-12-21 18:45:31.584819+00	9	3
267	2016-12-21 18:45:41.990143+00	8	3
268	2016-12-21 18:45:48.726951+00	10	3
269	2016-12-21 18:45:50.463385+00	14	2
270	2016-12-21 18:45:56.074157+00	8	5
271	2016-12-21 18:45:56.656731+00	8	4
272	2016-12-21 18:46:14.568661+00	10	3
273	2016-12-21 18:46:18.568799+00	10	3
274	2016-12-21 18:46:18.604887+00	14	2
275	2016-12-21 18:46:26.09893+00	8	4
276	2016-12-21 18:46:26.616734+00	11	3
277	2016-12-21 18:46:48.426182+00	6	2
278	2016-12-21 18:46:52.365962+00	8	5
279	2016-12-21 18:47:13.778662+00	26	4
280	2016-12-21 18:47:17.387007+00	1	4
281	2016-12-21 18:47:21.200216+00	11	3
282	2016-12-21 18:47:21.827656+00	2	4
283	2016-12-21 18:47:28.151841+00	20	4
284	2016-12-21 18:47:30.988819+00	11	3
285	2016-12-21 18:47:33.00014+00	21	4
286	2016-12-21 18:47:37.139231+00	22	4
287	2016-12-21 18:47:38.933197+00	12	3
288	2016-12-21 18:47:41.429574+00	23	4
289	2016-12-21 18:47:45.395084+00	13	3
290	2016-12-21 18:47:45.993285+00	24	4
291	2016-12-21 18:47:49.626528+00	25	4
292	2016-12-21 18:47:52.26639+00	14	3
293	2016-12-21 18:47:53.97209+00	26	4
294	2016-12-21 18:47:57.800549+00	27	4
295	2016-12-21 18:47:59.616757+00	15	3
296	2016-12-21 18:48:03.114952+00	28	4
297	2016-12-21 18:48:05.804949+00	16	3
298	2016-12-21 18:48:09.781819+00	29	4
299	2016-12-21 18:48:15.727329+00	30	4
300	2016-12-21 18:48:22.747552+00	17	3
301	2016-12-21 18:48:27.83627+00	6	2
302	2016-12-21 18:48:27.890985+00	18	3
303	2016-12-21 18:48:27.979732+00	19	4
304	2016-12-21 18:48:47.644347+00	18	3
305	2016-12-21 18:48:58.628601+00	13	3
306	2016-12-21 18:49:46.263027+00	25	3
307	2016-12-21 18:49:46.952419+00	6	2
308	2016-12-21 18:51:02.257298+00	9	4
309	2016-12-21 18:51:07.45634+00	6	2
310	2016-12-21 18:51:37.355474+00	22	3
311	2016-12-21 18:51:49.385343+00	16	2
312	2016-12-21 18:53:18.624131+00	30	3
313	2016-12-21 18:54:15.571504+00	21	2
\.


--
-- Name: cocomapapp_visit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('cocomapapp_visit_id_seq', 313, true);


--
-- Data for Name: cocomapapp_vote; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY cocomapapp_vote (id, is_positive, post_id, user_id) FROM stdin;
1	f	2	1
2	t	1	1
3	f	6	1
5	t	8	3
6	f	8	4
7	t	12	4
8	t	14	4
9	f	7	4
10	t	15	2
11	f	15	4
12	f	14	2
13	t	8	2
14	t	12	2
15	t	17	4
16	t	24	4
17	t	17	2
18	f	22	2
19	f	31	2
20	t	24	2
21	t	28	2
22	t	29	2
23	t	35	2
24	f	39	1
25	f	41	1
26	t	40	1
27	t	40	2
28	f	41	2
29	f	39	2
30	t	49	5
31	t	57	2
32	t	28	4
33	t	64	2
34	t	64	3
35	t	64	1
36	t	69	3
37	t	2	3
38	t	28	3
\.


--
-- Name: cocomapapp_vote_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('cocomapapp_vote_id_seq', 38, true);


--
-- Data for Name: django_admin_log; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY django_admin_log (id, action_time, object_id, object_repr, action_flag, change_message, content_type_id, user_id) FROM stdin;
\.


--
-- Name: django_admin_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('django_admin_log_id_seq', 1, false);


--
-- Data for Name: django_content_type; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY django_content_type (id, app_label, model) FROM stdin;
1	cocomapapp	tag
2	cocomapapp	post
3	cocomapapp	relation
4	cocomapapp	vote
5	cocomapapp	topic
6	cocomapapp	visit
7	admin	logentry
8	auth	group
9	auth	permission
10	auth	user
11	contenttypes	contenttype
12	sessions	session
13	sites	site
14	account	emailconfirmation
15	account	emailaddress
16	socialaccount	socialaccount
17	socialaccount	socialtoken
18	socialaccount	socialapp
\.


--
-- Name: django_content_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('django_content_type_id_seq', 18, true);


--
-- Data for Name: django_migrations; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY django_migrations (id, app, name, applied) FROM stdin;
1	contenttypes	0001_initial	2016-12-21 16:46:12.171828+00
2	auth	0001_initial	2016-12-21 16:46:12.301342+00
3	account	0001_initial	2016-12-21 16:46:12.342261+00
4	account	0002_email_max_length	2016-12-21 16:46:12.367655+00
5	admin	0001_initial	2016-12-21 16:46:12.392557+00
6	admin	0002_logentry_remove_auto_add	2016-12-21 16:46:12.405708+00
7	contenttypes	0002_remove_content_type_name	2016-12-21 16:46:12.438607+00
8	auth	0002_alter_permission_name_max_length	2016-12-21 16:46:12.452943+00
9	auth	0003_alter_user_email_max_length	2016-12-21 16:46:12.475978+00
10	auth	0004_alter_user_username_opts	2016-12-21 16:46:12.487536+00
11	auth	0005_alter_user_last_login_null	2016-12-21 16:46:12.501766+00
12	auth	0006_require_contenttypes_0002	2016-12-21 16:46:12.503713+00
13	auth	0007_alter_validators_add_error_messages	2016-12-21 16:46:12.51562+00
14	auth	0008_alter_user_username_max_length	2016-12-21 16:46:12.531863+00
15	cocomapapp	0001_initial	2016-12-21 16:46:12.695453+00
16	cocomapapp	0002_auto_20161126_1528	2016-12-21 16:46:12.708916+00
17	cocomapapp	0003_auto_20161127_0719	2016-12-21 16:46:12.779532+00
18	cocomapapp	0004_auto_20161203_1329	2016-12-21 16:46:12.838655+00
19	cocomapapp	0005_auto_20161203_2224	2016-12-21 16:46:12.893541+00
20	cocomapapp	0006_tag_hidden	2016-12-21 16:46:12.93301+00
21	cocomapapp	0007_auto_20161207_2333	2016-12-21 16:46:12.951442+00
22	cocomapapp	0008_auto_20161208_1038	2016-12-21 16:46:12.987421+00
23	cocomapapp	0009_auto_20161209_1034	2016-12-21 16:46:13.067835+00
24	cocomapapp	0010_visit	2016-12-21 16:46:13.097779+00
25	cocomapapp	0011_auto_20161221_1645	2016-12-21 16:46:13.117692+00
26	sessions	0001_initial	2016-12-21 16:46:13.131703+00
27	sites	0001_initial	2016-12-21 16:46:13.151646+00
28	sites	0002_alter_domain_unique	2016-12-21 16:46:13.160601+00
29	socialaccount	0001_initial	2016-12-21 16:46:13.315319+00
30	socialaccount	0002_token_max_lengths	2016-12-21 16:46:13.418587+00
31	socialaccount	0003_extra_data_default_dict	2016-12-21 16:46:13.443477+00
\.


--
-- Name: django_migrations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('django_migrations_id_seq', 31, true);


--
-- Data for Name: django_session; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY django_session (session_key, session_data, expire_date) FROM stdin;
668nqy5pazib1wckplpjymmwuzdmjhho	YmQ4MmJjOTUyNDhiZDExZjA0MDU0MmQzYzdmMDUwZjg0Y2ZmMTJhZDp7ImFjY291bnRfdmVyaWZpZWRfZW1haWwiOm51bGwsIl9hdXRoX3VzZXJfaGFzaCI6ImNkODRiMzIwOWYwNjgwNGMyZTgyMDdjZmI3MThlY2VmOWUwZjRhYjAiLCJhY2NvdW50X3VzZXIiOiIxIiwiX2F1dGhfdXNlcl9pZCI6IjEiLCJfYXV0aF91c2VyX2JhY2tlbmQiOiJhbGxhdXRoLmFjY291bnQuYXV0aF9iYWNrZW5kcy5BdXRoZW50aWNhdGlvbkJhY2tlbmQifQ==	2017-01-04 16:47:14.196087+00
8788eee91pijh7ow1zdhjddmdppcc1ha	MzE3M2RhZTQ4NTc2ZWU1M2NhODY3NjU3ZmUyZDFmZjJiMTQ1NTdlYTp7ImFjY291bnRfdmVyaWZpZWRfZW1haWwiOm51bGwsIl9hdXRoX3VzZXJfaGFzaCI6ImZhNzE5NDNiOTljZGI5OGE4YmNmZmIzN2ZiYmE1MGY0ODMwNjI5YmEiLCJhY2NvdW50X3VzZXIiOiIzIiwiX2F1dGhfdXNlcl9pZCI6IjMiLCJfYXV0aF91c2VyX2JhY2tlbmQiOiJhbGxhdXRoLmFjY291bnQuYXV0aF9iYWNrZW5kcy5BdXRoZW50aWNhdGlvbkJhY2tlbmQifQ==	2017-01-04 17:24:11.854057+00
i5p19k12ve6hvevcikgu1sncipxm08ch	NDZjMjUyMTRlZDdmNzhmMGU4Y2U1NTgwYWQzYmVhZmRkOWQ0OTYxYjp7ImFjY291bnRfdmVyaWZpZWRfZW1haWwiOm51bGwsIl9hdXRoX3VzZXJfaGFzaCI6IjBlM2U2ZWJjODRkNGRlNGQyNzQ5YjVkODFmZjEyMTM1MmVkZmFjYjciLCJhY2NvdW50X3VzZXIiOiI0IiwiX2F1dGhfdXNlcl9pZCI6IjQiLCJfYXV0aF91c2VyX2JhY2tlbmQiOiJhbGxhdXRoLmFjY291bnQuYXV0aF9iYWNrZW5kcy5BdXRoZW50aWNhdGlvbkJhY2tlbmQifQ==	2017-01-04 17:26:30.545245+00
7bgfvi6qmosuk7u79ij81afkl9s7928q	Y2M1NTNjMTcyNDQyNzllYTE3ZWViZDQ5ZDhjMjNmYzM2YWU0ZWU1Yjp7Il9hdXRoX3VzZXJfaGFzaCI6IjBlM2U2ZWJjODRkNGRlNGQyNzQ5YjVkODFmZjEyMTM1MmVkZmFjYjciLCJfc2Vzc2lvbl9leHBpcnkiOjAsIl9hdXRoX3VzZXJfaWQiOiI0IiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQifQ==	2017-01-04 17:41:08.699892+00
p5cgdb28n9rr0vgqjk9y9j6tyxkzkblt	ZjU0NGRlZmQ0OTA0YjQ1Y2E0ZjNmOTc5NTZkYjEwYzIwZDI5MDljZTp7ImFjY291bnRfdmVyaWZpZWRfZW1haWwiOm51bGwsIl9hdXRoX3VzZXJfaGFzaCI6ImQ3NDAxYzU5Nzk5NTVjYzAzNjM0ZjQ2Y2YxZjg3ZDMzYTIyZGNmZTMiLCJhY2NvdW50X3VzZXIiOiI1IiwiX2F1dGhfdXNlcl9pZCI6IjUiLCJfYXV0aF91c2VyX2JhY2tlbmQiOiJhbGxhdXRoLmFjY291bnQuYXV0aF9iYWNrZW5kcy5BdXRoZW50aWNhdGlvbkJhY2tlbmQifQ==	2017-01-04 17:46:02.547594+00
8at3xg7tsf34tizp4ipl18sl2uhwy2gv	ODU5MmFjNDljNzFlNmU4YTQ2NjFmOTM1NDc5ZmM1NmE2N2QzOTQ3Mjp7Il9hdXRoX3VzZXJfaGFzaCI6IjBlM2U2ZWJjODRkNGRlNGQyNzQ5YjVkODFmZjEyMTM1MmVkZmFjYjciLCJfc2Vzc2lvbl9leHBpcnkiOjEyMDk2MDAsIl9hdXRoX3VzZXJfaWQiOiI0IiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQifQ==	2017-01-04 17:50:44.034075+00
o28dq9y4wpfdiiz0xwqieu1xh9l4hchn	NTI0M2M5MjdhMTgzOGYzOWNjMjg5ZWEyNGM3ZmU3ZTQzMWU0NjgzODp7Il9hdXRoX3VzZXJfaGFzaCI6ImZhNzE5NDNiOTljZGI5OGE4YmNmZmIzN2ZiYmE1MGY0ODMwNjI5YmEiLCJfc2Vzc2lvbl9leHBpcnkiOjAsIl9hdXRoX3VzZXJfaWQiOiIzIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQifQ==	2017-01-04 17:51:05.818633+00
rehve2hl03nz84z9bjhdc9v72bgnccxw	MTM0OGUyM2U2Mzg5YjY2ZTRmYjQ1Y2Q2NWQzNjBiMWY1YWJhMTkyNjp7Il9hdXRoX3VzZXJfaGFzaCI6ImQ3NDAxYzU5Nzk5NTVjYzAzNjM0ZjQ2Y2YxZjg3ZDMzYTIyZGNmZTMiLCJfc2Vzc2lvbl9leHBpcnkiOjAsIl9hdXRoX3VzZXJfaWQiOiI1IiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQifQ==	2017-01-04 17:52:38.994859+00
rpp1hczxfm6alox0j0gy1vqwoz5dn2kn	N2Y4ZjI1NWE3YjI5YzM5ZmU1ODIyZDg2MTZjZmE0YzM0NjgzNmY0Mzp7Il9hdXRoX3VzZXJfaGFzaCI6IjQwNWJlYTFkZjIwMDIwNzAzMjVkNmZkZmQyZWIyNjllMGNjZDJlNzYiLCJfc2Vzc2lvbl9leHBpcnkiOjAsIl9hdXRoX3VzZXJfaWQiOiIyIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQifQ==	2017-01-04 18:35:59.628988+00
\.


--
-- Data for Name: django_site; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY django_site (id, domain, name) FROM stdin;
1	example.com	example.com
\.


--
-- Name: django_site_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('django_site_id_seq', 1, true);


--
-- Data for Name: socialaccount_socialaccount; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY socialaccount_socialaccount (id, provider, uid, last_login, date_joined, extra_data, user_id) FROM stdin;
\.


--
-- Name: socialaccount_socialaccount_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('socialaccount_socialaccount_id_seq', 1, false);


--
-- Data for Name: socialaccount_socialapp; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY socialaccount_socialapp (id, provider, name, client_id, secret, key) FROM stdin;
\.


--
-- Name: socialaccount_socialapp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('socialaccount_socialapp_id_seq', 1, false);


--
-- Data for Name: socialaccount_socialapp_sites; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY socialaccount_socialapp_sites (id, socialapp_id, site_id) FROM stdin;
\.


--
-- Name: socialaccount_socialapp_sites_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('socialaccount_socialapp_sites_id_seq', 1, false);


--
-- Data for Name: socialaccount_socialtoken; Type: TABLE DATA; Schema: public; Owner: cocouser
--

COPY socialaccount_socialtoken (id, token, token_secret, expires_at, account_id, app_id) FROM stdin;
\.


--
-- Name: socialaccount_socialtoken_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cocouser
--

SELECT pg_catalog.setval('socialaccount_socialtoken_id_seq', 1, false);


--
-- Name: account_emailaddress_email_key; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY account_emailaddress
    ADD CONSTRAINT account_emailaddress_email_key UNIQUE (email);


--
-- Name: account_emailaddress_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY account_emailaddress
    ADD CONSTRAINT account_emailaddress_pkey PRIMARY KEY (id);


--
-- Name: account_emailconfirmation_key_key; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY account_emailconfirmation
    ADD CONSTRAINT account_emailconfirmation_key_key UNIQUE (key);


--
-- Name: account_emailconfirmation_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY account_emailconfirmation
    ADD CONSTRAINT account_emailconfirmation_pkey PRIMARY KEY (id);


--
-- Name: auth_group_name_key; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY auth_group
    ADD CONSTRAINT auth_group_name_key UNIQUE (name);


--
-- Name: auth_group_permissions_group_id_0cd325b0_uniq; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_group_id_0cd325b0_uniq UNIQUE (group_id, permission_id);


--
-- Name: auth_group_permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_pkey PRIMARY KEY (id);


--
-- Name: auth_group_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY auth_group
    ADD CONSTRAINT auth_group_pkey PRIMARY KEY (id);


--
-- Name: auth_permission_content_type_id_01ab375a_uniq; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY auth_permission
    ADD CONSTRAINT auth_permission_content_type_id_01ab375a_uniq UNIQUE (content_type_id, codename);


--
-- Name: auth_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY auth_permission
    ADD CONSTRAINT auth_permission_pkey PRIMARY KEY (id);


--
-- Name: auth_user_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY auth_user_groups
    ADD CONSTRAINT auth_user_groups_pkey PRIMARY KEY (id);


--
-- Name: auth_user_groups_user_id_94350c0c_uniq; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY auth_user_groups
    ADD CONSTRAINT auth_user_groups_user_id_94350c0c_uniq UNIQUE (user_id, group_id);


--
-- Name: auth_user_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY auth_user
    ADD CONSTRAINT auth_user_pkey PRIMARY KEY (id);


--
-- Name: auth_user_user_permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_pkey PRIMARY KEY (id);


--
-- Name: auth_user_user_permissions_user_id_14a6b632_uniq; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_user_id_14a6b632_uniq UNIQUE (user_id, permission_id);


--
-- Name: auth_user_username_key; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY auth_user
    ADD CONSTRAINT auth_user_username_key UNIQUE (username);


--
-- Name: cocomapapp_post_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY cocomapapp_post
    ADD CONSTRAINT cocomapapp_post_pkey PRIMARY KEY (id);


--
-- Name: cocomapapp_post_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY cocomapapp_post_tags
    ADD CONSTRAINT cocomapapp_post_tags_pkey PRIMARY KEY (id);


--
-- Name: cocomapapp_post_tags_post_id_e581e497_uniq; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY cocomapapp_post_tags
    ADD CONSTRAINT cocomapapp_post_tags_post_id_e581e497_uniq UNIQUE (post_id, tag_id);


--
-- Name: cocomapapp_relation_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY cocomapapp_relation
    ADD CONSTRAINT cocomapapp_relation_pkey PRIMARY KEY (id);


--
-- Name: cocomapapp_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY cocomapapp_tag
    ADD CONSTRAINT cocomapapp_tag_pkey PRIMARY KEY ("wikidataID");


--
-- Name: cocomapapp_topic_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY cocomapapp_topic
    ADD CONSTRAINT cocomapapp_topic_pkey PRIMARY KEY (id);


--
-- Name: cocomapapp_topic_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY cocomapapp_topic_tags
    ADD CONSTRAINT cocomapapp_topic_tags_pkey PRIMARY KEY (id);


--
-- Name: cocomapapp_topic_tags_topic_id_d42d7fa3_uniq; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY cocomapapp_topic_tags
    ADD CONSTRAINT cocomapapp_topic_tags_topic_id_d42d7fa3_uniq UNIQUE (topic_id, tag_id);


--
-- Name: cocomapapp_visit_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY cocomapapp_visit
    ADD CONSTRAINT cocomapapp_visit_pkey PRIMARY KEY (id);


--
-- Name: cocomapapp_vote_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY cocomapapp_vote
    ADD CONSTRAINT cocomapapp_vote_pkey PRIMARY KEY (id);


--
-- Name: django_admin_log_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY django_admin_log
    ADD CONSTRAINT django_admin_log_pkey PRIMARY KEY (id);


--
-- Name: django_content_type_app_label_76bd3d3b_uniq; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY django_content_type
    ADD CONSTRAINT django_content_type_app_label_76bd3d3b_uniq UNIQUE (app_label, model);


--
-- Name: django_content_type_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY django_content_type
    ADD CONSTRAINT django_content_type_pkey PRIMARY KEY (id);


--
-- Name: django_migrations_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY django_migrations
    ADD CONSTRAINT django_migrations_pkey PRIMARY KEY (id);


--
-- Name: django_session_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY django_session
    ADD CONSTRAINT django_session_pkey PRIMARY KEY (session_key);


--
-- Name: django_site_domain_a2e37b91_uniq; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY django_site
    ADD CONSTRAINT django_site_domain_a2e37b91_uniq UNIQUE (domain);


--
-- Name: django_site_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY django_site
    ADD CONSTRAINT django_site_pkey PRIMARY KEY (id);


--
-- Name: socialaccount_socialaccount_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY socialaccount_socialaccount
    ADD CONSTRAINT socialaccount_socialaccount_pkey PRIMARY KEY (id);


--
-- Name: socialaccount_socialaccount_provider_fc810c6e_uniq; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY socialaccount_socialaccount
    ADD CONSTRAINT socialaccount_socialaccount_provider_fc810c6e_uniq UNIQUE (provider, uid);


--
-- Name: socialaccount_socialapp_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY socialaccount_socialapp
    ADD CONSTRAINT socialaccount_socialapp_pkey PRIMARY KEY (id);


--
-- Name: socialaccount_socialapp_sites_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY socialaccount_socialapp_sites
    ADD CONSTRAINT socialaccount_socialapp_sites_pkey PRIMARY KEY (id);


--
-- Name: socialaccount_socialapp_sites_socialapp_id_71a9a768_uniq; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY socialaccount_socialapp_sites
    ADD CONSTRAINT socialaccount_socialapp_sites_socialapp_id_71a9a768_uniq UNIQUE (socialapp_id, site_id);


--
-- Name: socialaccount_socialtoken_app_id_fca4e0ac_uniq; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY socialaccount_socialtoken
    ADD CONSTRAINT socialaccount_socialtoken_app_id_fca4e0ac_uniq UNIQUE (app_id, account_id);


--
-- Name: socialaccount_socialtoken_pkey; Type: CONSTRAINT; Schema: public; Owner: cocouser; Tablespace: 
--

ALTER TABLE ONLY socialaccount_socialtoken
    ADD CONSTRAINT socialaccount_socialtoken_pkey PRIMARY KEY (id);


--
-- Name: account_emailaddress_e8701ad4; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX account_emailaddress_e8701ad4 ON account_emailaddress USING btree (user_id);


--
-- Name: account_emailaddress_email_03be32b2_like; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX account_emailaddress_email_03be32b2_like ON account_emailaddress USING btree (email varchar_pattern_ops);


--
-- Name: account_emailconfirmation_6f1edeac; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX account_emailconfirmation_6f1edeac ON account_emailconfirmation USING btree (email_address_id);


--
-- Name: account_emailconfirmation_key_f43612bd_like; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX account_emailconfirmation_key_f43612bd_like ON account_emailconfirmation USING btree (key varchar_pattern_ops);


--
-- Name: auth_group_name_a6ea08ec_like; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX auth_group_name_a6ea08ec_like ON auth_group USING btree (name varchar_pattern_ops);


--
-- Name: auth_group_permissions_0e939a4f; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX auth_group_permissions_0e939a4f ON auth_group_permissions USING btree (group_id);


--
-- Name: auth_group_permissions_8373b171; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX auth_group_permissions_8373b171 ON auth_group_permissions USING btree (permission_id);


--
-- Name: auth_permission_417f1b1c; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX auth_permission_417f1b1c ON auth_permission USING btree (content_type_id);


--
-- Name: auth_user_groups_0e939a4f; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX auth_user_groups_0e939a4f ON auth_user_groups USING btree (group_id);


--
-- Name: auth_user_groups_e8701ad4; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX auth_user_groups_e8701ad4 ON auth_user_groups USING btree (user_id);


--
-- Name: auth_user_user_permissions_8373b171; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX auth_user_user_permissions_8373b171 ON auth_user_user_permissions USING btree (permission_id);


--
-- Name: auth_user_user_permissions_e8701ad4; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX auth_user_user_permissions_e8701ad4 ON auth_user_user_permissions USING btree (user_id);


--
-- Name: auth_user_username_6821ab7c_like; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX auth_user_username_6821ab7c_like ON auth_user USING btree (username varchar_pattern_ops);


--
-- Name: cocomapapp_post_19b4d727; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_post_19b4d727 ON cocomapapp_post USING btree (topic_id);


--
-- Name: cocomapapp_post_e8701ad4; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_post_e8701ad4 ON cocomapapp_post USING btree (user_id);


--
-- Name: cocomapapp_post_tags_76f094bc; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_post_tags_76f094bc ON cocomapapp_post_tags USING btree (tag_id);


--
-- Name: cocomapapp_post_tags_f3aa1999; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_post_tags_f3aa1999 ON cocomapapp_post_tags USING btree (post_id);


--
-- Name: cocomapapp_post_tags_tag_id_6eb564d7_like; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_post_tags_tag_id_6eb564d7_like ON cocomapapp_post_tags USING btree (tag_id text_pattern_ops);


--
-- Name: cocomapapp_relation_036d7e01; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_relation_036d7e01 ON cocomapapp_relation USING btree (topic_from_id);


--
-- Name: cocomapapp_relation_5ad9c3b4; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_relation_5ad9c3b4 ON cocomapapp_relation USING btree (topic_to_id);


--
-- Name: cocomapapp_tag_id_8c4b44a9_like; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_tag_id_8c4b44a9_like ON cocomapapp_tag USING btree ("wikidataID" text_pattern_ops);


--
-- Name: cocomapapp_topic_e8701ad4; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_topic_e8701ad4 ON cocomapapp_topic USING btree (user_id);


--
-- Name: cocomapapp_topic_tags_19b4d727; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_topic_tags_19b4d727 ON cocomapapp_topic_tags USING btree (topic_id);


--
-- Name: cocomapapp_topic_tags_76f094bc; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_topic_tags_76f094bc ON cocomapapp_topic_tags USING btree (tag_id);


--
-- Name: cocomapapp_topic_tags_tag_id_b33ac39e_like; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_topic_tags_tag_id_b33ac39e_like ON cocomapapp_topic_tags USING btree (tag_id text_pattern_ops);


--
-- Name: cocomapapp_visit_19b4d727; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_visit_19b4d727 ON cocomapapp_visit USING btree (topic_id);


--
-- Name: cocomapapp_visit_e8701ad4; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_visit_e8701ad4 ON cocomapapp_visit USING btree (user_id);


--
-- Name: cocomapapp_vote_e8701ad4; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_vote_e8701ad4 ON cocomapapp_vote USING btree (user_id);


--
-- Name: cocomapapp_vote_f3aa1999; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX cocomapapp_vote_f3aa1999 ON cocomapapp_vote USING btree (post_id);


--
-- Name: django_admin_log_417f1b1c; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX django_admin_log_417f1b1c ON django_admin_log USING btree (content_type_id);


--
-- Name: django_admin_log_e8701ad4; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX django_admin_log_e8701ad4 ON django_admin_log USING btree (user_id);


--
-- Name: django_session_de54fa62; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX django_session_de54fa62 ON django_session USING btree (expire_date);


--
-- Name: django_session_session_key_c0390e0f_like; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX django_session_session_key_c0390e0f_like ON django_session USING btree (session_key varchar_pattern_ops);


--
-- Name: django_site_domain_a2e37b91_like; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX django_site_domain_a2e37b91_like ON django_site USING btree (domain varchar_pattern_ops);


--
-- Name: socialaccount_socialaccount_e8701ad4; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX socialaccount_socialaccount_e8701ad4 ON socialaccount_socialaccount USING btree (user_id);


--
-- Name: socialaccount_socialapp_sites_9365d6e7; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX socialaccount_socialapp_sites_9365d6e7 ON socialaccount_socialapp_sites USING btree (site_id);


--
-- Name: socialaccount_socialapp_sites_fe95b0a0; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX socialaccount_socialapp_sites_fe95b0a0 ON socialaccount_socialapp_sites USING btree (socialapp_id);


--
-- Name: socialaccount_socialtoken_8a089c2a; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX socialaccount_socialtoken_8a089c2a ON socialaccount_socialtoken USING btree (account_id);


--
-- Name: socialaccount_socialtoken_f382adfe; Type: INDEX; Schema: public; Owner: cocouser; Tablespace: 
--

CREATE INDEX socialaccount_socialtoken_f382adfe ON socialaccount_socialtoken USING btree (app_id);


--
-- Name: account_em_email_address_id_5b7f8c58_fk_account_emailaddress_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY account_emailconfirmation
    ADD CONSTRAINT account_em_email_address_id_5b7f8c58_fk_account_emailaddress_id FOREIGN KEY (email_address_id) REFERENCES account_emailaddress(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: account_emailaddress_user_id_2c513194_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY account_emailaddress
    ADD CONSTRAINT account_emailaddress_user_id_2c513194_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_group_permiss_permission_id_84c5c92e_fk_auth_permission_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY auth_group_permissions
    ADD CONSTRAINT auth_group_permiss_permission_id_84c5c92e_fk_auth_permission_id FOREIGN KEY (permission_id) REFERENCES auth_permission(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_group_permissions_group_id_b120cbf9_fk_auth_group_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_group_id_b120cbf9_fk_auth_group_id FOREIGN KEY (group_id) REFERENCES auth_group(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_permiss_content_type_id_2f476e4b_fk_django_content_type_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY auth_permission
    ADD CONSTRAINT auth_permiss_content_type_id_2f476e4b_fk_django_content_type_id FOREIGN KEY (content_type_id) REFERENCES django_content_type(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_groups_group_id_97559544_fk_auth_group_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY auth_user_groups
    ADD CONSTRAINT auth_user_groups_group_id_97559544_fk_auth_group_id FOREIGN KEY (group_id) REFERENCES auth_group(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_groups_user_id_6a12ed8b_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY auth_user_groups
    ADD CONSTRAINT auth_user_groups_user_id_6a12ed8b_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_user_per_permission_id_1fbb5f2c_fk_auth_permission_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_per_permission_id_1fbb5f2c_fk_auth_permission_id FOREIGN KEY (permission_id) REFERENCES auth_permission(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: cocomapapp_post_ta_tag_id_6eb564d7_fk_cocomapapp_tag_wikidataID; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_post_tags
    ADD CONSTRAINT "cocomapapp_post_ta_tag_id_6eb564d7_fk_cocomapapp_tag_wikidataID" FOREIGN KEY (tag_id) REFERENCES cocomapapp_tag("wikidataID") DEFERRABLE INITIALLY DEFERRED;


--
-- Name: cocomapapp_post_tags_post_id_9edb027d_fk_cocomapapp_post_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_post_tags
    ADD CONSTRAINT cocomapapp_post_tags_post_id_9edb027d_fk_cocomapapp_post_id FOREIGN KEY (post_id) REFERENCES cocomapapp_post(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: cocomapapp_post_topic_id_2ff7bbbb_fk_cocomapapp_topic_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_post
    ADD CONSTRAINT cocomapapp_post_topic_id_2ff7bbbb_fk_cocomapapp_topic_id FOREIGN KEY (topic_id) REFERENCES cocomapapp_topic(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: cocomapapp_post_user_id_46e68555_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_post
    ADD CONSTRAINT cocomapapp_post_user_id_46e68555_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: cocomapapp_relati_topic_from_id_05bbea0c_fk_cocomapapp_topic_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_relation
    ADD CONSTRAINT cocomapapp_relati_topic_from_id_05bbea0c_fk_cocomapapp_topic_id FOREIGN KEY (topic_from_id) REFERENCES cocomapapp_topic(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: cocomapapp_relation_topic_to_id_52e4412f_fk_cocomapapp_topic_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_relation
    ADD CONSTRAINT cocomapapp_relation_topic_to_id_52e4412f_fk_cocomapapp_topic_id FOREIGN KEY (topic_to_id) REFERENCES cocomapapp_topic(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: cocomapapp_topic_t_tag_id_b33ac39e_fk_cocomapapp_tag_wikidataID; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_topic_tags
    ADD CONSTRAINT "cocomapapp_topic_t_tag_id_b33ac39e_fk_cocomapapp_tag_wikidataID" FOREIGN KEY (tag_id) REFERENCES cocomapapp_tag("wikidataID") DEFERRABLE INITIALLY DEFERRED;


--
-- Name: cocomapapp_topic_tags_topic_id_3a15ad25_fk_cocomapapp_topic_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_topic_tags
    ADD CONSTRAINT cocomapapp_topic_tags_topic_id_3a15ad25_fk_cocomapapp_topic_id FOREIGN KEY (topic_id) REFERENCES cocomapapp_topic(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: cocomapapp_topic_user_id_fb968474_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_topic
    ADD CONSTRAINT cocomapapp_topic_user_id_fb968474_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: cocomapapp_visit_topic_id_7478892c_fk_cocomapapp_topic_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_visit
    ADD CONSTRAINT cocomapapp_visit_topic_id_7478892c_fk_cocomapapp_topic_id FOREIGN KEY (topic_id) REFERENCES cocomapapp_topic(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: cocomapapp_visit_user_id_d07da301_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_visit
    ADD CONSTRAINT cocomapapp_visit_user_id_d07da301_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: cocomapapp_vote_post_id_b3312f2c_fk_cocomapapp_post_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_vote
    ADD CONSTRAINT cocomapapp_vote_post_id_b3312f2c_fk_cocomapapp_post_id FOREIGN KEY (post_id) REFERENCES cocomapapp_post(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: cocomapapp_vote_user_id_3517bab2_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY cocomapapp_vote
    ADD CONSTRAINT cocomapapp_vote_user_id_3517bab2_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: django_admin_content_type_id_c4bce8eb_fk_django_content_type_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY django_admin_log
    ADD CONSTRAINT django_admin_content_type_id_c4bce8eb_fk_django_content_type_id FOREIGN KEY (content_type_id) REFERENCES django_content_type(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: django_admin_log_user_id_c564eba6_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY django_admin_log
    ADD CONSTRAINT django_admin_log_user_id_c564eba6_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: socialacc_account_id_951f210e_fk_socialaccount_socialaccount_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY socialaccount_socialtoken
    ADD CONSTRAINT socialacc_account_id_951f210e_fk_socialaccount_socialaccount_id FOREIGN KEY (account_id) REFERENCES socialaccount_socialaccount(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: socialaccou_socialapp_id_97fb6e7d_fk_socialaccount_socialapp_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY socialaccount_socialapp_sites
    ADD CONSTRAINT socialaccou_socialapp_id_97fb6e7d_fk_socialaccount_socialapp_id FOREIGN KEY (socialapp_id) REFERENCES socialaccount_socialapp(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: socialaccount_soc_app_id_636a42d7_fk_socialaccount_socialapp_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY socialaccount_socialtoken
    ADD CONSTRAINT socialaccount_soc_app_id_636a42d7_fk_socialaccount_socialapp_id FOREIGN KEY (app_id) REFERENCES socialaccount_socialapp(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: socialaccount_socialaccount_user_id_8146e70c_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY socialaccount_socialaccount
    ADD CONSTRAINT socialaccount_socialaccount_user_id_8146e70c_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: socialaccount_socialapp_site_site_id_2579dee5_fk_django_site_id; Type: FK CONSTRAINT; Schema: public; Owner: cocouser
--

ALTER TABLE ONLY socialaccount_socialapp_sites
    ADD CONSTRAINT socialaccount_socialapp_site_site_id_2579dee5_fk_django_site_id FOREIGN KEY (site_id) REFERENCES django_site(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

