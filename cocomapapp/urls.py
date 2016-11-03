from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^$', views.index, name='index'),
    url(r'topic/show', views.show_topic, name='show_topic'),
    url(r'topic/add', views.add_topic, name='add_topic'),
    url(r'topic/second', views.second_topic, name='second_topic'),
    url(r'topic/math', views.math_topic, name='math_topic'),
    url(r'topic/postAdd', views.add_post, name='add_post'),
]
