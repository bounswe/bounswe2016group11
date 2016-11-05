from django.http import HttpResponse
from django.template import loader
import json

#from cocomapapp.forms import TopicForm
from cocomapapp.models import Topic
from cocomapapp.models import Post
from cocomapapp.models import Tag
from django.core.exceptions import ObjectDoesNotExist, MultipleObjectsReturned
from django.views.decorators.csrf import csrf_exempt


def index(request):
    template = loader.get_template('global.html')
    context = {
        'asd': 'asd',
    }
    return HttpResponse(template.render(context, request))

def show_topic(request):

    template = loader.get_template('topic.html')
    context = {
        'asd': 'asd',
    }
    return HttpResponse(template.render(context, request))

@csrf_exempt
def add_topic(request):
    template = loader.get_template('topicAdd.html')
    #if request.method == "POST":
    #    try:
    #        Topic.objects.get(name="simple topic")
    #        return HttpResponse("This topic exists")
    #    except ObjectDoesNotExist:
    #        Topic.objects.create(name="simple topic")
    #    except MultipleObjectsReturned:
    #        return HttpResponse("This topic exists")

    if request.method == "POST":
        try:
            Topic.objects.get(name=request.POST.get("topicName", ""))
            return HttpResponse("This topic exists")
        except ObjectDoesNotExist:
            topicObject = Topic.objects.create(name=request.POST.get("topicName", ""))
            tags = request.POST.get("tags", "").split(",");
            for tag in tags:
                try:
                    tagObject = Tag.objects.get(name=tag)
                except ObjectDoesNotExist:
                    tagObject = Tag.objects.create(name=tag)
                except MultipleObjectsReturned:
                    return HttpResponse("Multiple tags exist for." + tag + " Invalid State.")
                topicObject.tags.add(tagObject)

        except MultipleObjectsReturned:
            return HttpResponse("This topic exists")

    context = {
        'asd': 'asd',
    }
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

def second_topic(request):
    template = loader.get_template('secondTopic.html')
    context = {
        'asd': 'asd',
    }
    return HttpResponse(template.render(context, request))

@csrf_exempt
def math_topic(request):
    template = loader.get_template('topicMath.html')
    context = {
        'asd': 'asd',
    }
    return HttpResponse(template.render(context, request))
