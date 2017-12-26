package com.hrawat.paginglibrary.endlessScroll;

/**
 * Created by hrawat on 12/26/2017.
 */
interface RedditPostRepository {

    Listing<RedditPost> postsofSubReddit(String subReddit, int pagesize);

    enum Type {
        IN_MEMORY_BY_ITEM,
        IN_MEMORY_BY_PAGE,
        DB
    }
}


