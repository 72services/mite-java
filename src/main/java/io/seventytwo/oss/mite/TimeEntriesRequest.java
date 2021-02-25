package io.seventytwo.oss.mite;

public class TimeEntriesRequest {

    String userId;
    String customerId;
    String projectId;
    String serviceId;
    String note;
    String at;
    String from;
    String to;
    Boolean billable;
    Boolean locked;
    Boolean tracking;
    String sort;
    String direction;
    Integer limit;
    Integer page;

    public static class Builder {

        final TimeEntriesRequest timeEntriesRequest;

        public Builder() {
            this.timeEntriesRequest = new TimeEntriesRequest();
        }

        public Builder userId(String userid) {
            timeEntriesRequest.userId = userid;
            return this;
        }

        public Builder customerId(String customerId) {
            timeEntriesRequest.customerId = customerId;
            return this;
        }

        public Builder projectId(String projectId) {
            timeEntriesRequest.projectId = projectId;
            return this;
        }

        public Builder serviceId(String serviceId) {
            timeEntriesRequest.serviceId = serviceId;
            return this;
        }

        public Builder note(String note) {
            timeEntriesRequest.note = note;
            return this;
        }

        public Builder at(String at) {
            timeEntriesRequest.at = at;
            return this;
        }

        public Builder from(String from) {
            timeEntriesRequest.from = from;
            return this;
        }

        public Builder to(String to) {
            timeEntriesRequest.to = to;
            return this;
        }

        public Builder billable(boolean billable) {
            timeEntriesRequest.billable = billable;
            return this;
        }

        public Builder locked(boolean locked) {
            timeEntriesRequest.locked = locked;
            return this;
        }

        public Builder tracking(boolean tracking) {
            timeEntriesRequest.tracking = tracking;
            return this;
        }

        public Builder sort(String sort, String direction) {
            timeEntriesRequest.sort = sort;
            timeEntriesRequest.direction = direction;
            return this;
        }

        public Builder limit(int limit) {
            timeEntriesRequest.limit = limit;
            return this;
        }

        public Builder page(int page) {
            timeEntriesRequest.page = page;
            return this;
        }

        public TimeEntriesRequest build() {
            return timeEntriesRequest;
        }
    }
}
