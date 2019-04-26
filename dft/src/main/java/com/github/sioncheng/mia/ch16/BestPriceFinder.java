package com.github.sioncheng.mia.ch16;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class BestPriceFinder {

    public static void main(String[] args) {

        List<Shop> shopList = Arrays.asList(new Shop("BestBuy"),
                new Shop("ebay"),
                new Shop("amazon"),
                new Shop("tmall"),
                new Shop("jd"));

        /*
        List<String> pricesOfiPhoneSE = shopList.parallelStream()
                .map(shop -> String.format("%s : %.2f", shop.getName(), shop.getPrice("iPhoneSE")))
                .collect(Collectors.toList());

        System.out.println(pricesOfiPhoneSE);
         */

        List<CompletableFuture<String>> completableFutures = shopList.stream()
                .map(shop -> CompletableFuture.supplyAsync(()->String.format("%s : %.2f", shop.getName(), shop.getPrice("iPhoneSE"))))
                .collect(Collectors.toList());

        List<String> prices = completableFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        System.out.println(prices);


    }
}
