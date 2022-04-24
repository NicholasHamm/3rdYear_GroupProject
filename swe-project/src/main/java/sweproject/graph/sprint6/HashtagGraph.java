package sweproject.graph.sprint6;

import sweproject.GetProperties;
import sweproject.graph.sprint3.Edge;
import sweproject.graph.sprint3.Graph;
import sweproject.graph.sprint4.Evangelists;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class HashtagGraph {

    private final Map<String, Set<String>> list = new HashMap<>();
    private final Map<String, Map<String, Set<String>>> referenceList = new HashMap<>();

    // Inverted
    private final Map<String, Set<String>> invertedList = new HashMap<>();
    private final Map<String, Map<String, Set<String>>> invertedReferenceList = new HashMap<>();

    public void addSourceNode(String source) {
        list.computeIfAbsent(source, key -> new TreeSet<>());
    }
    public void addInvertedSourceNode(String source) { invertedList.computeIfAbsent(source, key -> new TreeSet<>());}

    public void addReference(HashtagReference e) {
       referenceList.get(e.hashtag).computeIfAbsent(e.split, key -> new TreeSet<>());
       referenceList.get(e.hashtag).get(e.split).add(e.ref);
    }
    public void addInvertedReference(HashtagReference e) {
        invertedReferenceList.get(e.split).computeIfAbsent(e.hashtag, key -> new TreeSet<>());
        invertedReferenceList.get(e.split).get(e.hashtag).add(e.ref);
    }

    public void addArc(String hashtag, String split, String ref) {
        if(referenceList.containsKey(hashtag)) {
            if(referenceList.get(hashtag).containsKey(split)) {
                addToReferenceList(new HashtagReference(hashtag, split, ref));
            }
            else addToReferenceList(new HashtagReference(hashtag, split, ref));
        }
        else addToReferenceList(new HashtagReference(hashtag, split, ref));
    }

    private void addToReferenceList(HashtagReference e) {
        addSourceNode(e.hashtag);
        list.get(e.hashtag).add(e.split);

        //inverted map
        addInvertedSourceNode(e.split);
        invertedList.get(e.split).add(e.hashtag);

        setReference(new HashtagReference(e.hashtag, e.split, e.ref));
    }

    private void setReference(HashtagReference e) {
        referenceList.computeIfAbsent(e.hashtag, key -> new HashMap<>());
        addReference(e);

        //inverted weighted map
        invertedReferenceList.computeIfAbsent(e.split, key -> new HashMap<>());
        addInvertedReference(e);
    }

    public  Map<String, Map<String, Set<String>>> getEdges() {
        return referenceList;
    }

    public  Map<String, Map<String, Set<String>>>  invert() {
        return invertedReferenceList;
    }

}
