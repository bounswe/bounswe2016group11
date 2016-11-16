from django.http import HttpResponse
from django.shortcuts import get_object_or_404
from django.template import loader
import json

from cocomapapp.models import User, Tag, Topic, Post
from cocomapapp.serializers import UserSerializer, TagSerializer, TopicSerializer, PostSerializer
from rest_framework import generics

from django.core.exceptions import ObjectDoesNotExist, MultipleObjectsReturned
from django.views.decorators.csrf import csrf_exempt
from django.core import serializers

from rest_framework.decorators import api_view
from rest_framework.response import Response

import requests

from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser

class TopicList(generics.ListAPIView):
    queryset = Topic.objects.all()
    serializer_class = TopicSerializer

class TopicCreate(generics.CreateAPIView):
    serializer_class = TopicSerializer

class TopicRetrieve(generics.RetrieveAPIView):
    queryset = Topic.objects.all()
    serializer_class = TopicSerializer

class PostCreate(generics.CreateAPIView):
    serializer_class = PostSerializer

class PostRetrieve(generics.RetrieveAPIView):
    queryset = Post.objects.all()
    serializer_class = PostSerializer

class PostUpdate(generics.UpdateAPIView):
    queryset = Post.objects.all()
    serializer_class = PostSerializer

@api_view(['PUT'])
def post_upvote(request, pk):
    try:
        post = Post.objects.get(pk=pk)
    except Snippet.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'PUT':
        post.positive_reaction_count += 1
        post.save()
        serializer = PostSerializer(post)
        return Response(serializer.data)

@api_view(['PUT'])
def post_downvote(request, pk):
    try:
        post = Post.objects.get(pk=pk)
    except Snippet.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'PUT':
        post.negative_reaction_count += 1
        post.save()
        serializer = PostSerializer(post)
        return Response(serializer.data)

@api_view(['GET'])
def wikidata_search(request, str):
    url_head = 'https://www.wikidata.org/w/api.php?action=wbsearchentities&search='
    url_tail = '&language=en&format=json'
    if request.method == 'GET':
        r = requests.get(url_head+str+url_tail);
        return Response(r.json()['search'])
     #print r


def index(request):
    template = loader.get_template('global.html')
    hot_topics = serializers.serialize("json", Topic.objects.order_by('-updated_at')[:5])
    random_topic = serializers.serialize("json", Topic.objects.order_by('?')[:1])

    context = {
        'hot_topics': hot_topics,
        'random_topic': random_topic
    }
    return HttpResponse(template.render(context, request))

def login(request):
    template = loader.get_template('login.html')
    context = {
        'asd': 'asd',
    }
    return HttpResponse(template.render(context, request))


@csrf_exempt
def show_topic(request, id):
    try:
        topic = serializers.serialize("json", Topic.objects.filter(id=id))
    except ObjectDoesNotExist:
        return HttpResponse("This topic doesn't exists!")
    hot_topics = serializers.serialize("json", Topic.objects.order_by('-updated_at')[:5])
    try:
        posts = serializers.serialize("json", Post.objects.filter(topic_id=id))
    except ObjectDoesNotExist:
        posts = None
    context = {
        'topic': topic,
        'hot_topics': hot_topics,
        'posts': posts
    }
    template = loader.get_template('topic.html')
    if request.method == "POST":
        user = User.objects.first()
        postObject = Post.objects.create(user_id=user.id, topic_id=topic.id,content=request.POST.get("content", ""), positive_reaction_count=0, negative_reaction_count=0)
        #tags = request.POST.get("tags", "").split(",");
        #for tag in tags:
        #    try:
        #        tagObject = Tag.objects.get(name=tag)
        #    except ObjectDoesNotExist:
        #        tagObject = Tag.objects.create(name=tag)
        #    except MultipleObjectsReturned:
        #        return HttpResponse("Multiple tags exist for." + tag + " Invalid State.")
        #    postObject.tags.add(tagObject)


    return HttpResponse(template.render(context, request))

@csrf_exempt
def add_topic(request):
    template = loader.get_template('topicAdd.html')
    context = {}
    if request.method == "POST":
        data = JSONParser().parse(request)
        try:
            Topic.objects.get(name=data["name"])
            return HttpResponse("This topic exists")
        except ObjectDoesNotExist:
            user = User.objects.first()
            name = data["name"]
            topicObject = Topic.objects.create(name=name, user=user)
            tags = data["tags"].split(",");
            for tag in tags:
                try:
                    tagObject = Tag.objects.get(name=tag)
                except ObjectDoesNotExist:
                    tagObject = Tag.objects.create(name=tag, user=user)
                except MultipleObjectsReturned:
                    return HttpResponse("Multiple tags exist for." + tag + " Invalid State.")
                topicObject.tags.add(tagObject)
                context = {
                }
            try:
                relatedTopicObject = Topic.objects.get(name=data["relates_to"])
                topicObject.relates_to.add(relatedTopicObject)
            except ObjectDoesNotExist:
                return HttpResponse("Related topic does not exist");
        except MultipleObjectsReturned:
            return HttpResponse("This topic exists")
        return HttpResponse(template.render(context, request))
    return HttpResponse(template.render(context, request))

@csrf_exempt
def add_post(request):
    template = loader.get_template('postAdd.html')
    if request.method == "POST":
        postObject = Post.objects.create(content=request.POST.get("content", ""), positive_reaction_count=0, negative_reaction_count=0)
        tags = request.POST.get("tags", "").split(",");
        for tag in tags:
            try:
                tagObject = Tag.objects.get(name=tag)
            except ObjectDoesNotExist:
                tagObject = Tag.objects.create(name=tag)
            except MultipleObjectsReturned:
                return HttpResponse("Multiple tags exist for." + tag + " Invalid State.")
            postObject.tags.add(tagObject)

    context = {
        'asd': 'asd',
    }
    return HttpResponse(template.render(context, request))



# def second_topic(request):
#     template = loader.get_template('secondTopic.html')
#     context = {
#         'asd': 'asd',
#     }
#     return HttpResponse(template.render(context, request))

# @csrf_exempt
# def math_topic(request):
#     template = loader.get_template('topicMath.html')
#     context = {
#         'asd': 'asd',
#     }
#     return HttpResponse(template.render(context, request))
