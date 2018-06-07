from django.urls import path
from . import views

urlpatterns = [
  path('23uy4g32j4vhj23v4j23v4j323b24k23/', views.Index.as_view(), name='index'),
  path('search/', views.ProductsAPI.as_view()),
  path('categories/', views.CategoryAPI.as_view()),
  path('trademarks/', views.TrademarkAPI.as_view()),
  path('families/', views.FamilyAPI.as_view())
]
