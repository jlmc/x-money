package com.jcosta.xmoney.api.activity.boundary;

class NonExistentOrInactivePersonException extends RuntimeException {

    public String messageKey = "person.non-existent-or-inactive";

    public String getMessageKey() {
        return messageKey;
    }
}
