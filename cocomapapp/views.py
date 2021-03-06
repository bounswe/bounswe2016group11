from django.db.models import Q
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from django.shortcuts import get_object_or_404, redirect
from django.template import loader
import json

from cocomapapp.models import Tag, Topic, Post, Relation, Vote, Visit
from django.contrib.auth.models import User
from cocomapapp.serializers import UserSerializer, TagSerializer, TopicSerializer, TopicNestedSerializer, HotTopicsSerializer, PostSerializer, PostNestedSerializer, RelationSerializer, VoteSerializer, VisitSerializer, RelationBulkSerializer
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

from functools import reduce
import operator
from django.utils import timezone

import requests
from io import StringIO

from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser
from rest_framework.views import APIView
from rest_framework_bulk import (
    BulkListSerializer,
    BulkSerializerMixin,
    ListBulkCreateUpdateDestroyAPIView,
)


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
    """
    A view that lists all topics.
    """

    queryset = Topic.objects.all()
    serializer_class = TopicNestedSerializer

class TopicCreate(ReadNestedWriteFlatMixin, generics.CreateAPIView):
    """
    A view that allows users to create topics.
    """

    serializer_class = TopicSerializer

class TopicRetrieve(ReadNestedWriteFlatMixin, generics.RetrieveAPIView):
    """
    A view that retrieves details of a topic given its id.
    """

    queryset = Topic.objects.all()
    serializer_class = TopicNestedSerializer

