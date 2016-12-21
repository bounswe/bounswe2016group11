from django.test import TestCase, Client

from cocomapapp.models import Tag, Topic, Post, Relation, Vote, Visit
from cocomapapp.serializers import UserSerializer, TagSerializer, TopicSerializer, TopicNestedSerializer, HotTopicsSerializer, PostSerializer, PostNestedSerializer, RelationSerializer, VoteSerializer, VisitSerializer, RelationBulkSerializer
import cocomapapp.views

from django.contrib.auth.models import AnonymousUser, User
from django.test.utils import setup_test_environment

from rest_framework.test import APIRequestFactory
from rest_framework.test import force_authenticate
from django.urls import reverse
from rest_framework import status
from rest_framework.test import APITestCase


def userSetup():
    return User.objects.create(username = "testUser", email = "test@user.com", password = "a1b2c3d4")

class TopicCreateTests(APITestCase):
    def setUp(self):
        self.user = userSetup()
        self.client.force_authenticate(user=self.user)

    def test_simple_create(self):
        url = reverse('topicCreate')
        data = {'name': 'testTopic', 'user': str(self.user.id), 'relates_to' : []}
        response = self.client.post(url, data, format='json')

        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(Topic.objects.count(), 1)
        self.assertEqual(Topic.objects.get().name, 'testTopic')
        self.assertEqual(Topic.objects.get().user, self.user)

class TopicListTests(APITestCase):
    def setUp(self):
        self.user = userSetup()
        self.client.force_authenticate(user=self.user)

    def test_empty_list(self):
        url = reverse('topicList')
        response = self.client.get(url)

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(len(response.data), 0)

    def test_single_list(self):
        createdTopic1 = Topic.objects.create(name='testTopic1', user=self.user)

        url = reverse('topicList')
        response = self.client.get(url)

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(len(response.data), 1)
        self.assertEqual(response.data[0]['name'], createdTopic1.name)

    def test_three_list(self):
        createdTopic1 = Topic.objects.create(name='testTopic1', user=self.user)
        createdTopic2 = Topic.objects.create(name='testTopic2', user=self.user)
        createdTopic3 = Topic.objects.create(name='testTopic3', user=self.user)

        url = reverse('topicList')
        response = self.client.get(url)

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(len(response.data), 3)
        self.assertEqual(response.data[0]['name'], createdTopic1.name)
        self.assertEqual(response.data[1]['name'], createdTopic2.name)
        self.assertEqual(response.data[2]['name'], createdTopic3.name)

class TopicRetrieveTests(APITestCase):
    def setUp(self):
        self.user = userSetup()
        self.client.force_authenticate(user=self.user)
        self.topic1 = Topic.objects.create(name='testTopic1', user=self.user)
        self.topic2 = Topic.objects.create(name='testTopic2', user=self.user)

    def test_first_topic(self):
        url = reverse('topicRetrieve', kwargs={'pk': self.topic1.id})
        response = self.client.get(url)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data['name'], self.topic1.name)

    def test_second_topic(self):
        url = reverse('topicRetrieve', kwargs={'pk': self.topic2.id})
        response = self.client.get(url)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data['name'], self.topic2.name)

    def test_nonexisting_topic(self):
        topic_id = self.topic1.id + self.topic2.id + 1
        url = reverse('topicRetrieve', kwargs={'pk': topic_id})
        response = self.client.get(url)
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

class PostCreateTests(APITestCase):
    def setUp(self):
        self.user = userSetup()
        self.client.force_authenticate(user=self.user)

    def test_simple_create(self):
        url = reverse('postCreate')
        data = {'user': str(self.user.id), 'content' : 'Post Create Content'}
        response = self.client.post(url, data, format='json')

        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(Post.objects.count(), 1)
        self.assertEqual(Post.objects.get().content, 'Post Create Content')
        self.assertEqual(Post.objects.get().user, self.user)

class PostRetrieveTests(APITestCase):
    def setUp(self):
        self.user = userSetup()
        self.client.force_authenticate(user=self.user)
        self.post1 = Post.objects.create(user=self.user, content= 'Post 1')
        self.post2 = Post.objects.create(user=self.user, content= 'Post 2')

    def test_first_post(self):
        url = reverse('postRetrieve', kwargs={'pk': self.post1.id})
        response = self.client.get(url)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data['content'], self.post1.content)

    def test_second_post(self):
        url = reverse('postRetrieve', kwargs={'pk': self.post2.id})
        response = self.client.get(url)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data['content'], self.post2.content)

class VisitCreateTests(APITestCase):
    def setUp(self):
        self.user = userSetup()
        self.client.force_authenticate(user=self.user)
        self.topic1 = Topic.objects.create(name='testTopic1', user=self.user)

    def test_first_topic(self):
        url = reverse('visitCreate')
        data = {'user': str(self.user.id), 'topic': self.topic1.id}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(response.data['topic'], self.topic1.id)
        self.assertEqual(response.data['user'], self.user.id)



class PostDeleteTests(APITestCase):
    def setUp(self):
        self.user = userSetup()
        self.client.force_authenticate(user=self.user)
        self.post1 = Post.objects.create(user=self.user, content='Post 1')

    def test_simple_delete(self):
        url = reverse('postDelete', kwargs={'pk': self.post1.id})
        data = {'user_id': str(self.user.id)}
        response = self.client.delete(url, data, format='json')

        self.assertEqual(response.status_code, status.HTTP_204_NO_CONTENT)

class GetRecommendedPostsTests(APITestCase):
    def setUp(self):
        self.user = userSetup()
        self.client.force_authenticate(user=self.user)
        self.post1 = Post.objects.create(user=self.user, content='Post 1')

    def test_simple_recommended_posts(self):
        url = reverse('getRecommendedPosts', kwargs={'limit': '5'})
        data = {'user': str(self.user.id)}
        response = self.client.get(url, data, format='json')

        self.assertEqual(response.status_code, status.HTTP_200_OK)


class GetRecommendedTopicsTests(APITestCase):
    def setUp(self):
        self.user = userSetup()
        self.client.force_authenticate(user=self.user)
        self.topic1 = Topic.objects.create(name='testTopic1', user=self.user)

    def test_simple_recommended_topics(self):
        url = reverse('getRecommendedTopics', kwargs={'limit': '3'})
        data = {'user': str(self.user.id)}
        response = self.client.get(url, data, format='json')

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data[0]["user"]["id"], self.user.id)
        self.assertEqual(response.data[0]["name"], self.topic1.name)

class RelationCreateTests(APITestCase):
    def setUp(self):
        self.user = userSetup()
        self.client.force_authenticate(user=self.user)
        self.topicFrom = Topic.objects.create(name='testTopic1', user=self.user)
        self.topicTo = Topic.objects.create(name='testTopic2', user=self.user)

    def test_simple_create(self):
        url = reverse('relationCreate')
        data = {'topic_from': str(self.topicFrom.id), 'topic_to': str(self.topicTo.id), 'label': 'Relation'}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(Relation.objects.count(), 1)
        self.assertEqual(Relation.objects.get().topic_from, self.topicFrom)
        self.assertEqual(Relation.objects.get().topic_to, self.topicTo)
        self.assertEqual(Relation.objects.get().label, 'Relation')
