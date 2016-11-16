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
from rest_framework.renderers import TemplateHTMLRenderer
from rest_framework.renderers import JSONRenderer
from rest_framework.views import APIView
from django.http import Http404

# class TopicList(generics.ListAPIView):
#     queryset = Topic.objects.all()
#     serializer_class = TopicSerializer
#     renderer_classes = (TemplateHTMLRenderer,)
#     lookup_fields = ('name')
#     def get(self, request, *args, **kwargs):
#         self.object = self.get_object()
#         return Response({'topic': serializer_class}, template_name='global.html')

class TopicList(APIView):
    renderer_classes = [TemplateHTMLRenderer]
    template_name = 'global.html'
    def get(self, request, format=None):
        queryset = Topic.objects.all()
        serializer = TopicSerializer(queryset, many=True)
        return Response({'serializer': serializer, 'topics': queryset})

class TopicCreate(APIView):
    renderer_classes = [TemplateHTMLRenderer]
    template_name = 'topicAdd.html'
    def get(self, request, format=None):
        return Response()
    def post(self, request, format=None):
        serializer = TopicSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class TopicRetrieve(APIView):
    renderer_classes = [TemplateHTMLRenderer]
    template_name = 'topic.html'
    def get_object(self, pk):
        try:
            return Topic.objects.get(pk=pk)
        except Topic.DoesNotExist:
            raise Http404
    def get(self, request, pk, format=None):
        topic = self.get_object(pk)
        serializer = TopicSerializer(topic)
        return Response({'serializer': serializer.data})

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
    #User.objects.create(first_name="Ali", last_name="Veli", email="a@b", password="1234");
    if request.method == "POST":
        try:
            Topic.objects.get(name=request.POST.get("name"))
            return HttpResponse("This topic exists")
        except ObjectDoesNotExist:
            user = User.objects.first()
            user_id = user.id
            topicObject = Topic.objects.create(name=request.POST.get("name"), user_id= user_id)
            tags = request.POST.get("tags").split(",");
            for tag in tags:
                try:
                    tagObject = Tag.objects.get(name=tag)
                except ObjectDoesNotExist:
                    tagObject = Tag.objects.create(name=tag, user_id=user_id)
                except MultipleObjectsReturned:
                    return HttpResponse("Multiple tags exist for." + tag + " Invalid State.")
                topicObject.tags.add(tagObject)
                context = {
                    'topic': topicObject,
                }
        except MultipleObjectsReturned:
            return HttpResponse("This topic exists")
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
