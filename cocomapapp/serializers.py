from rest_framework import serializers
from cocomapapp.models import User, Tag, Topic, Post, Relation

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('id', 'first_name', 'last_name', 'email', 'password', 'created_at', 'updated_at')

class TagSerializer(serializers.ModelSerializer):
    class Meta:
        model = Tag
        fields = ('id', 'name', 'created_at', 'updated_at')

class PostSerializer(serializers.ModelSerializer):
    tags = TagSerializer(many=True)
    user = UserSerializer()
    class Meta:
        model = Post
        fields = ('id', 'content', 'user', 'tags', 'topic', 'positive_reaction_count', 'negative_reaction_count', 'created_at', 'updated_at')

class TopicSerializer(serializers.ModelSerializer):
    posts = PostSerializer(many=True)
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
        fields = ('id', 'topic_from','topic_to','label')
