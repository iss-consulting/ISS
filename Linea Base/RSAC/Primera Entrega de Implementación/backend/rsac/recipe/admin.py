""" Admin for recipe app """
from django.contrib import admin
from recipe.models import Recipe, Ingredient, RecipeImage, RecipeIngredient

admin.site.register(Recipe)
admin.site.register(RecipeImage)
admin.site.register(Ingredient)
admin.site.register(RecipeIngredient)
