from django.db.models import Q
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from django.shortcuts import get_object_or_404, redirect
from django.template import loader
import json

from cocomapapp.models import Tag, Topic, Post, Relation
from django.contrib.auth.models import User
from cocomapapp.serializers import UserSerializer, TagSerializer, TopicSerializer, HotTopicsSerializer, PostSerializer, RelationSerializer
from rest_framework import generics

from django.core.exceptions import ObjectDoesNotExist, MultipleObjectsReturned
from django.views.decorators.csrf import csrf_exempt
from django.core import serializers

from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import status
from .forms import RegisterForm, LoginForm
from django.template import RequestContext
from django.views.decorators.csrf import ensure_csrf_cookie





import requests

from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser


class ReadNestedWriteFlatMixin(object):
    """
    Mixin that sets the depth of the serializer to 0 (flat) for writing operations.
    For all other operations it keeps the depth specified in the serializer_class
    """
    def get_serializer_class(self, *args, **kwargs):
        serializer_class = super(ReadNestedWriteFlatMixin, self).get_serializer_class(*args, **kwargs)
        if self.request.method in ['PATCH', 'POST', 'PUT']:
            serializer_class.Meta.depth = 0
        else:
            serializer_class.Meta.depth = 1
        return serializer_class

class TopicList(ReadNestedWriteFlatMixin, generics.ListAPIView):
    queryset = Topic.objects.all()
    serializer_class = TopicSerializer

class TopicCreate(ReadNestedWriteFlatMixin, generics.CreateAPIView):
    serializer_class = TopicSerializer

class TopicRetrieve(ReadNestedWriteFlatMixin, generics.RetrieveAPIView):
    queryset = Topic.objects.all()
    serializer_class = TopicSerializer

class PostCreate(ReadNestedWriteFlatMixin,generics.CreateAPIView):
    serializer_class = PostSerializer

class PostRetrieve(ReadNestedWriteFlatMixin,generics.RetrieveAPIView):
    queryset = Post.objects.all()
    serializer_class = PostSerializer

class PostUpdate(ReadNestedWriteFlatMixin,generics.UpdateAPIView):
    queryset = Post.objects.all()
    serializer_class = PostSerializer

class RelationRetrieve(ReadNestedWriteFlatMixin,generics.RetrieveAPIView):
    queryset = Relation.objects.all()
    serializer_class = RelationSerializer

class RelationList(ReadNestedWriteFlatMixin,generics.ListAPIView):
    serializer_class = RelationSerializer
    def get_queryset(self, *args, **kwargs):
        queryset_list = Relation.objects.all()
        query = self.request.GET.get("topic_id")
        if query:
            queryset_list = queryset_list.filter(Q(topic_to__id=query) | Q(topic_from__id=query))

        return queryset_list

class RecommendedTopics(ReadNestedWriteFlatMixin,generics.ListAPIView):
    serializer_class = TopicSerializer
    def get_queryset(self, *args, **kwargs):
        query = self.request.GET.get("user_id")
        if query:
            last_5_posts = Post.objects.filter(Q(user__id=query)).order_by('-created_at')[:5]
            last_5_topic_ids = []
            for post in last_5_posts:
                last_5_topic_ids.append(post.topic.id)

            queryset_list = Topic.objects.filter(id__in=last_5_topic_ids)
        else:
            queryset_list = Topic.objects.order_by('-updated_at')[:5]

        return queryset_list

class RecommendedPosts(ReadNestedWriteFlatMixin,generics.ListAPIView):
    serializer_class = PostSerializer
    def get_queryset(self, *args, **kwargs):
        query = self.request.GET.get("user_id")
        if query:
            last_5_posts = Post.objects.filter(Q(user__id=query)).order_by('-created_at')[:5]
            last_5_topic_ids = []
            for post in last_5_posts:
                last_5_topic_ids.append(post.topic.id)

            recommended_topics = Topic.objects.filter(id__in=last_5_topic_ids)
            recommended_post_ids = []
            for topic in recommended_topics:
                recommended_post_ids.append((topic.posts.order_by('positive_reaction_count')[:1]).id)

            queryset_list = Post.objects.filter(id__in=recommended_post_ids)

        else:
            queryset_list = Post.objects.order_by('-positive_reaction_count')[:5]

        return queryset_list


class RelationCreate(ReadNestedWriteFlatMixin,generics.CreateAPIView):
    serializer_class = RelationSerializer

class TagCreate(ReadNestedWriteFlatMixin,generics.CreateAPIView):
    serializer_class = TagSerializer

class TagRetrieve(ReadNestedWriteFlatMixin,generics.RetrieveAPIView):
    queryset = Tag.objects.all()
    serializer_class = TagSerializer


