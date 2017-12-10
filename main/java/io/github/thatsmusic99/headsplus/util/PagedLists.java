package io.github.thatsmusic99.headsplus.util;

import java.util.List;

public class PagedLists {

    // Util by Thatsmusic99

    private List<?> list;
    private int pages;
    private int contents;
    private int currentPage;

    public PagedLists(List<?> list, int contentsPerPage) {
        this.list = list;
        int pages = 1;
        int bls = list.size();
        while (bls > contentsPerPage) {
            pages++;
            bls = bls - contentsPerPage;
        }
        this.pages = pages;
        this.contents = list.size();
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

    public List<?> getList() {
        return list;
    }

    public List<?> getContentsInPage(int page) {
        int sIndex = (page - 1) * getTotalContents();
        int eIndex = getTotalContents() + sIndex;
        if (eIndex > getList().size()) {
            eIndex = getList().size();
        }
        setPage(page);
        return getList().subList(sIndex, eIndex);
    }

    private void setPage(int page) {
        this.currentPage = page;
    }

}
