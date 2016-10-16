from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^$', views.index, name='index'),
    url(r'topic/show', views.show_topic, name='show_topic'),
    url(r'topic/add', views.add_topic, name='add_topic'),
]