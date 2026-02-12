package model;

public class Response {
    public String topic;
    public String type;
    public String subject;
    public Data data;

    class Data {
        public String bestAsk;
        public String bestAskSize;
        public String bestBid;
        public String bestBidSize;
        public String price;
        public String sequence;
        public String size;
        public long time;
    }
}
