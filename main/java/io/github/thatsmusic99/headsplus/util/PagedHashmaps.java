package io.github.thatsmusic99.headsplus.util;

import java.util.*;

public class PagedHashmaps {
    private LinkedHashMap<?, ?> hs;
    private int pages;
    private int contents;
    private int currentPage;

    public PagedHashmaps(LinkedHashMap<?, ?> hs, int contentsPerPage) {
        this.hs = hs;
        int pages = 1;
        int bls = hs.size();
        while (bls > contentsPerPage) {
            pages++;
            bls = bls - contentsPerPage;
        }
        this.pages = pages;
        this.contents = hs.size();
        this.currentPage = 1;
    }

    public int getTotalPages() {
        return pages;
    }

    public int getTotalContents() {
        return contents;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public LinkedHashMap<?, ?> getHs() {
        return hs;
    }

    public LinkedHashMap<Object, Object> getContentsInPage(int page) {
        int sIndex = (page - 1) * getTotalContents();
        int eIndex = getTotalContents() + sIndex;
        if (eIndex > getHs().size()) {
            eIndex = getHs().size();
        }
        setPage(page);
        return splitHashmap(getHs(), sIndex, eIndex);
    }

    private static LinkedHashMap<Object, Object> splitHashmap(HashMap<?, ?> passedMap, int sIndex, int eIndex) {
        List<?> mapKeys = new ArrayList<>(passedMap.keySet());
        List<?> mapValues = new ArrayList<>(passedMap.values());
        mapKeys = mapKeys.subList(sIndex, eIndex);
        mapValues = mapValues.subList(sIndex, eIndex);

        LinkedHashMap<Object, Object> sortedMap =
                new LinkedHashMap<>();

        for (Object val : mapValues) {
            Iterator<?> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                Object comp1 = passedMap.get(key);

                if (comp1.equals(val)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    private void setPage(int page) {
        this.currentPage = page;
    }
}
