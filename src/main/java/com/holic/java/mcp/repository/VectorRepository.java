package com.holic.java.mcp.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Заглушка репозитория для работы с Pgvector/векторной БД.
 */
@Repository
public interface VectorRepository {
    void saveVector(String id, float[] vector, String metadata);
    List<String> searchNearest(float[] vector, int k);
}
