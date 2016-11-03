from django.http import HttpResponse
from django.template import loader

#from cocomapapp.forms import TopicForm
from cocomapapp.models import Topic
from django.core.exceptions import ObjectDoesNotExist, MultipleObjectsReturned


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

def add_topic(request):
    template = loader.get_template('topicAdd.html')
    if request.method == "POST":
        try:
            Topic.objects.get(name="simple topic")
            return HttpResponse("This topic exists")
        except ObjectDoesNotExist:
            Topic.objects.create(name="simple topic")
        except MultipleObjectsReturned:
            return HttpResponse("This topic exists")


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

def math_topic(request):
    template = loader.get_template('topicMath.html')
    context = {
        'asd': 'asd',
    }
    return HttpResponse(template.render(context, request))
