from django.http import HttpResponse
from django.template import loader
import json

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
    if request.is_ajax():
        context = json.dumps({"result": ["delilo","delilo2","delilo3"]})
        return HttpResponse(context)
    else:
        template = loader.get_template('topicAdd.html')
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
