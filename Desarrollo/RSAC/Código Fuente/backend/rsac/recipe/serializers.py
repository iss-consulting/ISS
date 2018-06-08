""" Serializers for recipe app """
from recipe.models import Recipe, RecipeIngredient, Ingredient, RecipeImage
from rsac import settings
from rest_framework.fields import SerializerMethodField
from rest_framework.serializers import ModelSerializer, DateTimeField
from drf_extra_fields.fields import Base64ImageField
from start.serializers import PersonSerializer


class IngredientSerializer(ModelSerializer):
    """ Serializer for Ingredient model """

    class Meta:
        """ Meta class for answer serializer """
        model = Ingredient
        fields = '__all__'
        extra_kwargs = {'id': {'read_only': True, 'required': False}}

    def __init__(self, *args, **kwargs):
        super(IngredientSerializer, self).__init__(*args, **kwargs)
        if self.context['request'].method == 'GET':
            self.fields['owner'] = PersonSerializer(read_only=True,
                                                    context=kwargs['context'])
        if self.context['request'].method == 'PATCH':
            self.fields['owner'] = PersonSerializer(context=kwargs['context'])


class RecipeSerializer(ModelSerializer):
    """ Serializer for Recipe model """
    recipe_image = SerializerMethodField()
    list_ingredients = SerializerMethodField()
    creation_date = DateTimeField(read_only=True, format="%Y-%m-%d %H:%M:%S")

    class Meta:
        """ Meta class for recipe serializer """
        model = Recipe
        fields = ['id', 'author', 'title', 'description', 'creation_date',
                  'recipe_image', 'list_ingredients']
        extra_kwargs = {'id': {'read_only': True, 'required': False}}

    def __init__(self, *args, **kwargs):
        super(RecipeSerializer, self).__init__(*args, **kwargs)
        if self.context['request'].method == 'GET':
            self.fields['author'] = PersonSerializer(read_only=True,
                                                     context=kwargs['context'])
        if self.context['request'].method == 'PATCH':
            self.fields['author'] = PersonSerializer(context=kwargs['context'])

    def create(self, validated_data):
        """ Create method for the serializer """
        recipe = Recipe.objects.create(**validated_data)
        recipe.save()
        if 'ingredients' in self.context['request'].data:
            for item in self.context['request'].data['ingredients']:
                ingredient = Ingredient.objects.get(id=item)
                recipe_ingredient = (RecipeIngredient.objects
                                     .create(recipe=recipe,
                                             ingredient=ingredient))
                recipe_ingredient.save()
        return recipe

    def get_recipe_image(self, obj):
        """ Method to obtain an image for a recipe """
        recipe_image = 'Sin imagen'
        if RecipeImage.objects.filter(recipe=obj).exists():
            recipe_image = (RecipeImage.objects.filter(recipe=obj)
                            .order_by('-upload_date').first())
            recipe_image = (settings.IMAGE_HOST + recipe_image.image.url)
        return recipe_image

    def get_list_ingredients(self, obj):
        """ Method to obtain the list of ingredients asociated to a recipe """
        ingredient_ids = (RecipeIngredient.objects.filter(recipe=obj)
                           .values('ingredient__id'))
        ingredients = Ingredient.objects.filter(id__in=ingredient_ids)
        return IngredientSerializer(ingredients, many=True,
                                    context={'request': self
                                    .context['request']}).data


class RecipeImageSerializer(ModelSerializer):
    """ Serializer for Image of recipes """
    image = Base64ImageField()
    url_image = SerializerMethodField()
    upload_date = DateTimeField(read_only=True, format="%Y-%m-%d %H:%M:%S")
    extra_kwargs = {'id': {'read_only': True, 'required': False}}

    def to_representation(self, obj):
        ret = super(RecipeImageSerializer, self).to_representation(obj)
        if self.context['request'].method == 'POST':
            ret.pop('id')
            ret.pop('recipe')
            ret.pop('image')
            ret.pop('upload_date')
        return ret

    class Meta:
        """ Meta class for ImageDetail serializer """
        model = RecipeImage
        fields = '__all__'

    def update(self, instance, validated_data):
        """ Function to update user information """
        instance.image.delete(save=False)
        instance.image = validated_data.get('image', instance.image)
        instance.save()
        return instance

    def get_url_image(self, obj):
        """ Function to obtain the url for an image """
        return settings.SERVER_HOST + obj.image.url
