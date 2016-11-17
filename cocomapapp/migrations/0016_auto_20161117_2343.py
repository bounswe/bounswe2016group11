# -*- coding: utf-8 -*-
# Generated by Django 1.10.1 on 2016-11-17 23:43
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('cocomapapp', '0015_auto_20161117_2220'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='post',
            name='tags',
        ),
        migrations.RemoveField(
            model_name='topic',
            name='tags',
        ),
        migrations.AddField(
            model_name='tag',
            name='post',
            field=models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.CASCADE, related_name='tags', to='cocomapapp.Post'),
        ),
        migrations.AddField(
            model_name='tag',
            name='topic',
            field=models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.CASCADE, related_name='tags', to='cocomapapp.Topic'),
        ),
    ]