@api_view(['PUT'])
def post_upvote(request, pk):
    try:
        post = Post.objects.get(pk=pk)
    except Post.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'PUT':
        post.positive_reaction_count += 1
        post.save()
        serializer = PostSerializer(post)
        return Response(serializer.data)


@ensure_csrf_cookie
@api_view(['PUT'])
def post_downvote(request, pk):
    try:
        post = Post.objects.get(pk=pk)
    except Post.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'PUT':
        post.negative_reaction_count += 1
        post.save()
        serializer = PostSerializer(post)
        return Response(serializer.data)

@api_view(['PUT'])
def relation_upvote(request, pk):
    try:
        relation = Relation.objects.get(pk=pk)
    except Relation.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'PUT':
        relation.positive_reaction_count += 1
        relation.save()
        serializer = RelationSerializer(relation)
        return Response(serializer.data)


@ensure_csrf_cookie
@api_view(['PUT'])
def relation_downvote(request, pk):
    try:
        relation = Relation.objects.get(pk=pk)
    except Relation.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'PUT':
        relation.negative_reaction_count += 1
        relation.save()
        serializer = RelationSerializer(relation)
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
def topic_get_hot(request):
    if request.method == 'GET':
        hot_topics = Topic.objects.order_by('-updated_at')[:5]
        serializer = TopicSerializer(hot_topics, many=True)
        return Response(serializer.data)

@api_view(['GET'])
def wikidata_query(request, str):
    url_head = 'https://query.wikidata.org/sparql?query=PREFIX%20entity:%20<http://www.wikidata.org/entity/>%20SELECT%20?propUrl%20?propLabel%20?valUrl%20?valLabel%20?picture%20WHERE%20{%20hint:Query%20hint:optimizer%20%27None%27%20.%20{%20BIND(entity:';
    url_second = '%20AS%20?valUrl)%20.%20BIND("N/A"%20AS%20?propUrl%20)%20.%20BIND("identity"@en%20AS%20?propLabel%20)%20.%20}%20UNION%20{%20entity:';
    url_tail = '%20?propUrl%20?valUrl%20.%20?property%20?ref%20?propUrl%20.%20?property%20a%20wikibase:Property%20.%20?property%20rdfs:label%20?propLabel%20}%20?valUrl%20rdfs:label%20?valLabel%20FILTER%20(LANG(?valLabel)%20=%20%27en%27)%20.%20OPTIONAL{%20?valUrl%20wdt:P18%20?picture%20.}%20FILTER%20(lang(?propLabel)%20=%20%27en%27%20)%20}&format=json'

    if request.method == 'GET':
        r = requests.get(url_head+str+url_second+str+url_tail);
        return Response(r.json()['results']['bindings'])
     #print r


@api_view(['POST'])
def search_by_tags(request):
    resultTopics = []
    resultPosts = []
    if request.method == 'POST':
        data = JSONParser().parse(request)
        search_query = data["query"]
        for tag in data["tags"]:
            try:
                tagObject = Tag.objects.get(wikidataID=tag)
            except Tag.DoesNotExist:
                continue;
            tag_topics = tagObject.topics.all()
            tag_posts = tagObject.posts.all()

            for topic in topics:
                if topic not in resultTopics:
                    resultTopics.append(topic)
            for post in posts:
                if post not in resultPosts:
                    resultPosts.append(post)
        query_topics = Topic.objects.filter(name__icontains=search_query)
        query_posts = Post.objects.filter(content__icontains=search_query)
        for topic in query_topics:
            if topic not in resultTopics:
                resultTopics.append(topic)
        for post in query_posts:
            if post not in resultPosts:
                resultPosts.append(post)

        all_relations = Relation.objects.all()
        for topic in resultTopics:
            for relation in all_relations:
                if (topic == relation.topic_from) and (relation.topic_to not in resultTopics):
                    resultTopics.append(relation.topic_to)
                if (topic == relation.topic_to) and (relation.topic_from not in resultTopics):
                    resultTopics.append(relation.topic_from)

        TopicSerializer.Meta.depth = 1
        PostSerializer.Meta.depth = 1

        topicSerializer = TopicSerializer(resultTopics, many=True)
        #topicSerializer.Meta.depth = 1
        postSerializer = PostSerializer(resultPosts, many=True)
        #postSerializer.Meta.depth = 1

        return Response({'topics':topicSerializer.data, 'posts':postSerializer.data})


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

# def login(request):

