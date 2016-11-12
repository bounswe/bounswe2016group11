from django.conf.urls import url
from django.conf import settings
from django.conf.urls.static import static
from .views import (
	index,
	show_topic,
	add_topic,
	login,
	add_post
)

urlpatterns = [
    url(r'^$', index),
	url(r'login', login, name='login'),
    url(r'topics/(?P<id>\d+)/$', show_topic),
    url(r'topics/add', add_topic),
    url(r'topics/postAdd', add_post),
] + static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)
