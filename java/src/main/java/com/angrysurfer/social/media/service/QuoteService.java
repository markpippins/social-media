package com.angrysurfer.social.media.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QuoteService {

    public List<String> getQuotes() {

        List<String> quotes = Collections.emptyList();

        try {
            Document doc = Jsoup.connect("https://spetsnaz.su/~tz/skinnypuppsum/").get();
            return doc.select("p").stream().map(el -> el.text()).collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return quotes;
    }

}
