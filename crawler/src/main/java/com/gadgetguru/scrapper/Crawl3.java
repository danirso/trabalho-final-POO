package com.gadgetguru.scrapper;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class Crawl3 {

    private String searchTerm;

    public void crawlForProduct(String searchTerm) {
        this.searchTerm = searchTerm.replaceAll(" ", "-").replaceAll("[^a-zA-Z0-9-]", "");
        String url = "https://www.gkinfostore.com.br/buscar?q=" + this.searchTerm;
        crawl3(1, url, new ArrayList<>());
    }


    private static void crawl3(int level, String url, ArrayList<String> visited) {
        if (level <= 5) {
            Document doc = request(url, visited);
            if (doc != null) {
                // Obtém links da classe 'productCard'
                Elements productLinks = doc.select(".produto-sobrepor > a[href]");
                for (Element productLink : productLinks) {
                    String productUrl = productLink.absUrl("href");
                    if (!visited.contains(productUrl)) {
                        crawl3(level + 1, productUrl, visited);
                        return;  // Interrompe a busca após encontrar um link
                    }
                }
    
                Elements priceElements = doc.select(".desconto-a-vista");
                if (!priceElements.isEmpty()) {
                    String price = priceElements.text();
    
                    // Obtém o nome do produto
                    Element productNameElement = doc.selectFirst(".nome-produto.cor-secundaria");
                    String productName = productNameElement != null ? productNameElement.text() : "Nome do Produto Não Encontrado";
    
                    // Obtém a URL da imagem do produto
                    Element productImageElement = doc.selectFirst(".imagem-produto.has-zoom");
                    String productImageUrl = productImageElement != null ? productImageElement.absUrl("src") : "URL da Imagem Não Encontrada";
    
                    System.out.println("Nome do Produto: " + productName);
                    System.out.println("Link do Produto: " + url);
                    System.out.println("Preço do Produto: " + price);
                    System.out.println("URL da Imagem: " + productImageUrl);
                    return;  // Interrompe a busca após encontrar o preço
                } else {
                    System.out.println("Elemento com classe 'price-value' não encontrado no link: " + url);
                }
            }
        }
    }

    private static Document request(String url, ArrayList<String> v) {
        try {
            Document doc = Jsoup.connect(url).get();
            v.add(url);
            return doc;
        } catch (IOException e) {
            System.out.println("Erro de IO ao acessar o link: " + url);
            return null;
        }
    }
}
