# -*- coding: utf-8 -*-
# Generated by Django 1.11.1 on 2018-06-01 20:54
from __future__ import unicode_literals

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion
import django.utils.timezone


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Person',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('genre', models.CharField(blank=True, max_length=1, null=True)),
                ('born_date', models.DateField(blank=True, null=True)),
                ('is_provider', models.BooleanField(default=False)),
                ('user', models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='PersonImage',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('image', models.ImageField(blank=True, upload_to='photos/users/profile')),
                ('upload_date', models.DateTimeField(default=django.utils.timezone.now)),
                ('person', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='start.Person')),
            ],
        ),
    ]
