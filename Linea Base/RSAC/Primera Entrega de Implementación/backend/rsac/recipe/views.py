""" Views for the recipe app """
from rest_framework.authentication import SessionAuthentication, \
    BasicAuthentication
from rest_framework.permissions import AllowAny
from rest_framework.viewsets import ModelViewSet

from recipe.models import Recipe, RecipeImage, Ingredient
from recipe.serializers import RecipeSerializer, RecipeImageSerializer, \
    IngredientSerializer
from django.shortcuts import render
from django.views import View

class CsrfExemptSessionAuthentication(SessionAuthentication):
    """ Class View used to exempt csrf for a request"""

    def enforce_csrf(self, request):
        """ Overwriting method enforce_csrf """
        return  # To not perform the csrf check previously happening


class RecipeAPI(ModelViewSet):
    """ API view to manage recipes """
    authentication_classes = [CsrfExemptSessionAuthentication,
                              BasicAuthentication]
    permission_classes = [AllowAny]
    serializer_class = RecipeSerializer
    lookup_field = 'id'

    def get_queryset(self):
        """ Redefinition of queryset for the view """
        if 'person_id' in self.request.GET:
            queryset = (Recipe.objects
                        .filter(author__id=self.request.GET['person_id'])
                        .order_by('creation_date'))
        else:
            queryset = Recipe.objects.all().order_by('creation_date')
        return queryset


class RecipeImageAPI(ModelViewSet):
    """ API view to manage images for recipes """
    authentication_classes = [CsrfExemptSessionAuthentication,
                              BasicAuthentication]
    permission_classes = [AllowAny]
    serializer_class = RecipeImageSerializer
    queryset = RecipeImage.objects.all()
    lookup_field = 'recipe__id'


class IngredientAPI(ModelViewSet):
    """ API view to manage ingredients for a recipe """
    authentication_classes = [CsrfExemptSessionAuthentication,
                              BasicAuthentication]
    permission_classes = [AllowAny]
    serializer_class = IngredientSerializer
    lookup_field = 'id'

    def get_queryset(self):
        """ Redefinition of queryset for the view """
        if 'provider_id' in self.request.GET:
            queryset = (Ingredient.objects
                        .filter(creator__id=self.request.GET['provider_id'])
                        .order_by('name'))
        else:
            queryset = Ingredient.objects.all().order_by('name')
        return queryset


class SearchIngredientView(View):
    """ Search and filter ingredients for dispend """
    def get(self, request):
        return render(request, 'recipe/SearchIngredients.html')
