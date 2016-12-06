from rest_framework import serializers
from cocomapapp.models import Tag, Topic, Post, Relation
from django.contrib.auth.models import User

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('id', 'last_login', 'is_superuser', 'username', 'first_name', 'last_name', 'email', 'is_staff')

class TagSerializer(serializers.ModelSerializer):
    class Meta:
        model = Tag
        fields = ('wikidataID', 'name', 'topics', 'posts', 'created_at', 'updated_at')

class PostSerializer(serializers.ModelSerializer):
    tags = TagSerializer(many=True)
    user = UserSerializer()
    class Meta:
        model = Post
        fields = ('id', 'content', 'user', 'tags', 'topic', 'positive_reaction_count', 'negative_reaction_count', 'created_at', 'updated_at')

class TopicSerializer(serializers.ModelSerializer):
    posts = PostSerializer(many=True, read_only=True)
    tags = TagSerializer(many=True)
    user = UserSerializer()
    class Meta:
        model = Topic
        fields = ('id', 'name', 'user', 'relates_to', 'tags', 'posts', 'created_at', 'updated_at')

class HotTopicsSerializer(serializers.ModelSerializer):
    tags = TagSerializer(many=True)
    user = UserSerializer()
    class Meta:
        model = Topic
        fields = ('id', 'name', 'user', 'tags', 'created_at', 'updated_at')

class RelationSerializer(serializers.ModelSerializer):
    class Meta:
        model = Relation
        fields = ('id', 'topic_from','topic_to','label', 'positive_reaction_count', 'negative_reaction_count',)
