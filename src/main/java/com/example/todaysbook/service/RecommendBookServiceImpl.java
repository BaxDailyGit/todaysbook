package com.example.todaysbook.service;

import com.example.todaysbook.domain.dto.RecommendBookDto;
import com.example.todaysbook.domain.dto.ReviewRequestDto;
import com.example.todaysbook.domain.dto.SimpleReview;
import com.example.todaysbook.repository.RecommendBookMapper;
import lombok.RequiredArgsConstructor;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecommendBookServiceImpl implements RecommendBookService {

    private final RecommendBookMapper recommendBookMapper;
    @Override
    public void GenerateRecommendBookList(List<SimpleReview> reviews) throws TasteException, IOException {

        String filePath = "src/main/resources/data/rating.csv";

        fileWrite(filePath, reviews);

        File dataFile = new File(filePath);
        DataModel model = new FileDataModel(dataFile);

        TanimotoCoefficientSimilarity similarity = new TanimotoCoefficientSimilarity(model);
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);

        List<Map<String, Object>> items = new ArrayList<>();

        LongPrimitiveIterator itemIDs = model.getItemIDs();
        while (itemIDs.hasNext()) {
            long currentItemID = itemIDs.nextLong();

            List<RecommendedItem> recommendedItems = recommender.mostSimilarItems(currentItemID, 5);

            for (RecommendedItem recommendedItem : recommendedItems) {
                Map<String, Object> item = new HashMap<>();

                item.put("bookId", currentItemID);
                item.put("recommendBookId", recommendedItem.getItemID());
                item.put("similarityScore", recommendedItem.getValue());

                items.add(item);
            }
        }

        recommendBookMapper.truncateRecommendBook();
        recommendBookMapper.insertRecommendBookInfo(items);
    }

    @Override
    public List<RecommendBookDto> getRecommendBooks(Long bookId) {

        return recommendBookMapper.getRecommendBooks(bookId);
    }

    private void fileWrite(String filePath, List<SimpleReview> reviews) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false));

        for(SimpleReview review : reviews) {

            bw.write(review.getUserId()+",");
            bw.write(review.getBookId()+",");
            bw.write(String.valueOf(review.getScore()));
            bw.write("\r\n");
        }

        bw.close();
    }
}