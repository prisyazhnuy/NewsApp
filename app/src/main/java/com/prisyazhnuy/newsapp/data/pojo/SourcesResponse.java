package com.prisyazhnuy.newsapp.data.pojo;

import java.util.List;

/**
 * max.pr on 04.03.2018.
 */

public class SourcesResponse extends BaseResponse {

    private List<Source> sources;

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
