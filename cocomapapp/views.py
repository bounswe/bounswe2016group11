from django.http import HttpResponse
from django.template import loader

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
    context = {
        'asd': 'asd',
    }
    return HttpResponse(template.render(context, request))