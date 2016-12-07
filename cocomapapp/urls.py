from django.conf.urls import url, include
from django.conf import settings
from django.conf.urls.static import static
from .views import (
	index,
	show_topic,
	add_topic,
	# login,
	# signup,
	add_post,
	TopicList,
	TopicCreate,
	TopicRetrieve,
	PostCreate,
	PostRetrieve,
	PostUpdate,
	RelationCreate,
	RelationRetrieve,
	RelationList,
	TagCreate,
	TagRetrieve,
	post_upvote,
	post_downvote,
	relation_upvote,
	relation_downvote,
	wikidata_search,
	topic_get_hot,
    wikidata_query,
	search_by_tags,
	search,
	RecommendedTopics,
	RecommendedPosts,
	add_relation,
)

urlpatterns = [
    url(r'^$', index),
    url(r'^accounts/', include('allauth.urls')),
    url(r'topics/(?P<id>\d+)/$', show_topic),
    url(r'topics/add', add_topic),
    url(r'topics/postAdd', add_post),

	url(r'topicList',  TopicList.as_view()),
	url(r'topicCreate',  TopicCreate.as_view()),
	url(r'topicRetrieve/(?P<pk>[0-9]+)/',  TopicRetrieve.as_view()),
	url(r'recommendedTopics',  RecommendedTopics.as_view()),

	url(r'postCreate', PostCreate.as_view()),
	url(r'postRetrieve/(?P<pk>[0-9]+)/',  PostRetrieve.as_view()),
	url(r'postUpdate/(?P<pk>[0-9]+)/',  PostUpdate.as_view()),
	url(r'recommendedPosts',  RecommendedPosts.as_view()),

	url(r'relationCreate',  RelationCreate.as_view()),
	url(r'relationRetrieve/(?P<pk>[0-9]+)/',  RelationRetrieve.as_view()),
	url(r'relationList',  RelationList.as_view()),

	url(r'tagCreate',  TagCreate.as_view()),
	url(r'tagRetrieve/(?P<pk>Q[0-9]+)/',  TagRetrieve.as_view()),

	url(r'postUpvote/(?P<pk>[0-9]+)/',  post_upvote),
	url(r'postDownvote/(?P<pk>[0-9]+)/',  post_downvote),

	url(r'relationUpvote/(?P<pk>[0-9]+)/',  relation_upvote),
	url(r'relationDownvote/(?P<pk>[0-9]+)/',  relation_downvote),

	url(r'wikidataSearch/(?P<str>.+)/', wikidata_search),
	url(r'getHotTopics/', topic_get_hot),
    url(r'wikidataQuery/(?P<str>.+)/', wikidata_query),
	url(r'searchByTags', search_by_tags),

	url(r'search', search),
	url(r'addRelation/(?P<id>[0-9]+)/', add_relation),
]
