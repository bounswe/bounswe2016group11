from django.http import HttpResponse
from django.http import HttpResponseRedirect
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
from .forms import RegisterForm, LoginForm

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

    if request.method =='POST':
        form = LoginForm(request.POST)
        if form.is_valid():
            newuser = User()
            newuser.email = form.cleaned_data['email']
            newuser.password = form.cleaned_data['password']
            newuser.save()
            return HttpResponseRedirect('/cocomapapp/')
    else:
        template = loader.get_template('login.html')
        registerForm = RegisterForm()
        loginForm = LoginForm()
        context = {
            'loginForm': loginForm,
            'registerForm': registerForm,
        }
    return HttpResponse(template.render(context, request))

def signup(request):

    if request.method =='POST':
        form = RegisterForm(request.POST)
        if form.is_valid():
            newuser = User()
            newuser.email = form.cleaned_data['email']
            newuser.first_name = form.cleaned_data['first_name']
            newuser.last_name = form.cleaned_data['last_name']
            newuser.password = form.cleaned_data['password']
            newuser.save()
            return HttpResponseRedirect('/cocomapapp/')
    else:
        template = loader.get_template('signup.html')
        registerForm = RegisterForm()
        loginForm = LoginForm()
        context = {
            'loginForm': loginForm,
            'registerForm': registerForm,
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
