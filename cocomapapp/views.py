from django.http import HttpResponse
from django.shortcuts import get_object_or_404
from django.template import loader
import json

#from cocomapapp.forms import TopicForm
from cocomapapp.models import Topic
from cocomapapp.models import Post
from cocomapapp.models import Tag
from cocomapapp.models import User
from django.core.exceptions import ObjectDoesNotExist, MultipleObjectsReturned
from django.views.decorators.csrf import csrf_exempt


def index(request):
    template = loader.get_template('global.html')
    hot_topics = Topic.objects.order_by('-updated_at')[:5]
    random_topic = Topic.objects.order_by('?').first()
    context = {
        'hot_topics': hot_topics,
        'random_topic': random_topic
    }
    return HttpResponse(template.render(context, request))

@csrf_exempt
def show_topic(request, id):
    topic = get_object_or_404(Topic, id=id)
    hot_topics = Topic.objects.order_by('-updated_at')[:5]
    template = loader.get_template('topic.html')
    try:
        posts = Post.objects.get(topic_id=id)
    except ObjectDoesNotExist:
        posts = None
    context = {
        'topic': topic,
        'hot_topics': hot_topics,
        'posts': posts
    }
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


    print (context)
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
            print (user)
            print (user_id)
            topicObject = Topic.objects.create(name=request.POST.get("name"), user_id= user_id)
            tags = request.POST.get("tags").split(",");
            print (tags)
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
