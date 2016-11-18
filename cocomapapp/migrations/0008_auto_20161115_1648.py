# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-11-15 16:48
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('cocomapapp', '0007_auto_20161115_1637'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='post',
            name='topic',
        ),
        migrations.AddField(
            model_name='topic',
            name='posts',
            field=models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.CASCADE, to='cocomapapp.Post'),
        ),
    ]