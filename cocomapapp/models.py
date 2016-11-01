from django.db import models
from django.utils import timezone
from django.utils.encoding import python_2_unicode_compatible

@python_2_unicode_compatible
class Tag(models.Model):
  name = models.TextField()
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
  relates_to = models.ForeignKey('self', null=True, blank=True)
  tags = models.ManyToManyField(Tag, null=True, blank=True)
  created_at     = models.DateTimeField(editable=False)
  updated_at    = models.DateTimeField()

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
  tags = models.ManyToManyField(Tag, null=True)
  positive_reaction_count = models.PositiveIntegerField()
  negative_reaction_count = models.IntegerField()
  created_at     = models.DateTimeField(editable=False)
  updated_at    = models.DateTimeField()

  def save(self, *args, **kwargs):
    if not self.id:
      self.created_at = timezone.now()
    self.updated_at = timezone.now()
    return super(Post, self).save(*args, **kwargs)

  def __str__(self):
    return (str(self.id) + ' ' + self.content)
