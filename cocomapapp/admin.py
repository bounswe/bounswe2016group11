from django.contrib import admin

from .models import Topic, Post, Tag, User

admin.site.register(Topic)
admin.site.register(Post)
admin.site.register(Tag)
admin.site.register(User)