class PostCreate(generics.CreateAPIView):
    """
    A view that allows users to create a post to a topic.
    """

    serializer_class = PostSerializer
    serializer_class.Meta.depth = 0
    def post(self, request, format=None):
        serializer = PostSerializer(data=request.data)
        if serializer.is_valid():
            obj = serializer.save()
            newSerializer = PostNestedSerializer(obj)
            return Response(newSerializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    #queryset = Post.objects.all()
    #serializer_class = PostSerializer

class PostRetrieve(ReadNestedWriteFlatMixin,generics.RetrieveAPIView):
    """
    A view that retrieves details of a post given its id.
    """

    queryset = Post.objects.all()
    serializer_class = PostNestedSerializer

class PostUpdate(ReadNestedWriteFlatMixin,generics.UpdateAPIView):
    """
    A view that updates details of a post.
    """

    queryset = Post.objects.all()
    serializer_class = PostSerializer

class PostDelete(ReadNestedWriteFlatMixin,generics.DestroyAPIView):
    """
    A view that deletes a post.
    """

    serializer_class = PostSerializer
    def get_queryset(self, *args, **kwargs):
        data = JSONParser().parse(self.request)
        if data['user_id']:
            user = User.objects.get(id = data['user_id'])
        else:
            user = self.request.user
        post = Post.objects.filter(id = self.kwargs['pk'], user = user)
        return post

class RelationRetrieve(ReadNestedWriteFlatMixin,generics.RetrieveAPIView):
    """
    A view that retrieves details of a relation given its id.
    """

    queryset = Relation.objects.all()
    serializer_class = RelationSerializer

class RelationList(ReadNestedWriteFlatMixin,generics.ListAPIView):
    """
    A view that lists all relations.
    """

    serializer_class = RelationSerializer
    def get_queryset(self, *args, **kwargs):
        queryset_list = Relation.objects.all()
        query = self.request.GET.get("topic_id")
        if query:
            queryset_list = queryset_list.filter(Q(topic_to__id=query) | Q(topic_from__id=query))

        return queryset_list

#DEPRECATED
class RecommendedTopics(ReadNestedWriteFlatMixin,generics.ListAPIView):
    serializer_class = TopicNestedSerializer
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

#DEPRECATED
class RecommendedPosts(ReadNestedWriteFlatMixin,generics.ListAPIView):
    serializer_class = PostNestedSerializer
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
                recommended_post_ids.append(sorted(topic.posts, key=lambda t: t.positive_reaction_count)[:1])

            queryset_list = Post.objects.filter(id__in=recommended_post_ids)

        else:
            queryset_list = sorted(Post.objects.all(), key=lambda t: -t.positive_reaction_count)[:5]

        return queryset_list

class RelationCreate(ListBulkCreateUpdateDestroyAPIView):
    """
    A view to create a relation between two topics.
    """

    queryset = Relation.objects.all()
    serializer_class = RelationBulkSerializer

class TagCreate(ReadNestedWriteFlatMixin,generics.CreateAPIView):
    """
    A view to create a tag.
    """

    serializer_class = TagSerializer

class TagRetrieve(ReadNestedWriteFlatMixin,generics.RetrieveAPIView):
    """
    A view to retrieve details of a tag.
    """

    queryset = Tag.objects.all()
    serializer_class = TagSerializer

class VisitCreate(ReadNestedWriteFlatMixin,generics.CreateAPIView):
    """
    A view that allows visit events to be created when a user enters a topic page.
    """

    serializer_class = VisitSerializer

#@csrf_exempt
@api_view(['POST'])
def post_vote(request):
    """
    A view to create (or update) authenticated user's vote on a post.
    If that user does not have a previous vote on that post, create it.
    If that user's existing vote is different from the one being created, update it.
    Otherwise, reset that user's vote.
    """
    if request.method == 'POST':
        user = request.user
        post_id = request.data['post_id']
        try:
            post = Post.objects.get(pk=post_id)
        except Post.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        is_positive = request.data['is_positive'].title() == "True"
        try:
            oldVote = Vote.objects.get(user = user, post = post)
            if oldVote.is_positive == is_positive:
                oldVote.delete()
                #return Response(oldVote.delete())
            else:
                oldVote.is_positive = is_positive
                oldVote.save()
                newVote = oldVote

        except Vote.DoesNotExist:
            newVote = Vote.objects.create(user=user, post=post, is_positive=is_positive)

        serializer = PostNestedSerializer(post)
        serializer.Meta.depth = 1;
        return Response(serializer.data)
        #serializer = VoteSerializer(newVote)
        #return Response(serializer.data)


@api_view(['GET'])
def getRecommendedTopics(request, limit):
    """
    A view to recommend <limit> amount of topics to the authenticated user.
    The formula depends on the hotness of the topic as well as the number of times and the
    most recent time the user has visited any topic related to the topic to be recommended.
    Each post is sorted by that formula, and the top <limit> are returned.
    """
    if request.method == 'GET':
        user = request.user;
        scores = {};
        for topic in Topic.objects.all():

            neighbor_visits = Visit.objects.filter(user=user, topic__relates_to__topic_to=topic)

            neighbor_visits_count = len(neighbor_visits);
            if neighbor_visits_count > 0:
                last_neighbor_visit = neighbor_visits.order_by('-visit_date')[0].visit_date;
            else:
                last_neighbor_visit = topic.created_at

            relevance_score = 5*neighbor_visits_count - (timezone.now()-last_neighbor_visit).total_seconds()/3600
            recommendation = relevance_score + topic.hotness

            scores[topic] = recommendation;

        sorted_scores = sorted(scores.items(), key=operator.itemgetter(1), reverse=True)[:int(limit)]
        recommended_topics = [key for key, value in sorted_scores]
        #print(recommended_topics)
        serializer = TopicNestedSerializer(recommended_topics, many=True);
        return Response(serializer.data)

@api_view(['GET'])
def getRecommendedPosts(request, limit):
    """
    A view to recommend <limit> amount of posts to the authenticated user.
    Posts created after the user has visited a topic are considered only.
    Those posts are sorted by a formula related to the latest time the user has
    visited the post's topic, and the accuracy of the post.
    The top <limit> posts are returned.
    """
    if request.method == 'GET':
        user = request.user;
        scores = {};
        for topic in Topic.objects.filter(visits__user=user).distinct():

            last_visit = topic.visits.filter(user=user).order_by('-visit_date')[0].visit_date;

            unread_posts = topic.posts.filter(created_at__gt=last_visit)
            for post in unread_posts:
                score = 10 * post.accuracy - (timezone.now()-last_visit).total_seconds()/3600
                scores[post] = score;

        extraPosts = Post.objects.exclude(topic__visits__user=user).order_by('-created_at')
        sorted_scores = sorted(scores.items(), key=operator.itemgetter(1), reverse=True)
        recommended_posts = [key for key, value in sorted_scores]
        recommended_posts += extraPosts
        recommended_posts = recommended_posts[:int(limit)]

        TopicNestedSerializer.Meta.depth = 1
        PostNestedSerializer.Meta.depth = 1
        serializer = PostNestedSerializer(recommended_posts, many=True);
        return Response(serializer.data)


@api_view(['GET'])
def listTopicRelevance(request):
    """
    An unused API to return statistics related to the authenticated user and all topics.
    """
    if request.method == 'GET':
        user = request.user;
        data = [];
        for topic in Topic.objects.all():
            row = {};

            topicSerializer = TopicNestedSerializer(topic)
            topicSerializer.Meta.depth = 1;
            #row['topic'] = topicSerializer.data;
            user_visits = topic.visits.filter(user=user)
            visitSerializer = VisitSerializer(user_visits, many=True)
            #visitSerializer.Meta.depth = 1;
            row['visit_count'] = len(user_visits);
            if row['visit_count'] > 0:
                row['last_visit']  = user_visits.order_by('-visit_date')[0].visit_date
            else:
                row['last_visit'] = topic.created_at

            neighbor_visits = Visit.objects.filter(user=user, topic__relates_to__topic_to=topic)

            row['neighbor_visits_count'] = len(neighbor_visits);
            if row['neighbor_visits_count'] > 0:
                row['last_neighbor_visit'] = neighbor_visits.order_by('-visit_date')[0].visit_date;
            else:
                row['last_neighbor_visit'] = topic.created_at

            row['post_count'] = len(topic.posts.filter(user=user))
            row['like_count'] = len(topic.posts.filter(votes__user=user))
            row['relevance_score'] = 5*row['neighbor_visits_count'] - (timezone.now()-row['last_neighbor_visit']).total_seconds()/3600
            row['recommendation'] = row['relevance_score'] + topic.hotness

            data.append(row)

        print(data)
        return Response(data)

#@csrf_exempt
#@api_view(['PUT'])
#def post_downvote(request, pk):
#    try:
#        post = Post.objects.get(pk=pk)
#    except Post.DoesNotExist:
#        return Response(status=status.HTTP_404_NOT_FOUND)

#    if request.method == 'PUT':
#        post.negative_reaction_count += 1
#        post.save()
#        serializer = PostSerializer(post)
#        return Response(serializer.data)

@api_view(['PATCH'])
def update_post(request, pk):
    """
    A view to update a post given its id.
    The authenticated user must be the post's owner.
    """
    data = JSONParser().parse(request)

    if request.method == 'PATCH':
        if data['user_id']:
            user = User.objects.get(id = data['user_id'])
        else:
            user = request.user
        try:
            postObject = Post.objects.filter(id=pk, user = user).first()
        except Post.DoesNotExist:
            content = {'user forbidden': 'you should be user of the requested post.'}
            return Response(content, status=status.HTTP_403_FORBIDDEN)


        postObject.content = data['content']
        postObject.tags.clear()

        for tag in data["tags"]:
            if len(tag)>0:
                if tag['name'] == '':
                    continue
                try:
                    tagObject = Tag.objects.get(wikidataID=tag['wikidataID'])
                except ObjectDoesNotExist:
                    tagObject = Tag.objects.create(wikidataID=tag['wikidataID'], name=tag['name'])
                except MultipleObjectsReturned:
                   return HttpResponse("Multiple tags exist for." + tag + " Invalid State.")

                unique_hidden_tags = list(set(tag['hidden_tags']))
                if unique_hidden_tags:
                    tagObject.hidden_tags = unique_hidden_tags

                tagObject.save()
                postObject.tags.add(tagObject)
        postObject.save()
        return Response(status=status.HTTP_200_OK)

@api_view(['PUT'])
def relation_upvote(request, pk):
    """
    A view to upvote a relation.
    """
    try:
        relation = Relation.objects.get(pk=pk)
    except Relation.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'PUT':
        relation.positive_reaction_count += 1
        relation.save()
        serializer = RelationSerializer(relation)
        return Response(serializer.data)

@api_view(['PUT'])
def relation_downvote(request, pk):
    """
    A view to downvote a relation.
    """
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
    """
    A view to search a string in wikidata.
    """
    url_head = 'https://www.wikidata.org/w/api.php?action=wbsearchentities&search='
    url_tail = '&language=en&format=json'
    if request.method == 'GET':
        r = requests.get(url_head+str+url_tail);
        return Response(r.json()['search'])
     #print r

@api_view(['GET'])
def topic_get_hot(request, limit):
    """
    A view to get the top <limit> hottest topics.
    The hotness depends on the time the topic is created,
    the number of posts, views and likes of the topic.
    """
    if request.method == 'GET':
        all_topics = Topic.objects.all()

        if int(limit) == 0:
            hot_topics = sorted(all_topics, key=lambda t: -t.hotness)
        else:
            hot_topics = sorted(all_topics, key=lambda t: -t.hotness)[:int(limit)]
        #hot_topics = Topic.objects.order_by('hotness')[:5]
        TopicNestedSerializer.Meta.depth = 2
        RelationSerializer.Meta.depth = 1
        serializer = TopicNestedSerializer(hot_topics, many=True)
        return Response(serializer.data)

@api_view(['GET'])
def post_get_recent(requst, limit):
    """
    A view to get the top <limit> most recent posts.
    """
    if requst.method == 'GET':
        recent_posts = Post.objects.order_by('-created_at')[:int(limit)]
        TopicNestedSerializer.Meta.depth = 1
        PostNestedSerializer.Meta.depth = 1
        serializer =  PostNestedSerializer(recent_posts, many=True)
        return Response(serializer.data)



@api_view(['GET'])
def wikidata_query(request, str):
    """
    A view to get the wikidata relations of a tag.
    """
    url_head = 'https://query.wikidata.org/sparql?query=PREFIX%20entity:%20<http://www.wikidata.org/entity/>%20SELECT%20?propUrl%20?propLabel%20?valUrl%20?valLabel%20?picture%20WHERE%20{%20hint:Query%20hint:optimizer%20%27None%27%20.%20{%20BIND(entity:';
    url_second = '%20AS%20?valUrl)%20.%20BIND("N/A"%20AS%20?propUrl%20)%20.%20BIND("identity"@en%20AS%20?propLabel%20)%20.%20}%20UNION%20{%20entity:';
    url_tail = '%20?propUrl%20?valUrl%20.%20?property%20?ref%20?propUrl%20.%20?property%20a%20wikibase:Property%20.%20?property%20rdfs:label%20?propLabel%20}%20?valUrl%20rdfs:label%20?valLabel%20FILTER%20(LANG(?valLabel)%20=%20%27en%27)%20.%20OPTIONAL{%20?valUrl%20wdt:P18%20?picture%20.}%20FILTER%20(lang(?propLabel)%20=%20%27en%27%20)%20}&format=json'

    if request.method == 'GET':
        r = requests.get(url_head+str+url_second+str+url_tail);
        return Response(r.json()['results']['bindings'])
     #print r


@api_view(['POST'])
def search_by_tags(request):
    """
    A view to get the search the posts and topics by wikidata tags.
    """
    resultTopics = []
    resultPosts = []
    if request.method == 'POST':
        data = request.data
        print(data)
        search_query = data['query']
        data_tags = list(set(data['tags']))
        print(data_tags)
        tagObjects = []
        if len(data_tags) > 0:
            tagObjects = Tag.objects.filter(hidden_tags__overlap=data_tags) | Tag.objects.filter(reduce(operator.and_, (Q(wikidataID=tag_id) for tag_id in data_tags)))
        for tagObject in tagObjects:
                print("LOL")
                tag_topics = tagObject.topics.all()
                tag_posts = tagObject.posts.all()
                for topic in tag_topics:
                    if topic not in resultTopics:
                        resultTopics.append(topic)
                for post in tag_posts:
                    if post not in resultPosts:
                        resultPosts.append(post)
        # for tag in data["tags"]:
        #     try:
        #         tagObjects = Tag.objects.filter(wikidataID=tag)
        #     except Tag.DoesNotExist:
        #         continue;
        #     for tagObject in tagObjects:
        #         tag_topics = tagObject.topics.all()
        #         tag_posts = tagObject.posts.all()
        #         for topic in tag_topics:
        #             if topic not in resultTopics:
        #                 resultTopics.append(topic)
        #         for post in tag_posts:
        #             if post not in resultPosts:
        #                 resultPosts.append(post)
        print(resultTopics);
        print(resultPosts);

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
        PostNestedSerializer.Meta.depth = 1

        topicSerializer = TopicNestedSerializer(resultTopics, many=True)
        #topicSerializer.Meta.depth = 1
        postSerializer = PostNestedSerializer(resultPosts, many=True)
        #postSerializer.Meta.depth = 1

        return Response({'topics':topicSerializer.data, 'posts':postSerializer.data})

def index(request):
    """
    Main page of cocomapapp which includes hot_topics and a random_topic .
    """
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
    """
    A view that shows the a topic's page. It includes all post written
    in that topic.
    """
    template = loader.get_template('topic.html')
    if request.method == "POST":
        print("POSTING")
        try:
            user = User.objects.get(username=request.user)
        except ObjectDoesNotExist:
            return HttpResponse("You should login to post!")
        requested_topic = Topic.objects.get(id=id)
        postObject = Post.objects.create(user_id=user.id, topic_id=requested_topic.id,content=request.POST.get("content", ""))
        tags = request.POST.get("tags", "").split(",");
        for tag in tags:
            if len(tag)>0:
                try:
                    tagObject = Tag.objects.get(wikidataID=tag)
                except ObjectDoesNotExist:
                    tagObject = Tag.objects.create(wikidataID=tag, name='Unknown')
                except MultipleObjectsReturned:
                   return HttpResponse("Multiple tags exist for." + tag + " Invalid State.")

                unique_hidden_tags = list(set(tag['hidden_tags']))
                if unique_hidden_tags:
                    tagObject.hidden_tags = unique_hidden_tags

                tagObject.save()
                postObject.tags.add(tagObject)
    try:
        topic = Topic.objects.get(id=id)
        TopicNestedSerializer.Meta.depth = 1
        serialized_topic = TopicNestedSerializer(topic)
        print(serialized_topic.data)
        topic_json = JSONRenderer().render(serialized_topic.data)
    except ObjectDoesNotExist:
        return HttpResponse("This topic doesn't exists!")
    hot_topics = Topic.objects.order_by('-updated_at')[:5]
    serialized_hot_topics = TopicNestedSerializer(hot_topics, many=True)
    hot_topics_json = JSONRenderer().render(serialized_hot_topics.data)
    context = {
        'topic': topic_json,
        'hot_topics': hot_topics_json
    }

    return HttpResponse(template.render(context, request))

@csrf_exempt
def add_topic(request):
    """
    A view that helps user to add new topics.
    """
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
            for tag in data["tags"]:
                tag_name = tag['label']
                if tag_name == '':
                    continue
                tag_wiki_id = tag['id']
                try:
                    tagObject = Tag.objects.get(wikidataID=tag_wiki_id)
                except ObjectDoesNotExist:
                    tagObject = Tag.objects.create(name=tag_name, wikidataID=tag_wiki_id)
                except MultipleObjectsReturned:
                    return HttpResponse("Multiple tags exist for." + tag + " Invalid State.")

                #hidden tags
                unique_hidden_tags = list(set(tag['hidden_tags']))
                if unique_hidden_tags:
                    tagObject.hidden_tags = unique_hidden_tags
                    # for hidden_tag in unique_hidden_tags:
                    #     try:
                    #         hiddenTagObject = Tag.objects.get(wikidataID=hidden_tag)
                    #     except ObjectDoesNotExist:
                    #         hiddenTagObject = Tag.objects.create(wikidataID=hidden_tag, hidden=True)
                    #         hiddenTagObject.save()
                tagObject.save()
                topicObject.tags.add(tagObject)
                context = {
                }

            # Add relationship to database.
            relates_to = data["relates_to"]
            for relation in data["relates_to"]:
                if relation['topic_id'] == '':
                    continue
                try:
                    relatedTopicObject = Topic.objects.get(pk=relation['topic_id'])
                    label = relation['rel_name']
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
                postStuff = data["post"]
                content = postStuff["post_content"]
                postObject = Post.objects.create(content=content, user=user, topic=topicObject)
                for tag in postStuff["post_tags"]:
                    if len(tag)>0:
                        if tag['label'] == '':
                            continue
                        try:
                            tagObject = Tag.objects.get(wikidataID=tag['id'])
                        except ObjectDoesNotExist:
                            tagObject = Tag.objects.create(wikidataID=tag['id'], name=tag['label'])
                        except MultipleObjectsReturned:
                           return HttpResponse("Multiple tags exist for." + tag + " Invalid State.")

                        unique_hidden_tags = list(set(tag['hidden_tags']))
                        if unique_hidden_tags:
                            tagObject.hidden_tags = unique_hidden_tags

                        tagObject.save()
                        postObject.tags.add(tagObject)
            # End of adding a post to new created topic

        return HttpResponse(template.render(context, request))
    return HttpResponse(template.render(context, request))

@csrf_exempt
def add_post(request, id):
    """
    A view that adds a post to a topic.
    """
    template = loader.get_template('topic.html')
    if request.method == "POST":
        data = JSONParser().parse(request)
        try:
            user = User.objects.get(username=request.user)
        except ObjectDoesNotExist:
            return HttpResponse("You should login to post!")
        requested_topic = Topic.objects.get(id=data["topic_id"])
        postObject = Post.objects.create(user_id=user.id, topic_id=requested_topic.id,content=data["content"])
        for tag in data["tags"]:
            if len(tag)>0:
                if tag['label'] == '':
                    continue
                try:
                    tagObject = Tag.objects.get(wikidataID=tag['id'])
                except ObjectDoesNotExist:
                    tagObject = Tag.objects.create(wikidataID=tag['id'], name=tag['label'])
                except MultipleObjectsReturned:
                   return HttpResponse("Multiple tags exist for." + tag + " Invalid State.")

                unique_hidden_tags = list(set(tag['hidden_tags']))
                if unique_hidden_tags:
                    tagObject.hidden_tags = unique_hidden_tags

                tagObject.save()
                postObject.tags.add(tagObject)
    try:
        topic = Topic.objects.get(id=id)
        serialized_topic = TopicNestedSerializer(topic)
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
def search(request):
    """
    A view that helps user to search something.
    """
    template = loader.get_template('searchresult.html')

    context = {
        'asd': 'asd',
    }
    return HttpResponse(template.render(context, request))

@csrf_exempt
def add_relation(request,id):
    """
    A view that adds relation between to topics.
    """
    template = loader.get_template('addRelation.html')
    requested_topic = Topic.objects.get(id=id)

    context = {
        'requested_topic': requested_topic,
    }
    return HttpResponse(template.render(context, request))

@csrf_exempt
def infocus(request, id):
    """
    A view that moves the clicked topic to center of the screen.
    """
    template = loader.get_template('infocus.html')
    try:
        topic = Topic.objects.get(id=id)
        serialized_topic = TopicNestedSerializer(topic)
        topic_json = JSONRenderer().render(serialized_topic.data)
    except ObjectDoesNotExist:
        return HttpResponse("This topic doesn't exists!")
    hot_topics = serializers.serialize("json", Topic.objects.order_by('-updated_at')[:5])
    random_topic = serializers.serialize("json", Topic.objects.order_by('?')[:1])

    context = {
        'hot_topics': hot_topics,
        'random_topic': random_topic,
        'request': request,
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
