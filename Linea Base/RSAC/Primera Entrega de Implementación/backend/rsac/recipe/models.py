""" Models for recipe app """
from django.utils import timezone
from django.db import models
from start.models import Person


class Recipe(models.Model):
    """ Recipe made by one person """
    author = models.ForeignKey(Person, on_delete=models.CASCADE)
    title = models.CharField(max_length=100)
    description = models.TextField(blank=True, null=True)
    creation_date = models.DateTimeField(default=timezone.now)

    def __str__(self):
        return self.title + ' - ' + self.author.user.get_full_name()


class RecipeImage(models.Model):
    """ Images for recipes """
    recipe = models.ForeignKey(Recipe)
    image = models.ImageField(upload_to='photos/users/recipes',
                              blank=True)
    upload_date = models.DateTimeField(default=timezone.now)

    def __str__(self):
        return self.recipe.__str__() + ': ' + self.image.url


class Ingredient(models.Model):
    """ Ingredients created by providers """
    owner = models.ForeignKey(Person)
    name = models.CharField(max_length=100)
    description = models.TextField(blank=True, null=True)

    def __str__(self):
        return self.name + ' - ' + self.owner.__str__()


class RecipeIngredient(models.Model):
    """ Intermediate table to relation recipes with ingredients """
    recipe = models.ForeignKey(Recipe, on_delete=models.CASCADE)
    ingredient = models.ForeignKey(Ingredient, on_delete=models.CASCADE)
    quantity = models.FloatField(default=1.0)

    def __str__(self):
        return self.recipe.title + ' - ' + self.ingredient.name
