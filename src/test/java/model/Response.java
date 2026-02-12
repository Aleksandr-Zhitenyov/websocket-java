package model;

public class Response {
    public String topic;
    public String type;
    public String subject;
    public Data data;

    public Response() {
    }

    public String getTopic() {
        return topic;
    }

    public String getType() {
        return type;
    }

    public String getSubject() {
        return subject;
    }

    public Data getData() {
        return data;
    }

    class Data {
        public String bestAsk;
        public String bestAskSize;
        public String bestBid;
        public String bestBidSize;
        public String price;
        public String sequence;
        public String size;
        public long time;

        public Data() {
        }

        public String getBestAsk() {
            return bestAsk;
        }

        public String getBestAskSize() {
            return bestAskSize;
        }

        public String getBestBid() {
            return bestBid;
        }

        public String getBestBidSize() {
            return bestBidSize;
        }

        public String getPrice() {
            return price;
        }

        public String getSequence() {
            return sequence;
        }

        public String getSize() {
            return size;
        }

        public long getTime() {
            return time;
        }
    }
}
