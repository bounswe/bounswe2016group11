# -*- coding: utf-8 -*-
# Generated by Django 1.10.1 on 2016-10-30 18:41
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('cocomapapp', '0002_auto_20161030_1832'),
    ]

    operations = [
        migrations.AlterField(
            model_name='post',
            name='tags',
            field=models.ManyToManyField(null=True, to='cocomapapp.Tag'),
        ),
        migrations.AlterField(
            model_name='topic',
            name='relates_to',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='cocomapapp.Topic'),
        ),
        migrations.AlterField(
            model_name='topic',
            name='tags',
            field=models.ManyToManyField(null=True, to='cocomapapp.Tag'),
        ),
    ]
