""" Models for start app """
from django.utils import timezone
from django.db import models
from django.contrib.auth.models import User


class Person(models.Model):
    """ Class for extra data for a user """
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    genre = models.CharField(max_length=1, blank=True, null=True)
    born_date = models.DateField(blank=True, null=True)
    is_provider = models.BooleanField(default=False)

    def __str__(self):
        return self.user.get_full_name()


class PersonImage(models.Model):
    """ Image for an user """
    person = models.ForeignKey(Person)
    image = models.ImageField(upload_to='photos/users/profile',
                              blank=True)
    upload_date = models.DateTimeField(default=timezone.now)

    def __str__(self):
        return self.person.user.get_full_name() + ': ' + self.image.url
