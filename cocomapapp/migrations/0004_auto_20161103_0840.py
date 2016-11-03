# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-11-03 08:40
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('cocomapapp', '0003_auto_20161030_1841'),
    ]

    operations = [
        migrations.AlterField(
            model_name='topic',
            name='relates_to',
            field=models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.CASCADE, to='cocomapapp.Topic'),
        ),
        migrations.AlterField(
            model_name='topic',
            name='tags',
            field=models.ManyToManyField(blank=True, null=True, to='cocomapapp.Tag'),
        ),
    ]
