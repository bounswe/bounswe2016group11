from django.conf.urls import url
from django.conf import settings
from django.conf.urls.static import static
from .views import (
	index,
	show_topic,
	add_topic,
	login,
	signup,
	add_post,
	TopicList,
	TopicCreate,
	TopicRetrieve,
	PostCreate,
	PostRetrieve,
	PostUpdate,
	RelationRetrieve,
	RelationList,
	post_upvote,
	post_downvote,
	wikidata_search,
	search,
)

urlpatterns = [
    url(r'^$', index),
	url(r'login', login, name='login'),
	url(r'signup', signup, name='signup'),
    url(r'topics/(?P<id>\d+)/$', show_topic),
    url(r'topics/add', add_topic),
    url(r'topics/postAdd', add_post),

	url(r'topicList',  TopicList.as_view()),
	url(r'topicCreate',  TopicCreate.as_view()),
	url(r'topicRetrieve/(?P<pk>[0-9]+)/',  TopicRetrieve.as_view()),

	url(r'postCreate', PostCreate.as_view()),
	url(r'postRetrieve/(?P<pk>[0-9]+)/',  PostRetrieve.as_view()),
	url(r'postUpdate/(?P<pk>[0-9]+)/',  PostUpdate.as_view()),

	url(r'relationRetrieve/(?P<pk>[0-9]+)/',  RelationRetrieve.as_view()),
	url(r'relationList',  RelationList.as_view()),

	url(r'postUpvote/(?P<pk>[0-9]+)/',  post_upvote),
	url(r'postDownvote/(?P<pk>[0-9]+)/',  post_downvote),

	url(r'wikidataSearch/(?P<str>.+)/', wikidata_search),

	url(r'search', search),
]
