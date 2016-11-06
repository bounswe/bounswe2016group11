from django.db import models
from django.utils import timezone
from django.utils.encoding import python_2_unicode_compatible

@python_2_unicode_compatible
class User(models.Model):
  first_name = models.TextField()
  last_name = models.TextField()
  email = models.EmailField(max_length=70, blank=False, null=False, unique=True)
  password = models.CharField(max_length=30, blank=False, null=False)
  created_at = models.DateTimeField(editable=False)
  updated_at = models.DateTimeField()

  def save(self, *args, **kwargs):
    if not self.id:
      self.created_at = timezone.now()
    self.updated_at = timezone.now()
    return super(User, self).save(*args, **kwargs)

  def __str__(self):
    return (str(self.id) + ' ' + self.first_name + ' ' + self.last_name)

@python_2_unicode_compatible
class Tag(models.Model):
  name = models.TextField()
  user = models.ForeignKey(User, on_delete=models.CASCADE)
  created_at     = models.DateTimeField(editable=False)
  updated_at    = models.DateTimeField()

  def save(self, *args, **kwargs):
    if not self.id:
      self.created_at = timezone.now()
    self.updated_at = timezone.now()
    return super(Tag, self).save(*args, **kwargs)

  def __str__(self):
    return (str(self.id) + ' ' + self.name)

@python_2_unicode_compatible
class Topic(models.Model):
  name = models.TextField()
  user = models.ForeignKey(User, on_delete=models.CASCADE)
  relates_to = models.ForeignKey('self', null=True, blank=True)
  tags = models.ManyToManyField(Tag, null=True, blank=True)
  created_at = models.DateTimeField(editable=False)
  updated_at = models.DateTimeField()

  def save(self, *args, **kwargs):
    if not self.id:
      self.created_at = timezone.now()
    self.updated_at = timezone.now()
    return super(Topic, self).save(*args, **kwargs)

  def __str__(self):
    return (str(self.id) + ' ' + self.name)

@python_2_unicode_compatible
class Post(models.Model):
  content = models.TextField()
  user = models.ForeignKey(User, on_delete=models.CASCADE)
  topic = models.ForeignKey(Topic, on_delete=models.CASCADE)
  tags = models.ManyToManyField(Tag, null=True, blank=True)
  positive_reaction_count = models.PositiveIntegerField()
  negative_reaction_count = models.IntegerField()
  created_at = models.DateTimeField(editable=False)
  updated_at = models.DateTimeField()

  def save(self, *args, **kwargs):
    if not self.id:
      self.created_at = timezone.now()
    self.updated_at = timezone.now()
    return super(Post, self).save(*args, **kwargs)

  def __str__(self):
    return (str(self.id) + ' ' + self.content)
