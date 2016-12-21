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
	update_post,
	PostDelete,
	RelationCreate,
	RelationRetrieve,
	RelationList,
	TagCreate,
	TagRetrieve,
	VisitCreate,
	#post_upvote,
	#post_downvote,
	post_vote,
	listTopicRelevance,
	getRecommendedTopics,
	getRecommendedPosts,
	relation_upvote,
	relation_downvote,
	wikidata_search,
	topic_get_hot,
	post_get_recent,
    wikidata_query,
	search_by_tags,
	search,
	RecommendedTopics,
	RecommendedPosts,
	add_relation,
    infocus
)

urlpatterns = [
    url(r'^$', index),
    url(r'^accounts/', include('allauth.urls')),
    url(r'topics/(?P<id>\d+)/$', show_topic),
    url(r'topics/add', add_topic),
    url(r'topics/(?P<id>\d+)/postAdd', add_post),

	url(r'topicList',  TopicList.as_view(), name='topicList'),		 					   #
	url(r'topicCreate',  TopicCreate.as_view(), name='topicCreate'),					   #
	url(r'topicRetrieve/(?P<pk>[0-9]+)/',  TopicRetrieve.as_view(), name='topicRetrieve'), #
	url(r'recommendedTopics',  RecommendedTopics.as_view()),

	url(r'postCreate', PostCreate.as_view(), name='postCreate'),							#M
	url(r'postRetrieve/(?P<pk>[0-9]+)/',  PostRetrieve.as_view(), name='postRetrieve'),		#M
	url(r'postUpdate/(?P<pk>[0-9]+)/',  update_post),		#
	url(r'postDelete/(?P<pk>[0-9]+)/',  PostDelete.as_view()),#
	url(r'recommendedPosts',  RecommendedPosts.as_view()),

	url(r'relationCreate',  RelationCreate.as_view(), name='relationCreate'),				#M
	url(r'relationRetrieve/(?P<pk>[0-9]+)/',  RelationRetrieve.as_view(), name='relationRetrieve'),					#O
	url(r'relationList',  RelationList.as_view(), name='relationList'),											#O

	url(r'tagCreate',  TagCreate.as_view()),
	url(r'tagRetrieve/(?P<pk>Q[0-9]+)/',  TagRetrieve.as_view()),

	url(r'visitCreate/',  VisitCreate.as_view()),#

	#url(r'postUpvote/(?P<pk>[0-9]+)/',  post_upvote),
	#url(r'postDownvote/(?P<pk>[0-9]+)/',  post_downvote),
	url(r'postVote/',  post_vote, name='postVote'),									#
	url(r'listTopicRelevance/', listTopicRelevance),
	url(r'getRecommendedTopics/(?P<limit>[0-9]+)/', getRecommendedTopics),
	url(r'getRecommendedPosts/(?P<limit>[0-9]+)/', getRecommendedPosts),

	url(r'relationUpvote/(?P<pk>[0-9]+)/',  relation_upvote),
	url(r'relationDownvote/(?P<pk>[0-9]+)/',  relation_downvote),

	url(r'wikidataSearch/(?P<str>.+)/', wikidata_search),
    url(r'wikidataQuery/(?P<str>.+)/', wikidata_query),
	url(r'searchByTags', search_by_tags),

	url(r'getHotTopics/(?P<limit>[0-9]+)/', topic_get_hot),
	url(r'getRecentPosts/(?P<limit>[0-9]+)/', post_get_recent),

	url(r'search', search),
	url(r'addRelation/(?P<id>[0-9]+)/', add_relation),

	url(r'infocus/(?P<id>\d+)/$', infocus),

	url(r'^accounts/', include('allauth.urls')),

]
