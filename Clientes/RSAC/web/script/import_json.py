import os
import sys
import django
import json

# SET MAIN PROYECT
os.chdir('../')
sys.path.append(os.getcwd())
os.environ['DJANGO_SETTINGS_MODULE'] = "product_management.settings"
django.setup()

# UPDLOAD JSON
with open('../products.json') as json_data:
    json_products = json.load(json_data) #34406 upps! id_code 76337

# IMPORT MODELS
from products.models import Category, Trademark,\
TrademarkCategory, Family, Product

# DELETE OLD REGISTERS FROM DB
models = [Category, Trademark, TrademarkCategory, Family, Product]
[mod.objects.all().delete() for mod in models]

def persistence_model(model, args):
    temp = model.objects.filter(**args)
    if temp.exists():
        temp = temp.last()
    else:
        temp = model.objects.create(**args)
    return temp

# ITERATE JSON
for product in json_products:
    cat = persistence_model(Category, {'name': product['DE_CATE']})
    trd = persistence_model(Trademark, {'name': product['DE_EQUI']})
    trd_cat = persistence_model(TrademarkCategory,
                        {'category': cat, 'trademark': trd})

    fam = persistence_model(Family, {'name': product['DE_FAMI'],
                                     'trademark_id': trd.id})

    id_item = str(product['ID_ITEM'])
    prod = persistence_model(Product,{'name': product['DE_ITEM'],
                                      'id_item': id_item})
    # TO CHECK TOTAL PRODUCTS
    prod.family = fam
    prod.save()
