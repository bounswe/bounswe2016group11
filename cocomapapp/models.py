from django.db import models
from django.utils import timezone
from django.utils.encoding import python_2_unicode_compatible
from django.contrib.auth.models import User
from django.contrib.postgres.fields import ArrayField
from django.db.models import Count

# @python_2_unicode_compatible
# class User(models.Model):
#   first_name = models.TextField()
#   last_name = models.TextField()
#   email = models.EmailField(max_length=70, blank=False, null=False, unique=True)
#   password = models.CharField(max_length=30, blank=False, null=False)
#   created_at = models.DateTimeField(auto_now_add=True)
#   updated_at = models.DateTimeField(auto_now=True)

#   def save(self, *args, **kwargs):
#     return super(User, self).save(*args, **kwargs)

#   def __str__(self):
#     return (str(self.id) + ' ' + self.first_name + ' ' + self.last_name)


@python_2_unicode_compatible
class Tag(models.Model):
  wikidataID = models.TextField(primary_key = True)
  name = models.TextField(null=True, blank=True)
  #hidden = models.BooleanField(default=False)
  hidden_tags= ArrayField(models.CharField(max_length=15, blank=True), null=True, blank=True)
  created_at = models.DateTimeField(auto_now_add=True)
  updated_at = models.DateTimeField(auto_now=True)

  def save(self, *args, **kwargs):
    return super(Tag, self).save(*args, **kwargs)

  def __str__(self):
    if self.name:
      return (str(self.wikidataID) + ' ' + self.name)
    else:
      return (str(self.wikidataID) + ' - hidden')

  def __unicode__(self):
    return (str(self.wikidataID) + ' ' + self.name)

@python_2_unicode_compatible
class Topic(models.Model):
  name = models.TextField()
  user = models.ForeignKey(User, on_delete=models.CASCADE)
  tags = models.ManyToManyField(Tag, related_name='topics', null=True, blank=True)
  #relates_to = models.ManyToManyField('self', null=True, blank=True)
  created_at = models.DateTimeField(auto_now_add=True)
  updated_at = models.DateTimeField(auto_now=True)

  @property
  def hotness(self):
    creation_time = (timezone.now()-self.created_at).total_seconds()
    post_count = self.posts.count()
    if post_count is 0:
        latest_post_time = creation_time
    else:
        latest_post = self.posts.latest('created_at')
        latest_post_time = (timezone.now()-latest_post.created_at).total_seconds()

    like_counts = self.posts.aggregate( votes = Count('votes') )['votes']
    visit_count = self.visits.count()
    return post_count + like_counts + visit_count  - 2 * latest_post_time/3600 - creation_time/3600

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
    positive_reaction_count = models.PositiveIntegerField(default=0)
    negative_reaction_count = models.PositiveIntegerField(default=0)

    def save(self, *args, **kwargs):
      return super(Relation, self).save(*args, **kwargs)

    def __str__(self):
      return (str(self.id) + ' ' + self.label)

@python_2_unicode_compatible
class Post(models.Model):
  content = models.TextField()
  user = models.ForeignKey(User, on_delete=models.CASCADE)
  topic = models.ForeignKey(Topic, related_name ='posts', null=True, blank=True)
  tags = models.ManyToManyField(Tag,related_name = 'posts', null=True, blank=True)
  #positive_reaction_count = models.PositiveIntegerField(default=0)
  #negative_reaction_count = models.PositiveIntegerField(default=0)

  @property
  def positive_reaction_count(self):
     if self.votes.count == 0:
         return 0
     return len(self.votes.filter(is_positive=True))

  @property
  def negative_reaction_count(self):
     if self.votes.count == 0:
         return 0
     return len(self.votes.filter(is_positive=False))

  @property
  def accuracy(self):
      if (self.positive_reaction_count + self.negative_reaction_count) == 0:
          return 0
      return self.positive_reaction_count / (self.positive_reaction_count + self.negative_reaction_count) * 100

  created_at = models.DateTimeField(auto_now_add=True)
  updated_at = models.DateTimeField(auto_now=True)

  def save(self, *args, **kwargs):
    return super(Post, self).save(*args, **kwargs)

  def __str__(self):
    return (str(self.id) + ' ' + self.content)

@python_2_unicode_compatible
class Vote(models.Model):
    user = models.ForeignKey(User, related_name='votes', on_delete=models.CASCADE)
    post = models.ForeignKey(Post, related_name ='votes', on_delete=models.CASCADE)
    is_positive = models.BooleanField(default=True)

    def save(self, *args, **kwargs):
        return super(Vote, self).save(*args, **kwargs)

    def __str__(self):
        return (str(self.id) + ' ' + str(self.is_positive))

@python_2_unicode_compatible
class Visit(models.Model):
    user = models.ForeignKey(User, related_name='visits', on_delete=models.CASCADE)
    topic = models.ForeignKey(Topic, related_name ='visits', on_delete=models.CASCADE)
    visit_date = models.DateTimeField(auto_now_add=True)

    def save(self, *args, **kwargs):
        return super(Visit, self).save(*args, **kwargs)

    def __str__(self):
        return (str(self.id) + ' ' + str(self.visit_date))
