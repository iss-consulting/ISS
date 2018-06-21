""" Admin for start app """
from django.contrib import admin
from start.models import Person, PersonImage

admin.site.register(Person)
admin.site.register(PersonImage)
