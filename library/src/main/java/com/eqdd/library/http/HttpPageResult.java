package com.eqdd.library.http;

import java.util.List;

/**
 * @author吕志豪 .
 * @date 18-1-8  上午11:39.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class HttpPageResult<T> {

    /**
     * msg : 成功
     * status : 200
     * items : {"content":[{"id":2,"name":"1","career":"1","sex":"1","birth":"1992-03-15","address":"1","race":"1","createTime":null,"poster":"http://192.168.1.137:8073/friend/c4ca41515378451743.jpg","createUserId":1,"evaluate":null},{"id":3,"name":"2","career":"2","sex":"2","birth":"1992-03-16","address":"2","race":"2","createTime":"2018-01-08","poster":"http://192.168.1.137:8073/friend/c81e71515378744083.jpg","createUserId":1,"evaluate":null}],"totalPages":2,"totalElements":3,"last":false,"sort":null,"numberOfElements":2,"first":true,"size":2,"number":0}
     */

    private String msg;
    private int status;
    private ItemsBean<T> items;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ItemsBean<T> getItems() {
        return items;
    }

    public void setItems(ItemsBean<T> items) {
        this.items = items;
    }

    public static class ItemsBean<T> {
        /**
         * content : [{"id":2,"name":"1","career":"1","sex":"1","birth":"1992-03-15","address":"1","race":"1","createTime":null,"poster":"http://192.168.1.137:8073/friend/c4ca41515378451743.jpg","createUserId":1,"evaluate":null},{"id":3,"name":"2","career":"2","sex":"2","birth":"1992-03-16","address":"2","race":"2","createTime":"2018-01-08","poster":"http://192.168.1.137:8073/friend/c81e71515378744083.jpg","createUserId":1,"evaluate":null}]
         * totalPages : 2
         * totalElements : 3
         * last : false
         * sort : null
         * numberOfElements : 2
         * first : true
         * size : 2
         * number : 0
         */

        private int totalPages;
        private int totalElements;
        private boolean last;
        private Object sort;
        private int numberOfElements;
        private boolean first;
        private int size;
        private int number;
        private List<T> content;

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public List<T> getContent() {
            return content;
        }

        public void setContent(List<T> content) {
            this.content = content;
        }
    }
}
