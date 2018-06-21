""" Serializers for start app """
from rsac import settings
from django.contrib.auth.models import User
from drf_extra_fields.fields import Base64ImageField
from rest_framework.fields import SerializerMethodField
from rest_framework.serializers import ModelSerializer, DateTimeField
from start.models import Person, PersonImage


class UserSerializer(ModelSerializer):
    """ Serializer for User model """

    class Meta:
        """ Meta class for User serializer """
        model = User
        fields = ['id', 'username', 'first_name', 'last_name', 'email']
        extra_kwargs = {'id': {'read_only': True, 'required': False},
                        'username': {'read_only': True, 'required': False}}


class PersonSerializer(ModelSerializer):
    """ Serializer for Person model """
    person_image = SerializerMethodField()

    class Meta:
        """ Meta class for Person serializer """
        model = Person
        fields = ['id', 'user', 'genre', 'born_date', 'person_image']
        extra_kwargs = {'id': {'read_only': True, 'required': False}}

    def __init__(self, *args, **kwargs):
        super(PersonSerializer, self).__init__(*args, **kwargs)
        if self.context['request'].method == 'GET':
            self.fields['user'] = UserSerializer(read_only=True,
                                                 context=kwargs['context'])
        if self.context['request'].method == 'PATCH':
            self.fields['user'] = UserSerializer(context=kwargs['context'])

    def update(self, instance, validated_data):
        """ Function to update user information """
        if 'user' in validated_data:
            user_data = validated_data.pop('user')
            user = instance.user
            if 'first_name' in user_data:
                if user_data['first_name'] != '':
                    user.first_name = user_data['first_name']
            if 'last_name' in user_data:
                if user_data['last_name'] != '':
                    user.first_name = user_data['last_name']
            if 'email' in user_data:
                if user_data['email'] != '':
                    user.first_name = user_data['email']
            user.save()
        if 'genre' in validated_data:
            instance.genre = validated_data.pop('genre')
        if 'born_date' in validated_data:
            instance.born_date = validated_data.pop('born_date')
        instance.save()
        return instance

    def get_person_image(self, obj):
        """ This method obtain the profile image of a person """
        person_image = 'Sin imagen'
        if PersonImage.objects.filter(person=obj).exists():
            person_image = (PersonImage.objects.filter(person=obj)
                            .order_by('-upload_date').first())
            person_image = (settings.IMAGE_HOST + person_image.image.url)
        return person_image


class PersonImageSerializer(ModelSerializer):
    """ Serializer for Image of user profiles """
    image = Base64ImageField()
    url_image = SerializerMethodField()
    upload_date = DateTimeField(read_only=True, format="%Y-%m-%d %H:%M:%S")
    extra_kwargs = {'id': {'read_only': True, 'required': False}}

    def to_representation(self, obj):
        ret = super(PersonImageSerializer, self).to_representation(obj)
        if self.context['request'].method == 'POST':
            ret.pop('id')
            ret.pop('person')
            ret.pop('image')
            ret.pop('upload_date')
        return ret

    class Meta:
        """ Meta class for ImageDetail serializer """
        model = PersonImage
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
