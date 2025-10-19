package com.holic.java.mcp.repository.impl;

import com.holic.java.mcp.repository.VectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.DocumentEmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PgVectorRepository implements VectorRepository {

//    private final VectorStore vectorStore;
//    private final DocumentEmbeddingModel documentEmbeddingModel;

    @Override
    public void saveVector(String id, float[] vector, String metadata) {
//        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
//        documentEmbeddingModel.
//        vectorStore.accept();
    }

    @Override
    public List<String> searchNearest(float[] vector, int k) {

        return List.of();
    }
}
