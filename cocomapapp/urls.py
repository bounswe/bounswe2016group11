from django.conf.urls import url

from .views import (
	index,
	show_topic,
	add_topic,
	add_post
)

urlpatterns = [
    url(r'^$', index),
    url(r'topics/(?P<id>\d+)/$', show_topic),
    url(r'topics/add', add_topic),
    url(r'topics/postAdd', add_post),

]
