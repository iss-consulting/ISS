from django.db import models

class Category(models.Model):
    name = models.CharField(max_length=100)

class Trademark(models.Model):
    name = models.CharField(max_length=100)

class TrademarkCategory(models.Model):
    # Una marca puede pertenecer a más de una categoría
    category = models.ForeignKey(Category, on_delete=models.CASCADE,
    blank=True, null=True)
    trademark = models.ForeignKey(Trademark, on_delete=models.CASCADE,
    blank=True, null=True)

class Family(models.Model):
    name = models.CharField(max_length=100)
    trademark = models.ForeignKey(Trademark, on_delete=models.CASCADE,
    blank=True, null=True)

class Product(models.Model):
    id_item = models.CharField(max_length=15)
    name = models.CharField(max_length=100)
    family = models.ForeignKey(Family, on_delete=models.CASCADE,
    blank=True, null=True)