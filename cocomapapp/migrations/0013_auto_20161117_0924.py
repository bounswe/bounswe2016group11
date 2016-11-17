# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-11-17 09:24
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('cocomapapp', '0012_auto_20161115_1718'),
    ]

    operations = [
        migrations.CreateModel(
            name='Relation',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('label', models.TextField()),
            ],
        ),
        migrations.RemoveField(
            model_name='topic',
            name='relates_to',
        ),
        migrations.AddField(
            model_name='relation',
            name='topic_from',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='relates_to', to='cocomapapp.Topic'),
        ),
        migrations.AddField(
            model_name='relation',
            name='topic_to',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='cocomapapp.Topic'),
        ),
    ]
