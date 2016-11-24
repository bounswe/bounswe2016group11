from django.http import HttpResponse, HttpResponseRedirect
from django.shortcuts import get_object_or_404, redirect
from django.template import loader
import json

from cocomapapp.models import User, Tag, Topic, Post, Relation
from cocomapapp.serializers import UserSerializer, TagSerializer, TopicSerializer, HotTopicsSerializer, PostSerializer, RelationSerializer
from rest_framework import generics

from django.core.exceptions import ObjectDoesNotExist, MultipleObjectsReturned
from django.views.decorators.csrf import csrf_exempt
from django.core import serializers

from rest_framework.decorators import api_view
from rest_framework.response import Response
from .forms import RegisterForm, LoginForm
from .models import User
from django.template import RequestContext


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

class RelationRetrieve(generics.RetrieveAPIView):
    queryset = Relation.objects.all()
    serializer_class = RelationSerializer

class RelationList(generics.ListAPIView):
    queryset = Relation.objects.all()
    serializer_class = RelationSerializer

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

@api_view(['GET'])
def wikidata_query(request, str):
    url_head = 'https://query.wikidata.org/sparql?query=PREFIX%20entity:%20<http://www.wikidata.org/entity/>%20SELECT%20?propUrl%20?propLabel%20?valUrl%20?valLabel%20?picture%20WHERE%20{%20hint:Query%20hint:optimizer%20%27None%27%20.%20{%20BIND(entity:';
    url_second = '%20AS%20?valUrl)%20.%20BIND("N/A"%20AS%20?propUrl%20)%20.%20BIND("identity"@en%20AS%20?propLabel%20)%20.%20}%20UNION%20{%20entity:';
    url_tail = '%20?propUrl%20?valUrl%20.%20?property%20?ref%20?propUrl%20.%20?property%20a%20wikibase:Property%20.%20?property%20rdfs:label%20?propLabel%20}%20?valUrl%20rdfs:label%20?valLabel%20FILTER%20(LANG(?valLabel)%20=%20%27en%27)%20.%20OPTIONAL{%20?valUrl%20wdt:P18%20?picture%20.}%20FILTER%20(lang(?propLabel)%20=%20%27en%27%20)%20}%20ORDER%20BY%20?propUrl%20?valUrl%20LIMIT%20200&format=json'

    if request.method == 'GET':
        r = requests.get(url_head+str+url_second+str+url_tail);
        return Response(r.json()['results']['bindings'])
     #print r


def index(request):
    template = loader.get_template('global.html')
    hot_topics = serializers.serialize("json", Topic.objects.order_by('-updated_at')[:5])
    random_topic = serializers.serialize("json", Topic.objects.order_by('?')[:1])

    context = {
        'hot_topics': hot_topics,
        'random_topic': random_topic,
        'request': request,
    }
    return HttpResponse(template.render(context, request))

def login(request):

    if request.method =='POST':
        form = LoginForm(request.POST)
        if form.is_valid():
            user = User()
            user.email = form.cleaned_data['email']
            user.password = form.cleaned_data['password']
            checkUser = User.objects.get(email=user.email)
            if checkUser == User.DoesNotExist:
                return HttpResponseRedirect('/cocomapapp/login')
            if checkUser.password == user.password:
                request.session['username'] = checkUser.first_name
                return HttpResponseRedirect('/cocomapapp/')
            return HttpResponseRedirect('/cocomapapp/login')
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
            return HttpResponseRedirect('/cocomapapp/login')
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
    template = loader.get_template('topic.html')
    if request.method == "POST":
        user = User.objects.first()
        requested_topic = Topic.objects.get(id=id)
        postObject = Post.objects.create(user_id=user.id, topic_id=requested_topic.id,content=request.POST.get("content", ""), positive_reaction_count=0, negative_reaction_count=0)
        tags = request.POST.get("tags", "").split(",");
        for tag in tags:
           try:
               tagObject = Tag.objects.get(name=tag)
           except ObjectDoesNotExist:
                tagObject = Tag.objects.create(name=tag, user=user, post_id=postObject.id)

           except MultipleObjectsReturned:
               return HttpResponse("Multiple tags exist for." + tag + " Invalid State.")
           postObject.tags.add(tagObject)
        return HttpResponseRedirect(request.META.get('HTTP_REFERER'))
    try:
        topic = Topic.objects.get(id=id)
        serialized_topic = TopicSerializer(topic)
        topic_json = JSONRenderer().render(serialized_topic.data)
    except ObjectDoesNotExist:
        return HttpResponse("This topic doesn't exists!")
    hot_topics = Topic.objects.order_by('-updated_at')[:5]
    serialized_hot_topics = HotTopicsSerializer(hot_topics, many=True)
    hot_topics_json = JSONRenderer().render(serialized_hot_topics.data)
    context = {
        'topic': topic_json,
        'hot_topics': hot_topics_json
    }

    return HttpResponse(template.render(context, request))

@csrf_exempt
def add_topic(request):
    template = loader.get_template('topicAdd.html')
    try:
        topic = serializers.serialize("json", Topic.objects.filter())
    except ObjectDoesNotExist:
        return HttpResponse("This topic doesn't exists!")
    context = {
        'topics': topic
    }
    #User.objects.create(first_name="Ali", last_name="Veli", email="a@b", password="1234");
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
                    tagObject = Tag.objects.create(name=tag, user=user, topic_id=topicObject.id)
                except MultipleObjectsReturned:
                    return HttpResponse("Multiple tags exist for." + tag + " Invalid State.")
                topicObject.tags.add(tagObject)
                context = {
                }
            try:
                relatedTopicObject = Topic.objects.get(name=data["relates_to"])
                label = data["relationships_name"]
                Relation.objects.create(topic_from=topicObject, topic_to=relatedTopicObject, label=label)
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
