package com.holic.java.mcp.repository;

import java.util.List;

import com.holic.java.mcp.dto.ProductVectorDto;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Repository;

/**
 * Заглушка репозитория для работы с Pgvector/векторной БД.
 */
@Repository
public interface ProductVectorRepository {

    void saveVector(List<ProductVectorDto> vectorMetadataDto);

    List<Document> searchProductsByDescription(String description, int limit);
}
