# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-11-15 16:51
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('cocomapapp', '0008_auto_20161115_1648'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='topic',
            name='posts',
        ),
        migrations.AddField(
            model_name='post',
            name='topic',
            field=models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.CASCADE, to='cocomapapp.Topic'),
        ),
    ]
