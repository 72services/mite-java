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

        public Builder withUserid(String userid) {
            timeEntriesRequest.userId = userid;
            return this;
        }

        public Builder withCustomerId(String customerId) {
            timeEntriesRequest.customerId = customerId;
            return this;
        }

        public Builder withProjectId(String projectId) {
            timeEntriesRequest.projectId = projectId;
            return this;
        }

        public Builder withServiceId(String serviceId) {
            timeEntriesRequest.serviceId = serviceId;
            return this;
        }

        public Builder withNote(String note) {
            timeEntriesRequest.note = note;
            return this;
        }

        public Builder withAt(String at) {
            timeEntriesRequest.at = at;
            return this;
        }

        public Builder withFrom(String from) {
            timeEntriesRequest.from = from;
            return this;
        }

        public Builder withTo(String to) {
            timeEntriesRequest.to = to;
            return this;
        }

        public Builder withBillable(boolean billable) {
            timeEntriesRequest.billable = billable;
            return this;
        }

        public Builder withLocked(boolean locked) {
            timeEntriesRequest.locked = locked;
            return this;
        }

        public Builder withTracking(boolean tracking) {
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
