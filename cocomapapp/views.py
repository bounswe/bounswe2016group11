from django.http import HttpResponse
from django.template import loader

def index(request):
    template = loader.get_template('global.html')
    context = {
        'asd': 'asd',
    }
    return HttpResponse(template.render(context, request))

def topic(request):
    template = loader.get_template('topic.html')
    context = {
        'asd': 'asd',
    }
    return HttpResponse(template.render(context, request))