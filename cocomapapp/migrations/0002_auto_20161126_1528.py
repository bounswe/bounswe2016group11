# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-11-26 15:28
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('cocomapapp', '0001_initial'),
    ]

    operations = [
        migrations.RenameField(
            model_name='tag',
            old_name='id',
            new_name='wikidataID',
        ),
    ]