#     if request.method =='POST':
#         form = LoginForm(request.POST)
#         if form.is_valid():
#             user = User()
#             user.email = form.cleaned_data['email']
#             user.password = form.cleaned_data['password']
#             checkUser = User.objects.get(email=user.email)
#             if checkUser == User.DoesNotExist:
#                 return HttpResponseRedirect('/cocomapapp/login')
#             if checkUser.password == user.password:
#                 request.session['username'] = checkUser.first_name
#                 return HttpResponseRedirect('/cocomapapp/')
#             return HttpResponseRedirect('/cocomapapp/login')
#     else:
#         template = loader.get_template('login.html')
#         registerForm = RegisterForm()
#         loginForm = LoginForm()
#         context = {
#             'loginForm': loginForm,
#             'registerForm': registerForm,
#         }
#     return HttpResponse(template.render(context, request))

# def signup(request):

#     if request.method =='POST':
#         form = RegisterForm(request.POST)
#         if form.is_valid():
#             newuser = User()
#             newuser.email = form.cleaned_data['email']
#             newuser.first_name = form.cleaned_data['first_name']
#             newuser.last_name = form.cleaned_data['last_name']
#             newuser.password = form.cleaned_data['password']
#             newuser.save()
#             return HttpResponseRedirect('/cocomapapp/login')
#     else:
#         template = loader.get_template('signup.html')
#         registerForm = RegisterForm()
#         loginForm = LoginForm()
#         context = {
#             'loginForm': loginForm,
#             'registerForm': registerForm,
#         }
#     return HttpResponse(template.render(context, request))


@csrf_exempt
def show_topic(request, id):
    template = loader.get_template('topic.html')
    if request.method == "POST":
        try:
            user = User.objects.get(username=request.user)
        except ObjectDoesNotExist:
            return HttpResponse("You should login to post!")
        requested_topic = Topic.objects.get(id=id)
        postObject = Post.objects.create(user_id=user.id, topic_id=requested_topic.id,content=request.POST.get("content", ""), positive_reaction_count=0, negative_reaction_count=0)
        tags = request.POST.get("tags", "").split(",");
        for tag in tags:
           if len(tag)>0:
               try:
                   tagObject = Tag.objects.get(wikidataID=tag)
               except ObjectDoesNotExist:
                   tagObject = Tag.objects.create(wikidataID=tag, name='Unknown')
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
    print("add_topic'e girdi")
    template = loader.get_template('topicAdd.html')
    try:
        topic = serializers.serialize("json", Topic.objects.filter())
    except ObjectDoesNotExist:
        return HttpResponse("This topic doesn't exists!")
    context = {
        'topics': topic
    }

    if request.method == "POST":
        data = JSONParser().parse(request)

        print(data)
        # Add topic to database.
        try:
            Topic.objects.get(name=data["name"])
            print("topic exists")
            return HttpResponse("This topic exists")
        except ObjectDoesNotExist:
            try:
                user = User.objects.get(username=request.user)
            except ObjectDoesNotExist:
                return JsonResponse({'status':'false','message':'You should login to create a topic!'}, status=401)
            name = data["name"]
            topicObject = Topic.objects.create(name=name, user=user)
            print("topic object: ", topicObject)
            for tag in data["tags"]:
                tag_name = tag['label']
                if tag_name == '':
                    continue
                tag_wiki_id = tag['id']
                try:
                    tagObject = Tag.objects.get(wikidataID=tag_wiki_id)
                except ObjectDoesNotExist:
                    tagObject = Tag.objects.create(name=tag_name, wikidataID=tag_wiki_id)
                    tagObject.save()
                except MultipleObjectsReturned:
                    return HttpResponse("Multiple tags exist for." + tag + " Invalid State.")
                topicObject.tags.add(tagObject)
                context = {
                }
            # end of add topic to database.

            # Add relationship to database.
            relates_to = data["relates_to"]
            for relation in data["relates_to"]:
                if relation['topic_id'] == '':
                    continue
                try:
                    relatedTopicObject = Topic.objects.get(pk=relation['topic_id'])
                    label = relation['rel_name']
                    print("topic object : ", topicObject)
                    print("related topic object : ", relatedTopicObject)
                    print("relation name : ", relation)
                    relationObject = Relation.objects.create(topic_from=topicObject, topic_to=relatedTopicObject, label=label)
                except ObjectDoesNotExist:
                    print("error")
                    return HttpResponse("Related topic does not exist");
                except MultipleObjectsReturned:
                    print("error")
                    return HttpResponse("This topic exists")
            # End of add relationship to database.


            # Adding a post to new created topic

            if data["postAdd"] == True:
                postObject = data["post"]
                content = postObject["the_text"]
                user = User.objects.first() # user
                Post.objects.create(content=content, user=user, topic=topicObject)
            # End of adding a post to new created topic

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

@csrf_exempt
def search(request):
    template = loader.get_template('searchresult.html')

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
