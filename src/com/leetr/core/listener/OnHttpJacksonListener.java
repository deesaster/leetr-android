package com.leetr.core.listener;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-07
 * Time: 11:56 AM
 */
public interface OnHttpJacksonListener {
    public void onJacksonLoaded(ObjectMapper mapper, Object obj);

    public void onJacksonLoadFailed();
}
