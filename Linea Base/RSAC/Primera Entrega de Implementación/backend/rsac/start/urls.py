""" URLs for start app"""
from django.conf import settings
from django.conf.urls import url
from django.conf.urls.static import static
from start.views import RegisterUserAPI, AuthenticateUserAPI, PersonAPI, \
    PersonImageAPI

app_name = 'start'

urlpatterns = [
    url(r'^register-api/$', RegisterUserAPI.as_view()),
    url(r'^login-api/$', AuthenticateUserAPI.as_view()),
    url(r'^person-api/$', PersonAPI.as_view({'get': 'list'})),
    url(r'^person-api/(?P<id>[0-9]+)/$',
        PersonAPI.as_view({'get': 'retrieve', 'patch': 'partial_update'})),
    url(r'^person-image-api/$',
        PersonImageAPI.as_view({'post': 'create'})),
    url(r'^person-image-api/(?P<person__id>[0-9]+)/$',
        PersonImageAPI.as_view({'get': 'retrieve', 'patch': 'partial_update'})),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
