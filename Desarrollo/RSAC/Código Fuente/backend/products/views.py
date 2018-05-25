from django.http import HttpResponse
from django.http import JsonResponse
from django.shortcuts import render
from django.views import View
from products.models import Product, Category, Trademark, Family
from django.db.models import Q


class Index(View):
    # Index
    def get(self, request):
        return render(request, 'products/index.html')

class ProductsAPI(View):
    # Management de products to search
    def get(self, request):
        name = request.GET.get('name', '')
        family_id = request.GET.get('family_id', '')
        if not name and not family_id:
            return JsonResponse([])
        # create query

        

        query = Q(id__gt=0)
        if name:
            words = name.split(' ')
            words = [word for word in words if word]
            for word in words:
                query = query & Q(name__icontains=' '+word) |\
                Q(name__icontains=' '+word+' ') | Q(name__endswith=word) |\
                Q(name__startswith=word)
        if family_id:
            query = query & Q(family_id=family_id)
        # call bd
        products = list(Product.objects\
                .filter(
                    query
                ).values()
            )
        return JsonResponse(products, safe=False)

class CategoryAPI(View):
    def get(self, request):
        categories = list(Category.objects.all().order_by('name').values())
        return JsonResponse(categories, safe=False)

class TrademarkAPI(View):
    def get(self, request):
        if 'category_id' not in request.GET:
            return JsonResponse([])
        trade = list(Trademark.objects\
            .filter(
                trademarkcategory__category__id=request.GET['category_id']
                )\
            .values()
        )
        return JsonResponse(trade, safe=False)

class FamilyAPI(View):
    def get(self, request):
        if not 'trademark_name' in request.GET:
            return JsonResponse([])
        trademark__name = request.GET['trademark_name']
        families = list(Family.objects\
            .filter(
                trademark__name=trademark__name
            )\
            .values()
        )
        return JsonResponse(families, safe=False)
