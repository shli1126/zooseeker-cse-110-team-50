package com.example.cse110_team49;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.nio.json.JSONImporter;



public class ZooDataItem {
    public static class VertexInfo {
        public static enum Kind {
            // The SerializedName annotation tells GSON how to convert
            // from the strings in our JSON to this Enum.
            @SerializedName("gate") GATE,
            @SerializedName("exhibit") EXHIBIT,
            @SerializedName("intersection") INTERSECTION
        }

        public String id;
        public Kind kind;
        public String name;
        public List<String> tags;
    }

    public static class EdgeInfo {
        public String id;
        public String street;
    }

    public static Map<String, ZooDataItem.VertexInfo> loadVertexInfoJSON(Context context, String path) {
        try {
            InputStream inputStream = context.getAssets().open(path);

            Reader reader = new InputStreamReader(inputStream);

            Gson gson = new Gson();
            Type type = new TypeToken<List<VertexInfo>>() {
            }.getType();
            List<VertexInfo> zooData = gson.fromJson(reader, type);

            Map<String, VertexInfo> indexedZooData = zooData
                    .stream()
                    .collect(Collectors.toMap(v -> v.id, datum -> datum));

            reader.close();
            return indexedZooData;
        }
        catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static Map<String, ZooDataItem.EdgeInfo> loadEdgeInfoJSON(Context context, String path) {
        try {
            InputStream inputStream = context.getAssets().open(path);

            Reader reader = new InputStreamReader(inputStream);

            Gson gson = new Gson();
            Type type = new TypeToken<List<ZooDataItem.EdgeInfo>>() {
            }.getType();
            List<ZooDataItem.EdgeInfo> zooData = gson.fromJson(reader, type);

            Map<String, ZooDataItem.EdgeInfo> indexedZooData = zooData
                    .stream()
                    .collect(Collectors.toMap(v -> v.id, datum -> datum));

            return indexedZooData;
        }
        catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static Graph<String, IdentifiedWeightedEdge> loadZooGraphJSON(Context context, String path) {
        try {
            InputStream inputStream = context.getAssets().open(path);
            Graph<String, IdentifiedWeightedEdge> g = new DefaultUndirectedWeightedGraph<>(IdentifiedWeightedEdge.class);


            JSONImporter<String, IdentifiedWeightedEdge> importer = new JSONImporter<>();

            importer.setVertexFactory(v -> v);

            importer.addEdgeAttributeConsumer(IdentifiedWeightedEdge::attributeConsumer);


            Reader reader = new InputStreamReader(inputStream);

            importer.importGraph(g, reader);

            return g;
        }
        catch (IOException e) {
            e.printStackTrace();

            return null;
        }

    }

}