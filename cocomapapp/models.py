from django.db import models
from django.utils import timezone
from django.utils.encoding import python_2_unicode_compatible

@python_2_unicode_compatible
class User(models.Model):
  first_name = models.TextField()
  last_name = models.TextField()
  email = models.EmailField(max_length=70, blank=False, null=False, unique=True)
  password = models.CharField(max_length=30, blank=False, null=False)
  created_at = models.DateTimeField(auto_now_add=True)
  updated_at = models.DateTimeField(auto_now=True)

  def save(self, *args, **kwargs):
    return super(User, self).save(*args, **kwargs)

  def __str__(self):
    return (str(self.id) + ' ' + self.first_name + ' ' + self.last_name)

@python_2_unicode_compatible
class Topic(models.Model):
  name = models.TextField()
  user = models.ForeignKey(User, on_delete=models.CASCADE)
  #relates_to = models.ManyToManyField('self', null=True, blank=True)
  created_at = models.DateTimeField(auto_now_add=True)
  updated_at = models.DateTimeField(auto_now=True)

  class Meta:
    ordering = ('pk',)

  def save(self, *args, **kwargs):
    return super(Topic, self).save(*args, **kwargs)

  def __str__(self):
    return (str(self.id) + ' ' + self.name)


@python_2_unicode_compatible
class Relation(models.Model):
    topic_from = models.ForeignKey(Topic, related_name='relates_to', on_delete=models.CASCADE)
    topic_to = models.ForeignKey(Topic, on_delete=models.CASCADE)
    label = models.TextField()

    def save(self, *args, **kwargs):
      return super(Relation, self).save(*args, **kwargs)

    def __str__(self):
      return (str(self.id) + ' ' + self.label)

@python_2_unicode_compatible
class Post(models.Model):
  content = models.TextField()
  user = models.ForeignKey(User, on_delete=models.CASCADE)
  topic = models.ForeignKey(Topic, related_name='posts', null=True, blank=True)
  positive_reaction_count = models.PositiveIntegerField(default=0)
  negative_reaction_count = models.PositiveIntegerField(default=0)
  created_at = models.DateTimeField(auto_now_add=True)
  updated_at = models.DateTimeField(auto_now=True)

  def save(self, *args, **kwargs):
    return super(Post, self).save(*args, **kwargs)

  def __str__(self):
    return (str(self.id) + ' ' + self.content)

@python_2_unicode_compatible
class Tag(models.Model):
  name = models.TextField()
  user = models.ForeignKey(User, on_delete=models.CASCADE)
  topic = models.ForeignKey(Topic, related_name='tags', null=True, blank=True)
  post = models.ForeignKey(Post, related_name='tags', null=True, blank=True)
  created_at = models.DateTimeField(auto_now_add=True)
  updated_at = models.DateTimeField(auto_now=True)

  def save(self, *args, **kwargs):
    return super(Tag, self).save(*args, **kwargs)

  def __str__(self):
    return (str(self.id) + ' ' + self.name)

  def __unicode__(self):
    return (str(self.id) + ' ' + self.name)