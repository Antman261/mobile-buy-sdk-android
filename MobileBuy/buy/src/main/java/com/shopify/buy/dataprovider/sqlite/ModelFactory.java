/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Shopify Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.shopify.buy.dataprovider.sqlite;

import com.shopify.buy.model.Cart;
import com.shopify.buy.model.CartLineItem;
import com.shopify.buy.model.Collection;
import com.shopify.buy.model.Image;
import com.shopify.buy.model.Option;
import com.shopify.buy.model.OptionValue;
import com.shopify.buy.model.Product;
import com.shopify.buy.model.ProductVariant;
import com.shopify.buy.model.Shop;
import com.shopify.buy.model.internal.CollectionImage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ModelFactory {

    static class DBShop extends Shop {

        public DBShop(String name, String city, String province, String country, String contactEmail, String currency, String domain, String url, String myshopifyDomain, String description, List<String> shipsToCountries, String moneyFormat, long publishedProductsCount) {
            this.name = name;
            this.city = city;
            this.province = province;
            this.country = country;
            this.contactEmail = contactEmail;
            this.currency = currency;
            this.domain = domain;
            this.url = url;
            this.myshopifyDomain = myshopifyDomain;
            this.description = description;
            this.shipsToCountries = shipsToCountries;
            this.moneyFormat = moneyFormat;
            this.publishedProductsCount = publishedProductsCount;
        }

    }

    static class DBCollection extends Collection {

        DBCollection(String title, String htmlDescription, String handle, boolean published, String collectionId, Date createdAtDate, Date updatedAtDate, Date publishedAtDate, String imageCreatedAt, String imageSrc) {
            this.title = title;
            this.htmlDescription = htmlDescription;
            this.handle = handle;
            this.published = published;
            this.collectionId = collectionId;
            this.createdAtDate = createdAtDate;
            this.updatedAtDate = updatedAtDate;
            this.publishedAtDate = publishedAtDate;

            if (imageCreatedAt != null && imageSrc != null) {
                this.image = new CollectionImage(imageCreatedAt, imageSrc);
            }
        }

    }

    static class DBOption extends Option {

        public DBOption(long id, String name, int position, String productId) {
            this.id = id;
            this.name = name;
            this.position = position;
            this.productId = productId;
        }

    }

    static class DBOptionValue extends OptionValue {

        private final String variantId;

        public DBOptionValue(String optionId, String name, String value, String variantId) {
            this.optionId = optionId;
            this.name = name;
            this.value = value;
            this.variantId = variantId;
        }

        public String getVariantId() {
            return variantId;
        }
    }

    static class DBProduct extends Product {

        public DBProduct(String productId, String channelId, String title, String handle, String bodyHtml, Date publishedAtDate, Date createdAtDate, Date updatedAtDate, String vendor, String productType, List<Image> images, List<ProductVariant> variants, List<Option> options, Set<String> tagSet, boolean available, boolean published) {
            this.productId = productId;
            this.channelId = channelId;
            this.title = title;
            this.handle = handle;
            this.bodyHtml = bodyHtml;
            this.publishedAtDate = publishedAtDate;
            this.createdAtDate = createdAtDate;
            this.updatedAtDate = updatedAtDate;
            this.vendor = vendor;
            this.productType = productType;
            this.images = images;
            this.variants = variants;
            this.options = options;
            this.tagSet = tagSet;
            this.available = available;
            this.published = published;
        }
    }

    static class DBProductVariant extends ProductVariant {

        public DBProductVariant(long id, String title, String price, List<DBOptionValue> optionValues, long grams, String compareAtPrice, String sku, boolean requiresShipping, boolean taxable, int position, long productId, String productTitle, Date createdAtDate, Date updatedAtDate, boolean available, String imageUrl) {
            super.id = id;
            this.title = title;
            this.price = price;
            this.grams = grams;
            this.compareAtPrice = compareAtPrice;
            this.sku = sku;
            this.requiresShipping = requiresShipping;
            this.taxable = taxable;
            this.position = position;
            this.productId = productId;
            this.productTitle = productTitle;
            this.createdAtDate = createdAtDate;
            this.updatedAtDate = updatedAtDate;
            this.available = available;
            this.imageUrl = imageUrl;

            this.optionValues = new ArrayList<>();
            this.optionValues.addAll(optionValues);
        }

    }

    static class DBImage extends Image {

        public DBImage(String createdAt, int position, String updatedAt, long productId, List<Long> variantIds, String src) {
            this.createdAt = createdAt;
            this.position = position;
            this.updatedAt = updatedAt;
            this.productId = productId;
            this.variantIds = variantIds;
            this.src = src;
        }

    }

    static class DBCartLineItem extends CartLineItem {

        public DBCartLineItem(ProductVariant variant, long quantity, String id, String price, boolean requiresShipping, String variantId, String title, String productId, String variantTitle, String linePrice, String compareAtPrice, String sku, boolean taxable, long grams, String fulfillmentService, Map<String, String> properties) {
            super(variant);
            this.quantity = quantity;
            this.id = id;
            this.price = price;
            this.requiresShipping = requiresShipping;
            this.variantId = Long.parseLong(variantId);
            this.title = title;
            this.productId = productId;
            this.variantTitle = variantTitle;
            this.linePrice = linePrice;
            this.compareAtPrice = compareAtPrice;
            this.sku = sku;
            this.taxable = taxable;
            this.grams = grams;
            this.fulfillmentService = fulfillmentService;
            this.properties = properties;
        }

    }

    static class DBCart extends Cart {

        public DBCart(List<CartLineItem> lineItems, Set<ProductVariant> productVariants) {
            super();
            this.lineItems.addAll(lineItems);
            this.productVariants.addAll(productVariants);
        }
    }

}