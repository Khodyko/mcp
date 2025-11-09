package com.holic.java.mcp.repository.impl;

import com.holic.java.mcp.dto.ProductVectorDto;
import com.holic.java.mcp.repository.ProductVectorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductVectorRepositoryImpl implements ProductVectorRepository {

    private final VectorStore vectorStore;

    @Override
    public void saveVector(List<ProductVectorDto> vectorMetadataDto) {
        List<Document> documents = new ArrayList<>();
        Document document;
        for (ProductVectorDto dto : vectorMetadataDto) {
            document = new Document(dto.getDescription(), dto.toMap());
            documents.add(document);
        }
        try {
            vectorStore.add(documents);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ProductVectorDto> searchProductsByDescription(String description, int limit) {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(description)
                .topK(limit)
                .similarityThreshold(0.7F)
                .build();

        List<Document> documents= vectorStore.similaritySearch(description);
        List<ProductVectorDto> productVectorDtos = new ArrayList<>();
        for(Document document : documents){
            productVectorDtos.add(new  ProductVectorDto(document));
        }
        return productVectorDtos;
    }
}
