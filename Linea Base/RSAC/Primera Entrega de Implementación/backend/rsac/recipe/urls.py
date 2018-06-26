""" URLs for recipe app"""
from django.conf import settings
from django.conf.urls import url
from django.conf.urls.static import static
from recipe.views import RecipeAPI, RecipeImageAPI, IngredientAPI\
, SearchIngredientView

app_name = 'recipe'

urlpatterns = [
    url(r'^recipe-api/$', RecipeAPI.as_view({'get': 'list', 'post': 'create'})),
    url(r'^recipe-api/(?P<id>[0-9]+)/$',
        RecipeAPI.as_view({'get': 'retrieve', 'patch': 'partial_update',
                           'delete': 'destroy'})),
    url(r'^recipe-image-api/$',
        RecipeImageAPI.as_view({'post': 'create'})),
    url(r'^recipe-image-api/(?P<recipe__id>[0-9]+)/$',
        RecipeImageAPI.as_view({'get': 'retrieve', 'patch': 'partial_update'})),
    url(r'^ingredient-api/$', IngredientAPI.as_view({'get': 'list',
                                                     'post': 'create'})),
    url(r'^ingredient-api/(?P<id>[0-9]+)/$',
        IngredientAPI.as_view({'get': 'retrieve', 'patch': 'partial_update',
                               'delete': 'destroy'})),

    # WEB
    url(r'^search_ingredient/', SearchIngredientView.as_view()),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
